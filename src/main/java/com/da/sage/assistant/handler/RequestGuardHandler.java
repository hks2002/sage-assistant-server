/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 16:09:54                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.AppConfig;
import com.da.sage.assistant.service.LogService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authorization.Authorizations;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RequestGuardHandler {

  public void check(RoutingContext ctx) {
    String requestPath = ctx.normalizedPath();
    String requestAction = requestPath.substring(ctx.currentRoute().getPath().length());
    JsonObject queryParams = REQUEST.getQueryJson(ctx);

    String ip = REQUEST.getTrueRemoteIp(ctx);
    ctx.put("ip", ip);

    User u = ctx.user();

    log.debug("requestAction: {}, ip: {}", requestAction, ip);

    if (requestAction.equals("user/login")
        || requestAction.equals("user/logout")
        || requestAction.equals("user/permission-query")
        || requestAction.equals("server/info")
        || requestAction.equals("server/dependencies")) {
      ctx.next();
      return;
    }

    // data-query/b
    if (requestAction.startsWith("data-query")) {
      ctx.next();
      return;
    }

    if (u == null) {
      RESPONSE.unauthorized(ctx, "Session expired or not logged in");
      return;

    } else {// session cached user, no need to check again
      String userName = u.principal().getString("login_name");
      log.trace("user: {} cached in session", userName);

      boolean permissionManage = AppConfig.config.getBoolean("permissionManage", true);
      if (permissionManage) {
        Authorizations authz = u.authorizations();
        if (!authz.contains(requestAction)) {
          RESPONSE.unauthorized(ctx, "No permission of [" + requestAction + "]");
          return;
        }
      }

      // if (REQUEST.isFileUpload(ctx)) {
      LogService.addLog("ACCESS", ip, userName, requestAction, queryParams.encode());
      // } else {
      // String questBody = Optional.ofNullable(ctx.body().asString()).orElse("");
      // LogService.addLog("ACCESS", ip, userName, requestAction,
      // queryParams.encode(), questBody);
      // }

      ctx.next();
    }

  }
}
