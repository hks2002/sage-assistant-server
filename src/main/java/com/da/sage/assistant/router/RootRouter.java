/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-28 11:25:07                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router;

import com.da.sage.assistant.AppConfig;
import com.da.sage.assistant.handler.RequestGuardHandler;
import com.da.sage.assistant.router.subRouter.ChartRouter;
import com.da.sage.assistant.router.subRouter.DataQueryRouter;
import com.da.sage.assistant.router.subRouter.DataRouter;
import com.da.sage.assistant.router.subRouter.LogRouter;
import com.da.sage.assistant.router.subRouter.RoleRouter;
import com.da.sage.assistant.router.subRouter.ServerRouter;
import com.da.sage.assistant.router.subRouter.UserRouter;
import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RootRouter {
  public static Router create(Vertx vertx) {
    var sessionConfig = AppConfig.config.getJsonObject("session");
    var uploadConfig = AppConfig.config.getJsonObject("upload");

    // session
    SessionStore sessionStore = LocalSessionStore.create(FS.vertx);
    SessionHandler sessionHandler = SessionHandler.create(sessionStore);
    Long timeOut = sessionConfig.getLong("sessionTimeout");
    sessionHandler
        .setSessionTimeout(timeOut)
        .setCookieHttpOnlyFlag(true)
        .setCookieSecureFlag(true);

    // upload
    BodyHandler bodyHandler = BodyHandler.create();
    Long bodyLimit = uploadConfig.getLong("bodyLimit", Long.MAX_VALUE);
    bodyHandler
        .setDeleteUploadedFilesOnEnd(false)
        .setBodyLimit(bodyLimit);

    Router api = Router.router(vertx);
    api.route().handler(sessionHandler);
    api.route().handler(bodyHandler);

    RequestGuardHandler requestGuardHandler = new RequestGuardHandler();
    api.route("/sa-api/*").handler(requestGuardHandler::check);
    api.route("/sa-api/server/*").subRouter(ServerRouter.create(vertx));
    api.route("/sa-api/log/*").subRouter(LogRouter.create(vertx));
    api.route("/sa-api/user/*").subRouter(UserRouter.create(vertx));
    api.route("/sa-api/role/*").subRouter(RoleRouter.create(vertx));
    api.route("/sa-api/data/*").subRouter(DataRouter.create(vertx));
    api.route("/sa-api/chart/*").subRouter(ChartRouter.create(vertx));
    api.route("/sa-api/data-query/*").subRouter(DataQueryRouter.create(vertx));

    RouterPriter.print(api);

    return api;
  }

}
