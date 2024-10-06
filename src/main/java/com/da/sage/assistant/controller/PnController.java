/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 19:06:00                                                                       *
 * @LastEditDate          : 2025-07-25 14:27:13                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.ComponentDeliveryDuration;
import com.da.sage.assistant.model.ComponentPurchaseHistory;
import com.da.sage.assistant.model.PnDetails;
import com.da.sage.assistant.model.PnIndustrialization;
import com.da.sage.assistant.model.PnOptionPn;
import com.da.sage.assistant.model.PnRootPn;
import com.da.sage.assistant.model.ProductCostHistory;
import com.da.sage.assistant.model.ProductDeliveryDuration;
import com.da.sage.assistant.model.ProductQuoteHistory;
import com.da.sage.assistant.model.ProductSalesHistory;
import com.da.sage.assistant.model.StockInfo;
import com.da.sage.assistant.service.PnService;
import com.da.sage.assistant.utils.PNUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class PnController {

  private final PnService pnService;

  @GetMapping("/CheckPN")
  public String checkPN(
      @RequestParam(value = "PN", required = false, defaultValue = "--") String PN) {
    return pnService.checkPN(PN);
  }

  @GetMapping("/OptionPN")
  public List<PnOptionPn> getStockOptionPN(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "--") String PN) {
    return pnService.findOptionPN(PN);
  }

  @GetMapping("/PNHelper")
  public List<PnRootPn> getPNs(
      @RequestParam(value = "PN", required = false, defaultValue = "%%") String pnOrPnRoot,
      @RequestParam(value = "Count", required = false, defaultValue = "20") Integer Count) {
    return (pnService.findPnByStartWith(pnOrPnRoot, Count));
  }

  @GetMapping("/MakeShortPn")
  public String makeShortPn(
      @RequestParam(value = "Pn", required = true) String PN) {
    return PNUtils.makeShortPn(PN);
  }

  @GetMapping("/PNsInFamily")
  public List<PnDetails> getPNsInFamily(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findAllPnByPnRoot(PnRoot));
  }

  @GetMapping("/PNIndustrialization")
  public List<PnIndustrialization> getIndustrialization(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findIndustrializationByPnRoot(PnRoot));
  }

  @GetMapping("/ProductSalesHistory")
  public List<ProductSalesHistory> getProductSalesHistory(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findProductSalesHistoryByPnRoot(PnRoot));
  }

  @GetMapping("/ProductQuoteHistory")
  public List<ProductQuoteHistory> getProductQuoteHistory(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findProductQuoteHistoryByPnRoot(PnRoot));
  }

  @GetMapping("/ProductCostHistory")
  public List<ProductCostHistory> getProductCostHistory(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findProductCostHistoryByPnRoot(PnRoot));
  }

  @GetMapping("/ProductDeliveryDuration")
  public List<ProductDeliveryDuration> getProductDeliveryDuration(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findProductDeliveryDurationByPnRoot(PnRoot));
  }

  @GetMapping("/ProductInventoryStock")
  public List<StockInfo> getProductInventoryStock(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findProductStockInfoByPnRoot(PnRoot));
  }

  @GetMapping("/ComponentDeliveryDuration")
  public List<ComponentDeliveryDuration> getComponentDeliveryDuration(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findComponentDeliveryDurationByPnRoot(PnRoot));
  }

  @GetMapping("/ComponentInventoryStock")
  public List<StockInfo> getComponentInventoryStock(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findComponentStockInfoByPnRoot(PnRoot));
  }

  @GetMapping("/ComponentPurchaseHistory")
  public List<ComponentPurchaseHistory> getComponentPurchaseHistory(
      @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot) {
    return (pnService.findComponentPurchaseHistoryByPnRoot(PnRoot));
  }
}
