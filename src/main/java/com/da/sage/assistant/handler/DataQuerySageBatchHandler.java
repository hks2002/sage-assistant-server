/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 21:53:15                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.DataQueryOfSystemService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQuerySageBatchHandler {
  private DataQueryOfSystemService ds = new DataQueryOfSystemService();

  public void salesOrderBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "SalesMapper.SALES_ORDER", "OrderDate"));
  }

  public void salesQuoteBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "SalesMapper.SALES_QUOTE", "QuoteDate"));
  }

  public void salesOrderCostBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "SalesMapper.SALES_ORDER_COST", "OrderDate"));
  }

  public void salesOrderCosDetailstBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "SalesMapper.SALES_ORDER_COST_DETAIL", "OrderDate"));
  }

  public void purchaseOrderBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "PurchaseOrderMapper.PURCHASE_ORDER", "PurchaseOrderDate"));
  }

  public void projectProfitBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "ProjectMapper.PROJECT_PROFIT", "ProjectProfitDate"));
  }

  public void projectProfitDetailsBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "ProjectMapper.PROJECT_PROFIT_DETAILS", "ProjectProfitDate"));
  }

}