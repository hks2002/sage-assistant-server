/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-26 17:23:32                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.DataHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);
    DataHandler dataHandler = new DataHandler();

    router.get("/query").handler(dataHandler::query);
    router.post("/create").handler(dataHandler::create);
    router.put("/update").handler(dataHandler::update);
    router.delete("/delete").handler(dataHandler::delete);

    return router;
  }

}
