/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 17:23:33                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.DataService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataHandler {
  private DataService dataService = new DataService();

  public void query(RoutingContext ctx) {
    JsonObject queryJson = REQUEST.getQueryJson(ctx);
    RESPONSE.responseObjectDataTotal(ctx, dataService.query(queryJson));
  }

  public void create(RoutingContext ctx) {
    Integer userId = ctx.user().principal().getInteger("id");
    JsonObject dataInfo = ctx.body().asJsonObject();

    dataInfo.put("create_by", userId);
    dataInfo.put("owner_id", userId);

    RESPONSE.responseCreate(ctx, dataService.insert(dataInfo));
  }

  public void update(RoutingContext ctx) {
    Integer userId = ctx.user().principal().getInteger("id");
    String userName = ctx.user().principal().getString("login_name");
    JsonObject dataInfo = ctx.body().asJsonObject();
    Integer ownerId = dataInfo.getInteger("owner_id");

    if (!userName.equals("sa") && !userId.equals(ownerId)) {
      RESPONSE.forbidden(ctx, "You are not the owner of this data");
      return;
    }
    dataInfo.put("update_by", userId);

    RESPONSE.responseUpdate(ctx, dataService.update(dataInfo));
  }

  public void delete(RoutingContext ctx) {
    Integer userId = ctx.user().principal().getInteger("id");
    String userName = ctx.user().principal().getString("login_name");
    JsonObject dataInfo = REQUEST.getQueryJson(ctx);
    Integer ownerId = Integer.valueOf(dataInfo.getString("owner_id"));

    if (!userName.equals("sa") && !userId.equals(ownerId)) {
      RESPONSE.forbidden(ctx, "You are not the owner of this data");
      return;
    }

    RESPONSE.responseDelete(ctx, dataService.delete(dataInfo));
  }

}
