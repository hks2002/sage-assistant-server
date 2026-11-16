/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-16 11:51:49                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-06 09:29:29                                                                        *
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
public class DataQuerySageBatchHandler {
  private DataQueryOfSageService ds = new DataQueryOfSageService();

  public void salesOrderBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "SalesOrderMapper.SALES_ORDER_OF_PN", "OrderDate"));
  }

  public void salesQuoteBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "SalesQuoteMapper.SALES_QUOTE_OF_PN", "QuoteDate"));
  }

  public void salesOrderCostBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "CostProfitMapper.COST_OF_PN", "OrderDate"));
  }

  public void purchaseOrderBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx,
        ds.batchQuery(params, "PurchaseOrderMapper.PURCHASE_ORDER_OF_PN", "PurchaseOrderDate"));
  }

  public void projectProfitBatch(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    RESPONSE.responseString(ctx, ds.batchQuery(params, "CostProfitMapper.PROJECT_PROFIT", "OrderDate"));
  }

  public void downloadFile(RoutingContext ctx) {
    JsonObject params = REQUEST.getQueryJson(ctx);
    String fileName = params.getString("fileName");

    if (fileName.contains("..") || fileName.startsWith("/")) {
      ctx.response().setStatusCode(403).end("forbidden");
      return;
    }

    var resp = ctx.response();
    resp.putHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

    resp.sendFile(fileName)
        .onSuccess(v -> {
          // 正常完成
        })
        .onFailure(cause -> {
          // jar中找不到资源、读取异常都会进这里
          log.warn("文件下载失败, fileName={}", fileName, cause);
          if (!resp.ended()) {
            resp.setStatusCode(404).end("file not found");
          }
        });
  }

}