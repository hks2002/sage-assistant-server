/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 21:35:00                                                                      *
 * @LastEditDate          : 2025-07-31 14:16:55                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.QcNCHistory;
import com.da.sage.assistant.model.QcNCHistoryTiny;
import com.da.sage.assistant.model.QcNCSummary;
import com.da.sage.assistant.model.QcQPY;
import com.da.sage.assistant.model.QcQPYTop;
import com.da.sage.assistant.service.QcService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sa-api")
public class QcController {

  private final QcService qcService;

  @GetMapping("/QcQPY")
  public List<QcQPY> getQcQPY(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "QcType", required = true) String QcType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (qcService.getQcQPY(
        Site,
        SupplierCode,
        CustomerCode,
        QcType,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/QcQPYTop")
  public List<QcQPYTop> getQcQPYTop(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit,
      @RequestParam(value = "Sort", required = true) String Sort) {
    return (qcService.getQcQPYTop(
        Site,
        SupplierType,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit),
        Sort));
  }

  @GetMapping("/QcNCSummary")
  public List<QcNCSummary> getQcNCSummary(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "NCCatCode", required = true) String NCCatCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (qcService.getQcNCSummary(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/QcNCHistoryCnt")
  public Integer getQcNCHistoryCnt(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "NCCatCode", required = true) String NCCatCode,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo) {
    return (qcService.getQcNCHistoryCnt(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo + " 23:59:59"));
  }

  @GetMapping("/QcNCHistory")
  public List<QcNCHistory> getQcNCHistory(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "NCCatCode", required = true) String NCCatCode,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (qcService.getQcNCHistory(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)));
  }

  @GetMapping("/QcNCHistoryTiny")
  public List<QcNCHistoryTiny> getQcNCHistoryTiny(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "NCCatCode", required = true) String NCCatCode,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (qcService.getQcNCHistoryTiny(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)));
  }
}
