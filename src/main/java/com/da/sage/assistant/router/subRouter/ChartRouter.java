/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-03-31 10:02:11                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.ChartHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ChartRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);
    ChartHandler chartHandler = new ChartHandler();

    router.get("/query").handler(chartHandler::query);
    router.post("/create").handler(chartHandler::create);
    router.put("/update").handler(chartHandler::update);
    router.delete("/delete").handler(chartHandler::delete);

    return router;
  }

}
