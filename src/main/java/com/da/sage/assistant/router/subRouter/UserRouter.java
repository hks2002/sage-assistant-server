/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-01 00:49:12                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.UserHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);
    UserHandler userHandler = new UserHandler();

    router.post("/login").handler(userHandler::login);
    router.post("/logout").handler(userHandler::logout);

    router.get("/query").handler(userHandler::query);
    router.post("/create").handler(userHandler::create);
    router.put("/update").handler(userHandler::update);
    router.delete("/delete").handler(userHandler::delete);

    router.get("/permission").handler(userHandler::permission);
    router.get("/permission-query").handler(userHandler::permissionQuery);
    router.post("/permission-assign").handler(userHandler::assignPermission);
    router.delete("/permission-unassign").handler(userHandler::unAssignPermission);

    router.get("/role-query").handler(userHandler::roleQuery);
    router.post("/role-assign").handler(userHandler::assignRole);
    router.delete("/role-unassign").handler(userHandler::unAssignRole);

    return router;
  }

}
