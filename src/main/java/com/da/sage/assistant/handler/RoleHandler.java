/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 17:14:56                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.RoleService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoleHandler {

  private RoleService roleService = new RoleService();

  public void query(RoutingContext ctx) {
    JsonObject queryJson = REQUEST.getQueryJson(ctx);

    RESPONSE.responseObjectDataTotal(ctx, roleService.query(queryJson));
  }

  public void create(RoutingContext ctx) {
    User u = ctx.user();
    Integer userId = u.principal().getInteger("id");

    JsonObject roleInfo = ctx.body().asJsonObject();
    roleInfo.put("create_by", userId);

    RESPONSE.responseCreate(ctx, roleService.insert(roleInfo));
  }

  public void update(RoutingContext ctx) {
    Integer userId = ctx.user().principal().getInteger("id");
    JsonObject roleInfo = ctx.body().asJsonObject();
    roleInfo.put("update_by", userId);

    RESPONSE.responseUpdate(ctx, roleService.update(roleInfo));
  }

  public void delete(RoutingContext ctx) {
    RESPONSE.responseDelete(ctx, roleService.delete(REQUEST.getQueryJson(ctx)));
  }

  public void permissionQuery(RoutingContext ctx) {
    JsonObject queryJson = REQUEST.getQueryJson(ctx);

    RESPONSE.responseObjectDataTotal(ctx, roleService.permissionQuery(queryJson));
  }

  public void assignPermission(RoutingContext ctx) {
    RESPONSE.responseAssign(ctx, roleService.assignPermission(ctx.body().asJsonObject()));
  }

  public void unAssignPermission(RoutingContext ctx) {
    RESPONSE.responseUnAssign(ctx, roleService.unAssignPermission(REQUEST.getQueryJson(ctx)));
  }

  public void userQuery(RoutingContext ctx) {
    JsonObject queryJson = REQUEST.getQueryJson(ctx);

    RESPONSE.responseObjectDataTotal(ctx, roleService.userQuery(queryJson));
  }

  public void assignUser(RoutingContext ctx) {
    RESPONSE.responseAssign(ctx, roleService.assignUser(ctx.body().asJsonObject()));
  }

  public void unAssignUser(RoutingContext ctx) {
    RESPONSE.responseUnAssign(ctx, roleService.unAssignUser(REQUEST.getQueryJson(ctx)));
  }
}
