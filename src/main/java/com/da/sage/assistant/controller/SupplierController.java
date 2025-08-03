/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 21:35:00                                                                      *
 * @LastEditDate          : 2025-07-30 18:27:24                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.SupplierDOD;
import com.da.sage.assistant.model.SupplierDetails;
import com.da.sage.assistant.model.SupplierName;
import com.da.sage.assistant.model.SupplierOTD;
import com.da.sage.assistant.model.SupplierOTDTop;
import com.da.sage.assistant.model.SupplierOrder;
import com.da.sage.assistant.model.SupplierSummaryAmountByTarget;
import com.da.sage.assistant.model.SupplierSummaryAmountTop;
import com.da.sage.assistant.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sa-api")
public class SupplierController {

  private final SupplierService SupplierService;

  @GetMapping("/BPHelper")
  public String getBPName(
      @RequestParam(value = "bpCode", required = false, defaultValue = "XXXX") String bpCode) {
    return SupplierService.getBusinessPartnerByCode(bpCode);
  }

  @GetMapping("/SupplierHelper")
  public List<SupplierName> getSupplierName(
      @RequestParam(value = "supplierName", required = false, defaultValue = "%%") String SupplierCodeOrName,
      @RequestParam(value = "count", required = false, defaultValue = "50") Integer count) {
    if (SupplierCodeOrName.equals("%%")) {
      SupplierName name = new SupplierName();
      name.setSupplierName("ALL");
      name.setSupplierCode("%%");
      return (List.of(name));
    }
    return (SupplierService.getSupplierByCodeOrName(SupplierCodeOrName, count));
  }

  @GetMapping("/SupplierDetails")
  public List<SupplierDetails> getSupplierDetails(
      @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
    return (SupplierService.getSupplierDetails(SupplierCode));
  }

  @GetMapping("/SupplierSummaryAmountByTarget")
  public List<SupplierSummaryAmountByTarget> getSupplierSummaryAmount(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (SupplierService.getSupplierSummaryAmount(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/SupplierSummaryAmountTop")
  public List<SupplierSummaryAmountTop> getSupplierSummaryAmountTop(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (SupplierService.getSupplierSummaryAmountTop(
        Site,
        SupplierType,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)));
  }

  @GetMapping("/SupplierOTD")
  public List<SupplierOTD> getSupplierOTD(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (SupplierService.getSupplierOTD(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/SupplierOTDTop")
  public List<SupplierOTDTop> getSupplierOTDTop(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Sort", required = true) String Sort,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (SupplierService.getSupplierOTDTop(
        Site,
        SupplierType,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59",
        Sort,
        Integer.parseInt(Limit)));
  }

  @GetMapping("/SupplierDOD")
  public List<SupplierDOD> getSupplierDOD(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo) {
    return (SupplierService.getSupplierDOD(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59"));
  }

  @GetMapping("/SupplierOrdersCnt")
  public Integer getSupplierOrdersCnt(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateType", required = true) String DateType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "OrderStatus", required = true) String OrderStatus) {
    return (SupplierService.getSupplierOrdersCnt(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateType,
        DateFrom,
        DateTo + " 23:59:59",
        OrderStatus));
  }

  @GetMapping("/SupplierOrders")
  public List<SupplierOrder> getSupplierOrders(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "SupplierType", required = true) String SupplierType,
      @RequestParam(value = "SupplierCode", required = true) String SupplierCode,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "DateType", required = true) String DateType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "OrderStatus", required = true) String OrderStatus,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (SupplierService.getSupplierOrders(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateType,
        DateFrom,
        DateTo + " 23:59:59",
        OrderStatus,
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)));
  }

}
