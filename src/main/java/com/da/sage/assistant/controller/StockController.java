/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 20:39:00                                                                       *
 * @LastEditDate          : 2025-07-22 14:01:40                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.StockHistory;
import com.da.sage.assistant.model.StockSummary;
import com.da.sage.assistant.service.StockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class StockController {

  private final StockService stockService;

  @GetMapping("/StockQty")
  public String getStockQty(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "PN", required = false, defaultValue = "--") String PN) {
    return stockService.getStockQty(Site, PN);
  }

  @GetMapping("/StockSummary")
  public List<StockSummary> getStockSummary(
      @RequestParam(value = "site", required = false, defaultValue = "ZHU") String Site) {
    return (stockService.getStockSummary(Site));
  }

  @GetMapping("/StockHistory")
  public List<StockHistory> getStockHistory(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "PnOrName", required = false, defaultValue = "%%") String PnOrName,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
    return (stockService.getStockHistory(
        Site,
        PnOrName,
        DateFrom,
        DateTo + " 23:59:59.999"));
  }
}
