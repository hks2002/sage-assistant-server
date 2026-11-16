/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-11-09 17:26:27                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-09 18:39:04                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestDBPerfVertx {

  private static final int TIMES = 20;
  private static Vertx vertx;
  private final long[] cost = new long[TIMES];

  @BeforeAll
  public static void setup() {
    vertx = Vertx.vertx();
    FS.setup(vertx);
    AppConfig.setupPeriod(vertx, "src/main/resources/config-prod.json");
  }

  @Test
  public void runSelectOne() throws InterruptedException {
    CountDownLatch ready = new CountDownLatch(1);
    vertx.setTimer(3000, id -> {
      DB.setupDB();
      ready.countDown();
    });
    if (!ready.await(20, TimeUnit.SECONDS)) {
      log.error("DB init timeout");
      return;
    }

    // warmup: fill the pool before measuring, to isolate connection-establishment
    // cost
    int poolMax = TIMES;
    for (Object o : AppConfig.config.getJsonArray("dataSource")) {
      JsonObject ds = (JsonObject) o;
      if ("sage".equals(ds.getString("name"))) {
        poolMax = ds.getJsonObject("options").getInteger("maxSize", TIMES);
        break;
      }
    }
    CountDownLatch warm = new CountDownLatch(poolMax);
    for (int i = 0; i < poolMax; i++) {
      DB.selectBySql("sage", "SELECT 1", new JsonObject()).onComplete(ar -> warm.countDown());
    }
    if (!warm.await(60, TimeUnit.SECONDS)) {
      log.error("warmup timeout, done = {}", poolMax - warm.getCount());
    }
    log.info("warmup {} connections done", poolMax - warm.getCount());

    CountDownLatch latch = new CountDownLatch(TIMES);
    AtomicInteger success = new AtomicInteger();

    long overallStart = System.nanoTime();
    for (int i = 0; i < TIMES; i++) {
      final int idx = i;
      long t0 = System.nanoTime();
      DB.selectBySql("sage", "SELECT 1", new JsonObject())
          .onComplete(ar -> {
            cost[idx] = System.nanoTime() - t0;
            if (ar.succeeded()) {
              success.incrementAndGet();
            } else {
              log.error("query #{} failed: {}", idx, ar.cause().getMessage());
            }
            latch.countDown();
          });
    }

    if (!latch.await(180, TimeUnit.SECONDS)) {
      log.error("Timed out waiting for {} queries", TIMES - latch.getCount());
    }

    long overallElapsed = System.nanoTime() - overallStart;
    long sum = 0;
    long min = Long.MAX_VALUE;
    long max = 0;
    int done = (int) (TIMES - latch.getCount());
    for (int i = 0; i < done; i++) {
      sum += cost[i];
      min = Math.min(min, cost[i]);
      max = Math.max(max, cost[i]);
      log.info("query #{} = {} ms", i, ms(cost[i]));
    }

    log.info("--------------------------------------------");
    log.info("select 1 x {} (concurrent) done", TIMES);
    log.info("success  = {} / {} (done {})", success.get(), TIMES, done);
    log.info("total    = {} ms", ms(overallElapsed));
    log.info("avg      = {} ms", done > 0 ? ms(sum) / done : 0);
    log.info("min      = {} ms", done > 0 ? ms(min) : 0);
    log.info("max      = {} ms", ms(max));
    log.info("--------------------------------------------");
  }

  @Test
  public void runSelectOneRaw() throws InterruptedException {
    CountDownLatch ready = new CountDownLatch(1);
    vertx.setTimer(3000, id -> {
      DB.setupDB();
      ready.countDown();
    });
    if (!ready.await(20, TimeUnit.SECONDS)) {
      log.error("DB init timeout");
      return;
    }

    io.vertx.sqlclient.Pool pool = DB.pools.get("sage");
    if (pool == null) {
      log.error("sage pool not found");
      return;
    }

    // warmup: fill the pool
    int poolMax = TIMES;
    for (Object o : AppConfig.config.getJsonArray("dataSource")) {
      JsonObject ds = (JsonObject) o;
      if ("sage".equals(ds.getString("name"))) {
        poolMax = ds.getJsonObject("options").getInteger("maxSize", TIMES);
        break;
      }
    }
    CountDownLatch warm = new CountDownLatch(poolMax);
    for (int i = 0; i < poolMax; i++) {
      pool.query("SELECT 1").execute().onComplete(ar -> warm.countDown());
    }
    warm.await(60, TimeUnit.SECONDS);
    log.info("warmup {} connections done", poolMax - warm.getCount());

    long[] cost = new long[TIMES];
    CountDownLatch latch = new CountDownLatch(TIMES);
    AtomicInteger success = new AtomicInteger();

    long overallStart = System.nanoTime();
    for (int i = 0; i < TIMES; i++) {
      final int idx = i;
      long t0 = System.nanoTime();
      pool.query("SELECT 1").execute().onComplete(ar -> {
        cost[idx] = System.nanoTime() - t0;
        if (ar.succeeded()) {
          success.incrementAndGet();
        } else {
          log.error("query #{} failed: {}", idx, ar.cause().getMessage());
        }
        latch.countDown();
      });
    }

    if (!latch.await(180, TimeUnit.SECONDS)) {
      log.error("Timed out waiting for {} queries", TIMES - latch.getCount());
    }

    long overallElapsed = System.nanoTime() - overallStart;
    long sum = 0;
    long min = Long.MAX_VALUE;
    long max = 0;
    int done = (int) (TIMES - latch.getCount());
    for (int i = 0; i < done; i++) {
      sum += cost[i];
      min = Math.min(min, cost[i]);
      max = Math.max(max, cost[i]);
      log.info("query #{} = {} ms", i, ms(cost[i]));
    }

    log.info("--------------------------------------------");
    log.info("select 1 x {} (raw pool.query) done", TIMES);
    log.info("success  = {} / {} (done {})", success.get(), TIMES, done);
    log.info("total    = {} ms", ms(overallElapsed));
    log.info("avg      = {} ms", done > 0 ? ms(sum) / done : 0);
    log.info("min      = {} ms", done > 0 ? ms(min) : 0);
    log.info("max      = {} ms", ms(max));
    log.info("--------------------------------------------");
  }

  private static long ms(long nanos) {
    return nanos / 1_000_000L;
  }
}