/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 16:59:59                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.UserService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.UserContextImpl;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserHandler {
  public void login(RoutingContext ctx) {
    User u = ctx.user();

    if (u == null) {
      UsernamePasswordCredentials credentials = REQUEST.getCredentials(ctx);
      if (credentials == null) {
        RESPONSE.unauthorized(ctx, "Missing Authorization header");
        return;
      }

      UserService userService = new UserService(ctx);

      userService.login(credentials)
          .onFailure(err -> {
            RESPONSE.unauthorized(ctx, err.getMessage());
          })
          .onSuccess(user -> {
            ((UserContextImpl) ctx.userContext()).setUser(user);
            RESPONSE.success(ctx, user.principal());
          });

    } else {// session cached user, no need to check again
      log.debug("user: {} cached in session", u.principal());
      RESPONSE.success(ctx, u.principal());
    }
  }

  public void logout(RoutingContext ctx) {
    User u = ctx.user();

    if (u == null) {
      RESPONSE.success(ctx, "Logout Success");
    } else {
      UserService userService = new UserService(ctx);
      userService.logout(ctx)
          .onFailure(err -> {
            RESPONSE.failed(ctx, err.getMessage());
          })
          .onSuccess((v) -> {
            String accept = RESPONSE.getAccept(ctx);
            switch (accept) {
              case "application/json":
                ctx.userContext().clear();
                RESPONSE.success(ctx, "Logout Success");
                break;
              default:
                ctx.userContext().logout("/sa-web/#/login")
                    .onSuccess((ar) -> {
                    })
                    .onFailure((ar) -> {
                      RESPONSE.internalError(ctx, "Logout Failed");
                    });
            }
          });
    }
  }

  public void query(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    JsonObject queryJson = REQUEST.getQueryJson(ctx);

    RESPONSE.responseObjectDataTotal(ctx, userService.query(queryJson));
  }

  public void create(RoutingContext ctx) {
    User u = ctx.user();

    JsonObject userInfo = ctx.body().asJsonObject();
    userInfo.put("create_by", u.principal().getInteger("id"));

    UserService userService = new UserService(ctx);
    RESPONSE.responseCreate(ctx, userService.insert(userInfo));
  }

  public void update(RoutingContext ctx) {
    User u = ctx.user();

    JsonObject userInfo = ctx.body().asJsonObject();
    userInfo.put("update_by", u.principal().getInteger("id"));

    UserService userService = new UserService(ctx);
    RESPONSE.responseUpdate(ctx, userService.update(userInfo));
  }

  public void delete(RoutingContext ctx) {
    UserService userService = new UserService(ctx);

    RESPONSE.responseDelete(ctx, userService.delete(REQUEST.getQueryJson(ctx)));
  }

  public void permission(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    JsonObject queryJson = REQUEST.getQueryJson(ctx);

    userService.permissionQuery(queryJson)
        .onFailure(err -> {
          RESPONSE.failed(ctx, err.getMessage());
        })
        .onSuccess((val) -> {
          JsonArray permissions = val.getJsonArray("data");
          JsonArray permissionCodes = new JsonArray();
          permissions.forEach(permission -> {
            permissionCodes.add(((JsonObject) permission).getString("permission_code"));
          });
          RESPONSE.success(ctx, permissionCodes);
        });
  }

  public void permissionQuery(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    JsonObject queryJson = REQUEST.getQueryJson(ctx);
    RESPONSE.responseObjectDataTotal(ctx, userService.permissionQuery(queryJson));
  }

  public void assignPermission(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    RESPONSE.responseAssign(ctx, userService.assignPermission(ctx.body().asJsonObject()));
  }

  public void unAssignPermission(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    JsonObject queryJson = REQUEST.getQueryJson(ctx);
    RESPONSE.responseUnAssign(ctx, userService.unAssignPermission(queryJson));
  }

  public void roleQuery(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    JsonObject queryJson = REQUEST.getQueryJson(ctx);
    RESPONSE.responseObjectDataTotal(ctx, userService.roleQuery(queryJson));
  }

  public void assignRole(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    RESPONSE.responseAssign(ctx, userService.assignRole(ctx.body().asJsonObject()));
  }

  public void unAssignRole(RoutingContext ctx) {
    UserService userService = new UserService(ctx);
    RESPONSE.responseUnAssign(ctx, userService.unAssignRole(REQUEST.getQueryJson(ctx)));
  }
}