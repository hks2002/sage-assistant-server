/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 17:26:21                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.ChartService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ChartHandler {
  private ChartService chartService = new ChartService();

  public void query(RoutingContext ctx) {
    JsonObject queryJson = REQUEST.getQueryJson(ctx);

    RESPONSE.responseObjectDataTotal(ctx, chartService.query(queryJson));
  }

  public void create(RoutingContext ctx) {
    User u = ctx.user();
    Integer userId = u.principal().getInteger("id");

    JsonObject chartInfo = ctx.body().asJsonObject();
    // escape single quote
    String chartOptions = chartInfo.getString("chart_options", "").replaceAll("'", "''");
    chartInfo.put("chart_options", chartOptions);
    chartInfo.put("create_by", userId);

    RESPONSE.responseCreate(ctx, chartService.insert(chartInfo));
  }

  public void update(RoutingContext ctx) {
    Integer userId = ctx.user().principal().getInteger("id");
    JsonObject chartInfo = ctx.body().asJsonObject();
    Integer ownerId = Integer.valueOf(chartInfo.getString("owner_id"));
    // escape single quote
    String chartOptions = chartInfo.getString("chart_options", "").replaceAll("'", "''");

    if (!userId.equals(ownerId)) {
      RESPONSE.forbidden(ctx, "You are not the owner of this data");
      return;
    }

    chartInfo.put("chart_options", chartOptions);
    chartInfo.put("update_by", userId);

    RESPONSE.responseUpdate(ctx, chartService.update(chartInfo));
  }

  public void delete(RoutingContext ctx) {
    Integer userId = ctx.user().principal().getInteger("id");
    JsonObject chartInfo = REQUEST.getQueryJson(ctx);
    Integer ownerId = Integer.parseInt(chartInfo.getString("owner_id"));

    if (!userId.equals(ownerId)) {
      RESPONSE.forbidden(ctx, "You are not the owner of this data");
      return;
    }

    RESPONSE.responseDelete(ctx, chartService.delete(chartInfo));
  }

}
