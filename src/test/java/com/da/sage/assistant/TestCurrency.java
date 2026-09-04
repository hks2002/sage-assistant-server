/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 19:32:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-30 16:25:25                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.service.CurrencyService;
import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(VertxExtension.class)
public class TestCurrency {

  @BeforeAll
  public static void setup(Vertx vertx) throws Throwable {
    log.info("Test Setup...");
    FS.setup(vertx);
    AppConfig.setupPeriod(vertx, "src/main/resources/config-dev.json");
  }

  @Test
  public void test1(Vertx vertx, VertxTestContext testContext) throws Throwable {

    vertx.setTimer(1000, id -> {
      DB.initDB();
      CurrencyService.getRate("RMB_USD_2024-08-05").onSuccess(rate -> {
        log.info("{}", rate);
        testContext.completeNow();
      }).onFailure(e -> {
        testContext.failNow(e);
      });
    });
  }

  @Test
  public void test2(Vertx vertx, VertxTestContext testContext) throws Throwable {

    vertx.setTimer(1000, id -> {
      DB.initDB();
      CurrencyService.getRate("RMBUSD2024-08-05").onSuccess(rate -> {
        log.info("{}", rate);
        testContext.completeNow();
      }).onFailure(e -> {
        testContext.failNow(e);
      });
    });
  }

}