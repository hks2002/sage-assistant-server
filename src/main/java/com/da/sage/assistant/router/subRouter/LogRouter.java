/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-16 14:09:56                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-18 17:43:19                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.LogHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class LogRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);
    LogHandler logHandler = new LogHandler();

    router.get("/query").handler(logHandler::query);

    return router;
  }
}
