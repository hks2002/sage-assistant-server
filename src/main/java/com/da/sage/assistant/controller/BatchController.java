/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 21:46:00                                                                       *
 * @LastEditDate          : 2025-07-27 14:59:39                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.service.BatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class BatchController {

  private final BatchService batchService;

  @GetMapping("/Batch/Sales")
  public String analysesSales(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "PN", required = false, defaultValue = "") String PnRoot,
      @RequestParam(value = "Currency", required = false, defaultValue = "USD") String Currency,
      @RequestParam(value = "Target", required = false, defaultValue = "NetPrice") String Target,
      @RequestParam(value = "LastN", required = false, defaultValue = "1") String LastN) {
    if (PnRoot.isEmpty()) {
      return "http://sageassistant/sa-api/Batch/Sales?Site=SITE&PN=PN&Currency=USD&Target=NetPrice&LastN=1\nAvailable Target:NetPrice,OrderNO,OrderDate,CustomerCode,CustomerName,QTY";
    }
    return batchService.getSales(Site, PnRoot, Currency, Target, LastN);
  }

  @GetMapping("/Batch/Quote")
  public String analysesQuote(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "PN", required = false, defaultValue = "") String PnRoot,
      @RequestParam(value = "Currency", required = false, defaultValue = "USD") String Currency,
      @RequestParam(value = "Target", required = false, defaultValue = "NetPrice") String Target,
      @RequestParam(value = "LastN", required = false, defaultValue = "1") String LastN) {
    if (PnRoot.isEmpty()) {
      return "http://sageassistant/sa-api/Batch/Quote?Site=SITE&PN=PN&Currency=USD&Target=NetPrice&LastN=1\nAvailable Target:NetPrice,QuoteNO,QuoteDate,CustomerCode,CustomerName,OrderNO,OrderFlag,QTY";
    }
    return batchService.getQuote(Site, PnRoot, Currency, Target, LastN);
  }

  @GetMapping("/Batch/Purchase")
  public String analysesPurchase(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "PN", required = false, defaultValue = "") String PnRoot,
      @RequestParam(value = "Currency", required = false, defaultValue = "USD") String Currency,
      @RequestParam(value = "Target", required = false, defaultValue = "LocalCostWithTax") String Target,
      @RequestParam(value = "LastN", required = false, defaultValue = "1") String LastN) {
    if (PnRoot.isEmpty()) {
      return "http://sageassistant/sa-api/Batch/Purchase?Site=SITE&PN=PN&Currency=USD&Target=LocalCostWithTax&LastN=1\nAvailable Target:LocalCostNoTax,LocalCostWithTax,ProjectNO,PurchaseDate";
    }
    return batchService.getPurchase(Site, PnRoot, Currency, Target, LastN);
  }

}
