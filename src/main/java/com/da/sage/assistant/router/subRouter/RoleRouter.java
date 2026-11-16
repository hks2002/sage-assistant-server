/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-26 17:23:32                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.RoleHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoleRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);
    RoleHandler roleHandler = new RoleHandler();

    router.get("/query").handler(roleHandler::query);
    router.post("/create").handler(roleHandler::create);
    router.put("/update").handler(roleHandler::update);
    router.delete("/delete").handler(roleHandler::delete);

    router.get("/permission-query").handler(roleHandler::permissionQuery);
    router.post("/permission-assign").handler(roleHandler::assignPermission);
    router.delete("/permission-unassign").handler(roleHandler::unAssignPermission);

    router.get("/user-query").handler(roleHandler::userQuery);
    router.post("/user-assign").handler(roleHandler::assignUser);
    router.delete("/user-unassign").handler(roleHandler::unAssignUser);

    return router;
  }

}
