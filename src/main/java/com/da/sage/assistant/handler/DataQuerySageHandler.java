/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 21:58:02                                                                        *
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
public class DataQuerySageHandler {
  private DataQueryOfSystemService ds = new DataQueryOfSystemService();

  public void allsites(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SiteMapper.ALL_SITES"));
  }

  public void pnFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.FIND_BY_LIKE"));
  }

  public void pnIndustrialization(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.INDUSTRIALIZATION"));
  }

  public void pnAllPnInRoot(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.ALL_PN_IN_ROOT"));
  }

  public void pnOptionPN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_OPTION_PN"));
  }

  public void pnStockInfo(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.STOCK_INFO"));
  }

  public void salesDelivery(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesMapper.SALES_DELIVERY"));
  }

  public void salesQuote(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesMapper.SALES_QUOTE"));
  }

  public void salesOrder(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesMapper.SALES_ORDER"));
  }

  public void salesOrderCost(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesMapper.SALES_ORDER_COST"));
  }

  public void salesOrderCostDetails(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesMapper.SALES_ORDER_COST_DETAILS"));
  }

  public void purchaseOrder(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseMapper.PURCHASE_ORDER"));
  }

  public void purchaseReceive(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseMapper.PURCHASE_RECEIVE"));
  }
}