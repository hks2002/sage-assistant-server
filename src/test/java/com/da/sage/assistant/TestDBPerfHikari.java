/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-11-09 17:42:04                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-09 18:39:06                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.da.sage.assistant.serviceStatic.FS;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestDBPerfHikari {

  private static final int TIMES = 10;
  private static Vertx vertx;
  private static HikariDataSource dataSource;

  @BeforeAll
  public static void setup() throws Exception {
    vertx = Vertx.vertx();
    FS.setup(vertx);
    AppConfig.setupPeriod(vertx, "src/main/resources/config-prod.json");

    CountDownLatch ready = new CountDownLatch(1);
    vertx.setTimer(3000, id -> ready.countDown());
    ready.await(20, TimeUnit.SECONDS);

    dataSource = buildDataSourceFor("sage");
  }

  private static HikariDataSource buildDataSourceFor(String name) {
    JsonObject sage = null;
    for (Object o : AppConfig.config.getJsonArray("dataSource")) {
      JsonObject ds = (JsonObject) o;
      if (name.equals(ds.getString("name"))) {
        sage = ds;
        break;
      }
    }
    if (sage == null) {
      throw new IllegalStateException("datasource '" + name + "' not found in config");
    }

    JsonObject cfg = sage.getJsonObject("config");
    JsonObject options = sage.getJsonObject("options");
    String type = sage.getString("type");

    HikariConfig hc = new HikariConfig();
    int poolMax = options.getInteger("maxSize", 5);
    hc.setMaximumPoolSize(poolMax);
    hc.setMinimumIdle(poolMax);

    if ("MSSQL".equalsIgnoreCase(type) || "SQLSERVER".equalsIgnoreCase(type)) {
      hc.setJdbcUrl(cfg.getString("jdbcUrl"));
      hc.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      hc.setUsername(cfg.getString("user"));
      hc.setPassword(cfg.getString("password"));
    } else {
      String host = cfg.getString("host");
      Integer port = cfg.getInteger("port");
      String database = cfg.getString("database");
      hc.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database
          + "?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
      hc.setDriverClassName("com.mysql.cj.jdbc.Driver");
      hc.setUsername(cfg.getString("user"));
      hc.setPassword(cfg.getString("password"));
    }
    return new HikariDataSource(hc);
  }

  @Test
  public void runSelectOneHikari() throws InterruptedException {
    long[] cost = new long[TIMES];
    CountDownLatch latch = new CountDownLatch(TIMES);
    AtomicInteger success = new AtomicInteger();
    ExecutorService exec = Executors.newFixedThreadPool(TIMES);

    int pre = dataSource.getMaximumPoolSize();
    CountDownLatch warm = new CountDownLatch(pre);
    for (int i = 0; i < pre; i++) {
      exec.submit(() -> {
        try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
          st.execute("SELECT 1");
        } catch (Exception e) {
          log.error("warmup failed: {}", e.getMessage());
        } finally {
          warm.countDown();
        }
      });
    }
    warm.await(60, TimeUnit.SECONDS);
    log.info("warmup {} connections done", pre - warm.getCount());

    long overallStart = System.nanoTime();
    for (int i = 0; i < TIMES; i++) {
      final int idx = i;
      exec.submit(() -> {
        long t0 = System.nanoTime();
        try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
          st.execute("SELECT 1");
          cost[idx] = System.nanoTime() - t0;
          success.incrementAndGet();
        } catch (Exception e) {
          log.error("query #{} failed: {}", idx, e.getMessage());
          cost[idx] = System.nanoTime() - t0;
        } finally {
          latch.countDown();
        }
      });
    }
    exec.shutdown();
    if (!latch.await(180, TimeUnit.SECONDS)) {
      log.error("Timed out waiting for {} queries", TIMES - latch.getCount());
    }

    long overallElapsed = System.nanoTime() - overallStart;
    long sum = 0;
    long min = Long.MAX_VALUE;
    long max = 0;
    for (int i = 0; i < TIMES; i++) {
      sum += cost[i];
      min = Math.min(min, cost[i]);
      max = Math.max(max, cost[i]);
      log.info("query #{} = {} ms", i, ms(cost[i]));
    }

    log.info("--------------------------------------------");
    log.info("select 1 x {} (concurrent, HikariCP) done", TIMES);
    log.info("success = {} / {}", success.get(), TIMES);
    log.info("total   = {} ms", ms(overallElapsed));
    log.info("avg     = {} ms", ms(sum) / TIMES);
    log.info("min     = {} ms", ms(min));
    log.info("max     = {} ms", ms(max));
    log.info("--------------------------------------------");
  }

  private static long ms(long nanos) {
    return nanos / 1_000_000L;
  }
}