/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-11 09:32:59                                                                        *
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

    router.get("/s/all-sites").handler(dqs::allSites);
    router.get("/s/so-by-like").handler(dqs::soFindByLike);
    router.get("/s/project-by-like").handler(dqs::projectFindByLike);

    router.get("/s/pn-by-like").handler(dqs::pnFindByLike);
    router.get("/s/pn-industrialization").handler(dqs::pnIndustrialization);
    router.get("/s/pn-all-pn-in-root").handler(dqs::pnAllPnInRoot);
    router.get("/s/pn-stock-info").handler(dqs::pnStockInfo);
    router.get("/s/pn-option-pn").handler(dqs::pnOptionPN);
    router.get("/s/pn-option-pn-short").handler(dqs::pnOptionPNShort);
    router.get("/s/pn-program").handler(dqs::pnProgram);
    router.get("/s/pn-family").handler(dqs::pnFamily);
    router.get("/s/pn-group1").handler(dqs::pnGroup1);
    router.get("/s/pn-group2").handler(dqs::pnGroup2);
    router.get("/s/pn-group3").handler(dqs::pnGroup3);

    router.get("/s/stock-history").handler(dqs::stockHistory);
    router.get("/s/stock-history-cnt").handler(dqs::stockHistoryCnt);
    router.get("/s/stock-summary").handler(dqs::stockSummary);

    router.get("/s/sales-delivery").handler(dqs::salesDelivery);
    router.get("/s/sales-order").handler(dqs::salesOrder);
    router.get("/s/sales-quote").handler(dqs::salesQuote);
    router.get("/s/sales-order-cost").handler(dqs::salesOrderCost);
    router.get("/s/sales-order-line-amount-sum").handler(dqs::salesOrderLineAmountSum);
    router.get("/s/sales-order-line-count-sum").handler(dqs::salesOrderLineCountSum);
    router.get("/s/sales-order-line-amount-top-n").handler(dqs::salesOrderLineAmountTopN);
    router.get("/s/sales-order-line-count-top-n").handler(dqs::salesOrderLineCountTopN);
    router.get("/s/sales-order-line-otd").handler(dqs::salesOrderLineOTD);
    router.get("/s/sales-order-line-dod").handler(dqs::salesOrderLineDOD);
    router.get("/s/sales-order-line-details").handler(dqs::salesOrderLineDetails);
    router.get("/s/sales-order-line-details-cnt").handler(dqs::salesOrderLineDetailsCnt);

    router.get("/s/delivery-line-amount-sum").handler(dqs::deliveryLineAmountSum);
    router.get("/s/delivery-line-count-sum").handler(dqs::deliveryLineCountSum);
    router.get("/s/delivery-line-amount-top-n").handler(dqs::deliveryLineAmountTopN);
    router.get("/s/delivery-line-count-top-n").handler(dqs::deliveryLineCountTopN);
    router.get("/s/delivery-line-details").handler(dqs::deliveryLineDetails);
    router.get("/s/delivery-line-details-cnt").handler(dqs::deliveryLineDetailsCnt);

    router.get("/s/project-profit").handler(dqs::projectProfit);

    router.get("/s/representative-by-like").handler(dqs::representativeFindByLike);
    router.get("/s/buyer-by-like").handler(dqs::buyerFindByLike);
    router.get("/s/logistics-operator-by-like").handler(dqs::logisticsOperatorFindByLike);

    router.get("/s/purchase-order").handler(dqs::purchaseOrder);
    router.get("/s/purchase-receive").handler(dqs::purchaseReceive);
    router.get("/s/purchase-order-line-amount-sum").handler(dqs::purchaseOrderLineAmountSum);
    router.get("/s/purchase-order-line-count-sum").handler(dqs::purchaseOrderLineCountSum);
    router.get("/s/purchase-order-line-amount-top-n").handler(dqs::purchaseOrderLineAmountTopN);
    router.get("/s/purchase-order-line-count-top-n").handler(dqs::purchaseOrderLineCountTopN);
    router.get("/s/purchase-order-line-otd").handler(dqs::purchaseOrderLineOTD);
    router.get("/s/purchase-order-line-dod").handler(dqs::purchaseOrderLineDOD);
    router.get("/s/purchase-order-line-details").handler(dqs::purchaseOrderLineDetails);
    router.get("/s/purchase-order-line-details-cnt").handler(dqs::purchaseOrderLineDetailsCnt);

    router.get("/s/bp-country").handler(dqs::bpCountry);
    router.get("/s/bp-us-state").handler(dqs::bpUsState);
    router.get("/s/bp-city").handler(dqs::bpCity);

    router.get("/s/bp-customer-category").handler(dqs::bpCustomerCategory);
    router.get("/s/bp-customer-type").handler(dqs::bpCustomerType);
    router.get("/s/bp-customer-geo-region").handler(dqs::bpCustomerGeoRegion);
    router.get("/s/bp-customer-by-like").handler(dqs::bpCustomerFindByLike);
    router.get("/s/bp-customer-info").handler(dqs::bpCustomerInfo);

    router.get("/s/bp-supplier-category").handler(dqs::bpSupplierCategory);
    router.get("/s/bp-supplier-type").handler(dqs::bpSupplierType);
    router.get("/s/bp-supplier-sub-type").handler(dqs::bpSupplierSubType);
    router.get("/s/bp-supplier-geo-region").handler(dqs::bpSupplierGeoRegion);
    router.get("/s/bp-supplier-by-like").handler(dqs::bpSupplierFindByLike);
    router.get("/s/bp-supplier-info").handler(dqs::bpSupplierInfo);

    router.get("/b/sales-order").handler(dqsb::salesOrderBatch);
    router.get("/b/sales-order-cost").handler(dqsb::salesOrderCostBatch);
    router.get("/b/sales-quote").handler(dqsb::salesQuoteBatch);
    router.get("/b/project-profit").handler(dqsb::projectProfitBatch);
    router.get("/b/purchase-order").handler(dqsb::purchaseOrderBatch);
    router.get("/b/download-file").handler(dqsb::downloadFile);

    return router;
  }
}
