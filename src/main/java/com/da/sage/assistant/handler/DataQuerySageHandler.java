/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-11 14:40:46                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import com.da.sage.assistant.service.DataQueryOfSageService;
import com.da.sage.assistant.serviceStatic.REQUEST;
import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQuerySageHandler {
  private DataQueryOfSageService ds = new DataQueryOfSageService();

  // #region Common
  // --------------------------------------------------------------------------------------------------
  public void allSites(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CommonMapper.ALL_SITES"));
  }

  public void representativeFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CommonMapper.SALES_REPRESENTATIVE_FIND_BY_LIKE"));
  }

  public void buyerFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CommonMapper.BUYER_FIND_BY_LIKE"));
  }

  public void soFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CommonMapper.SO_FIND_BY_LIKE"));
  }

  public void projectFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CommonMapper.PROJECT_FIND_BY_LIKE"));
  }

  public void logisticsOperatorFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CommonMapper.LOGISTICS_OPERATOR_FIND_BY_LIKE"));
  }
  // #endregion

  // #region PN
  // --------------------------------------------------------------------------------------------------
  public void pnFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_FIND_BY_LIKE"));
  }

  public void pnIndustrialization(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_INDUSTRIALIZATION"));
  }

  public void pnAllPnInRoot(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_ALL_IN_ROOT"));
  }

  public void pnOptionPN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_OPTION_PN"));
  }

  public void pnOptionPNShort(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_OPTION_PN_SHORT"));
  }

  public void pnGroup1(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_GROUP1"));
  }

  public void pnGroup2(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_GROUP2"));
  }

  public void pnGroup3(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_GROUP3"));
  }

  public void pnProgram(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_PROGRAM"));
  }

  public void pnFamily(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PnMapper.PN_FAMILY"));
  }
  // #endregion

  // #region PN stock
  // --------------------------------------------------------------------------------------------------
  public void pnStockInfo(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "StockMapper.STOCK_INFO"));
  }

  public void stockHistory(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "StockMapper.STOCK_HISTORY"));
  }

  public void stockHistoryCnt(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "StockMapper.STOCK_HISTORY_CNT"));
  }

  public void stockSummary(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "StockMapper.STOCK_SUMMARY"));
  }
  // #endregion

  // #region PN sales
  // --------------------------------------------------------------------------------------------------
  public void salesQuote(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesQuoteMapper.SALES_QUOTE_OF_PN"));
  }

  public void salesOrder(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesOrderMapper.SALES_ORDER_OF_PN"));
  }

  public void salesOrderCost(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CostProfitMapper.COST_OF_PN"));
  }

  public void salesDelivery(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesOrderMapper.SALES_DELIVERY_OF_PN"));
  }
  // #endregion

  // #region PN purchase
  public void purchaseOrder(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseOrderMapper.PURCHASE_ORDER_OF_PN"));
  }

  public void purchaseReceive(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseOrderMapper.PURCHASE_RECEIVE_OF_PN"));
  }
  // #endregion

  // #region bp common
  // --------------------------------------------------------------------------------------------------
  public void bpCountry(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpLocationMapper.BP_COUNTRY"));
  }

  public void bpUsState(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpLocationMapper.BP_US_STATE"));
  }

  public void bpCity(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpLocationMapper.BP_CITY"));
  }
  // #endregion

  // #region bp customer
  // --------------------------------------------------------------------------------------------------

  public void bpCustomerCategory(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpCustomerMapper.BP_CUSTOMER_CATEGORY"));
  }

  public void bpCustomerType(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpCustomerMapper.BP_CUSTOMER_TYPE"));
  }

  public void bpCustomerGeoRegion(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpCustomerMapper.BP_CUSTOMER_GEO_REGION"));
  }

  public void bpCustomerFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpCustomerMapper.BP_CUSTOMER_FIND_BY_LIKE"));
  }

  public void bpCustomerInfo(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpCustomerMapper.BP_CUSTOMER_INFO"));
  }
  // #endregion

  // #region bp supplier
  // --------------------------------------------------------------------------------------------------
  public void bpSupplierCategory(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpSupplierMapper.BP_SUPPLIER_CATEGORY"));
  }

  public void bpSupplierType(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpSupplierMapper.BP_SUPPLIER_TYPE"));
  }

  public void bpSupplierSubType(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpSupplierMapper.BP_SUPPLIER_SUB_TYPE"));
  }

  public void bpSupplierGeoRegion(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpSupplierMapper.BP_SUPPLIER_GEO_REGION"));
  }

  public void bpSupplierFindByLike(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpSupplierMapper.BP_SUPPLIER_FIND_BY_LIKE"));
  }

  public void bpSupplierInfo(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "BpSupplierMapper.BP_SUPPLIER_INFO"));
  }
  // #endregion

  // #region sales order
  // --------------------------------------------------------------------------------------------------
  public void salesOrderLineDetails(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesOrderMapper.SALES_ORDER_LINE_DETAILS"));
  }

  public void salesOrderLineDetailsCnt(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesOrderMapper.SALES_ORDER_LINE_DETAILS_CNT"));
  }

  public void salesOrderLineAmountSum(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.querySum(params, "SalesOrderMapper.SALES_ORDER_LINE_AMOUNT_BY_TARGET"));
  }

  public void salesOrderLineCountSum(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.querySum(params, "SalesOrderMapper.SALES_ORDER_LINE_COUNT_BY_TARGET"));
  }

  public void salesOrderLineAmountTopN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.queryTopN(params, "SalesOrderMapper.SALES_ORDER_LINE_AMOUNT_BY_TARGET"));
  }

  public void salesOrderLineCountTopN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.queryTopN(params, "SalesOrderMapper.SALES_ORDER_LINE_COUNT_BY_TARGET"));
  }

  public void salesOrderLineOTD(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesOrderMapper.SALES_ORDER_LINE_OTD_BY_TARGET"));
  }

  public void salesOrderLineDOD(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "SalesOrderMapper.SALES_ORDER_LINE_DOD"));
  }

  // #endregion
  // #region delivery lines
  // --------------------------------------------------------------------------------------------------
  public void deliveryLineDetails(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "DeliveryMapper.DELIVERY_LINE_DETAILS"));
  }

  public void deliveryLineDetailsCnt(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "DeliveryMapper.DELIVERY_LINE_DETAILS_CNT"));
  }

  public void deliveryLineAmountSum(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.querySum(params, "DeliveryMapper.DELIVERY_LINE_AMOUNT_BY_TARGET"));
  }

  public void deliveryLineCountSum(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.querySum(params, "DeliveryMapper.DELIVERY_LINE_COUNT_BY_TARGET"));
  }

  public void deliveryLineAmountTopN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.queryTopN(params, "DeliveryMapper.DELIVERY_LINE_AMOUNT_BY_TARGET"));
  }

  public void deliveryLineCountTopN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.queryTopN(params, "DeliveryMapper.DELIVERY_LINE_COUNT_BY_TARGET"));
  }
  // #endregion

  // #region purchase order
  // --------------------------------------------------------------------------------------------------
  public void purchaseOrderLineDetails(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_DETAILS"));
  }

  public void purchaseOrderLineDetailsCnt(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_DETAILS_CNT"));
  }

  public void purchaseOrderLineAmountSum(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.querySum(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_AMOUNT"));
  }

  public void purchaseOrderLineCountSum(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.querySum(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_COUNT"));
  }

  public void purchaseOrderLineAmountTopN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.queryTopN(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_AMOUNT"));
  }

  public void purchaseOrderLineCountTopN(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.queryTopN(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_COUNT"));
  }

  public void purchaseOrderLineOTD(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_OTD_BY_TARGET"));
  }

  public void purchaseOrderLineDOD(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "PurchaseOrderMapper.PURCHASE_ORDER_LINE_DOD"));
  }
  // #endregion

  // #region project profit
  // --------------------------------------------------------------------------------------------------
  public void projectProfit(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseArray(ctx, ds.query(params, "CostProfitMapper.PROJECT_PROFIT"));
  }

  // #endregion
}