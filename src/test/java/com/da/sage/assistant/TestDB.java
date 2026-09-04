/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 19:32:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-24 11:20:33                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(VertxExtension.class)
public class TestDB {

  @BeforeAll
  public static void setup(Vertx vertx) throws Throwable {
    log.info("Test Setup...");
    FS.setup(vertx);
    AppConfig.setupPeriod(vertx, "src/main/resources/config-dev.json");
  }

  @Test
  public void testDB1(Vertx vertx, VertxTestContext testContext) throws Throwable {

    vertx.setTimer(1000, id -> {
      DB.initDB();

      String sql = """
          SELECT SQL_CALC_FOUND_ROWS  * from user;
          SELECT FOUND_ROWS();
                      """;
      DB.selectBySql("sa", sql, JsonObject.of("p1", "AAAA"))
          .onSuccess(rs -> {
            testContext.completeNow();
          })
          .onFailure(e -> {
            testContext.failNow(e);
          });

    });
  }

  @Test
  public void testDB3(Vertx vertx, VertxTestContext testContext) throws Throwable {

    vertx.setTimer(1000, id -> {
      DB.initDB();

      String sql = """
          SELECT #{p1} AS p1 ;
                      """;
      DB.selectBySql("sage", sql, JsonObject.of("p1", "AAAA"))
          .onSuccess(rs -> {

            testContext.completeNow();
          })
          .onFailure(e -> {
            testContext.failNow(e);
          });

    });
  }

}