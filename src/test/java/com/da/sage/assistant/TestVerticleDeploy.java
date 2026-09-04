/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-05-19 16:13:27                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-10 16:19:28                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/

package com.da.sage.assistant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(VertxExtension.class)
public class TestVerticleDeploy {
  @BeforeAll
  public static void setup(Vertx vertx) throws Throwable {
    log.info("Test Setup...");
    FS.setup(vertx);
    AppConfig.setupPeriod(vertx, "src/main/resources/config-dev.json");
  }

  @Test
  void verticleDeploy(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      log.info("App config: {}", AppConfig.config.encodePrettily());
      testContext.completeNow();
    });
  }
}