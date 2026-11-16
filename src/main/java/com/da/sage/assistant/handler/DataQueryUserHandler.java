/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 17:19:26                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.DataQueryOfUserService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQueryUserHandler {

  private DataQueryOfUserService ds = new DataQueryOfUserService();

  public void source(RoutingContext ctx) {
    RESPONSE.responseArray(ctx, ds.getDataSource());
  }

  public void test(RoutingContext ctx) {
    JsonObject dataInfo = ctx.body().asJsonObject();
    RESPONSE.responseObjectDataTotal(ctx, ds.test(dataInfo));
  }

  public void query(RoutingContext ctx) {
    String db = ctx.pathParam("db");
    String dataCode = ctx.pathParam("data_code");
    JsonObject params = REQUEST.getQueryJson(ctx);

    RESPONSE.responseObjectDataTotal(ctx, ds.query(db, dataCode, params));
  }
}