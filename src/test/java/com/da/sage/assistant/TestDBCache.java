/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 19:32:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-07 14:17:37                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.db.DBQueryCache;
import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(VertxExtension.class)
public class TestDBCache {
  @BeforeAll
  public static void setup(Vertx vertx) throws Throwable {
    log.info("Test Setup...");
    FS.setup(vertx);
    AppConfig.setupPeriod(vertx, "src/main/resources/config-dev.json");
  }

  @Test
  public void test0(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      DB.initDB();
      DB.selectBySqlId("user_Query", new JsonObject())
          .onFailure(err -> {
            log.error("{}", err.getMessage());
            testContext.failNow(err);
          })
          .onSuccess(s -> {
            log.info("{}", s.encode());
            testContext.completeNow();
          });
    });

  }

  @Test
  public void test1(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      DB.initDB();

      String key = "sa" + "|" + "user_Query" + "|" + "{}";
      Future<JsonArray> f1 = DBQueryCache.get(key);

      f1.onFailure(err -> {
        log.error("{}", err.getMessage());
        testContext.failNow(err);
      }).onSuccess(s -> {
        log.info("{}", s.encode());

        testContext.completeNow();
      });
    });
  }

  @Test
  public void test2(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      DB.initDB();

      String key = "sa" + "|" + "user_Query" + "|" + "{}";
      Future<JsonArray> f1 = DBQueryCache.get(key);

      f1.onFailure(err -> {
        log.error("{}", err.getMessage());
        testContext.failNow(err);
      }).onSuccess(s -> {
        log.info("{}", s.encode());
      }).andThen(ar -> {
        // now the second query should be from cache, without the sql log
        Future<JsonArray> f2 = DBQueryCache.get(key);
        f2.onFailure(err -> {
          log.error("{}", err.getMessage());
          testContext.failNow(err);
        }).onSuccess(s -> {
          log.info("{}", s.encode());
          testContext.completeNow();
        });
      });

    });
  }

  @Test
  public void test3(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      DB.initDB();

      String key = "sa" + "|" + "user_Query" + "|" + "{}";
      Future<JsonArray> f1 = DBQueryCache.get(key);

      f1.onFailure(err -> {
        log.error("{}", err.getMessage());
        testContext.failNow(err);
      }).onSuccess(s -> {
        log.info("{}", s.encode());

        testContext.completeNow();
      });

    });
  }

  @Test
  public void test4(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      DB.initDB();

      String key = "sa" + "|" + "user_Query" + "|" + "{}";
      Future<JsonArray> f1 = DBQueryCache.get(key);

      f1.onFailure(err -> {
        log.error("{}", err.getMessage());
        testContext.failNow(err);
      }).onSuccess(s -> {
        log.info("{}", s.encode());
      }).andThen(ar -> {
        // now the second query should be from cache, without the sql log
        Future<JsonArray> f2 = DBQueryCache.get(key);

        f2.onFailure(err -> {
          log.error("{}", err.getMessage());
          testContext.failNow(err);
        }).onSuccess(s -> {
          log.info("{}", s.encode());

          testContext.completeNow();
        });
      });

    });
  }

  @Test
  public void test5(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.setTimer(1000, id -> {
      DB.initDB();

      String key = "sa" + "|" + "user_Query" + "|" + "{}";
      Future<JsonArray> f1 = DBQueryCache.get(key);

      f1.onFailure(err -> {
        log.error("{}", err.getMessage());
        testContext.failNow(err);
      }).onSuccess(s -> {
        log.info("{}", s.encode());
      }).andThen(ar -> {

        DBQueryCache.invalidateByKey("sa" + "|" + "user_Query");
        Future<JsonArray> f2 = DBQueryCache.get(key);

        f2.onFailure(err -> {
          log.error("{}", err.getMessage());
          testContext.failNow(err);
        }).onSuccess(s -> {
          log.info("{}", s.encode());

          testContext.completeNow();
        });
      });

    });
  }
}
