/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-18 17:29:06                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.ServerHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServerRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);
    ServerHandler serverHandler = new ServerHandler();
    router.get("/info").handler(serverHandler::info);
    router.get("/dependencies").handler(serverHandler::dependencies);

    return router;
  }

}
