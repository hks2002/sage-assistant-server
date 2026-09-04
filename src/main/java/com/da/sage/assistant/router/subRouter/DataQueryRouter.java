/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-04 09:30:12                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router.subRouter;

import com.da.sage.assistant.handler.DataQuerySageBatchHandler;
import com.da.sage.assistant.handler.DataQuerySageHandler;
import com.da.sage.assistant.handler.DataQueryUserHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQueryRouter {
  public static Router create(Vertx vertx) {
    Router router = Router.router(vertx);

    DataQueryUserHandler dqu = new DataQueryUserHandler();
    DataQuerySageHandler dqs = new DataQuerySageHandler();
    DataQuerySageBatchHandler dqsb = new DataQuerySageBatchHandler();

    router.get("/source").handler(dqu::source);
    router.post("/t").handler(dqu::test);
    router.get("/u/:db/:data_code").handler(dqu::query);

    router.get("/s/all-sites").handler(dqs::allsites);

    router.get("/s/pn-by-like").handler(dqs::pnFindByLike);
    router.get("/s/pn-industrialization").handler(dqs::pnIndustrialization);
    router.get("/s/pn-all-pn-in-root").handler(dqs::pnAllPnInRoot);
    router.get("/s/pn-stock-info").handler(dqs::pnStockInfo);
    router.get("/s/pn-option-pn").handler(dqs::pnOptionPN);

    router.get("/s/sales-delivery").handler(dqs::salesDelivery);
    router.get("/s/sales-order").handler(dqs::salesOrder);
    router.get("/s/sales-quote").handler(dqs::salesQuote);
    router.get("/s/sales-order-cost").handler(dqs::salesOrderCost);
    router.get("/s/sales-order-cost-details").handler(dqs::salesOrderCostDetails);

    router.get("/s/purchase-order").handler(dqs::purchaseOrder);
    router.get("/s/purchase-receive").handler(dqs::purchaseReceive);

    router.get("/b/sales-order").handler(dqsb::salesOrderBatch);
    router.get("/b/sales-order-cost").handler(dqsb::salesOrderCostBatch);
    router.get("/b/sales-order-cost-details").handler(dqsb::salesOrderCosDetailstBatch);
    router.get("/b/sales-quote").handler(dqsb::salesQuoteBatch);
    router.get("/b/project-profit").handler(dqsb::projectProfitBatch);
    router.get("/b/project-profit-details").handler(dqsb::projectProfitDetailsBatch);
    router.get("/b/purchase-order").handler(dqsb::purchaseOrderBatch);

    return router;
  }
}
