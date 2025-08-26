/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-31 16:29:00                                                                      *
 * @LastEditDate          : 2025-08-25 19:11:27                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/


package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.CustomerDOD;
import com.da.sage.assistant.model.CustomerDetails;
import com.da.sage.assistant.model.CustomerName;
import com.da.sage.assistant.model.CustomerOTD;
import com.da.sage.assistant.model.CustomerOrder;
import com.da.sage.assistant.model.CustomerSummaryAmountByTarget;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByCustomer;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByRepresentative;
import com.da.sage.assistant.model.CustomerSummaryCountByTarget;
import com.da.sage.assistant.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping("/CustomerHelper")
  public List<CustomerName> getCustomerName(
      @RequestParam(value = "customerName", required = true) String CustomerCodeOrName,
      @RequestParam(value = "count", required = false, defaultValue = "50") Integer count) {
    if (CustomerCodeOrName.equals("%%")) {
      CustomerName name = new CustomerName();
      name.setCustomerName("ALL");
      name.setCustomerCode("%%");
      return (List.of(name));
    }
    return (customerService.getCustomerByCodeOrName(CustomerCodeOrName, count));
  }

  @GetMapping("/CustomerDetails")
  public List<CustomerDetails> getCustomerDetails(
      @RequestParam(value = "customerCode", required = true) String CustomerCode) {
    return (customerService.getCustomerDetails(CustomerCode));
  }

  @GetMapping("/CustomerSummaryAmountByTarget")
  public List<CustomerSummaryAmountByTarget> getCustomerSummaryAmount(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (customerService.getCustomerSummaryAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/CustomerSummaryCountByTarget")
  public List<CustomerSummaryCountByTarget> getCustomerSummaryCount(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (customerService.getCustomerSummaryCount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/CustomerSummaryAmountTopByCustomer")
  public List<CustomerSummaryAmountTopByCustomer> getCustomerSummaryAmountTopByCustomer(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (customerService.getCustomerSummaryAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)));
  }

  @GetMapping("/CustomerSummaryAmountTopByRepresentative")
  public List<CustomerSummaryAmountTopByRepresentative> getCustomerSummaryAmountTopByRepresentative(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (customerService.getCustomerSummaryAmountTopByRepresentative(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)));
  }

  @GetMapping("/CustomerSummaryAmountTopByPNFamily")
  public List<CustomerSummaryAmountTopByPNFamily> getCustomerSummaryAmountTopByPNFamily(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (customerService.getCustomerSummaryAmountTopByPNFamily(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)));
  }

  @GetMapping("/CustomerOTD")
  public List<CustomerOTD> getCustomerOTD(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (customerService.getCustomerOTD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/CustomerDOD")
  public List<CustomerDOD> getCustomerDOD(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo) {
    return (customerService.getCustomerDOD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"));
  }

  @GetMapping("/CustomerOrdersCnt")
  public Integer getCustomerOrdersCnt(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateType", required = true) String DateType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "OrderStatus", required = true) String OrderStatus,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (customerService.getCustomerOrdersCnt(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59",
        OrderStatus));
  }

  @GetMapping("/CustomerOrders")
  public List<CustomerOrder> getCustomerOrders(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateType", required = true) String DateType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "OrderStatus", required = true) String OrderStatus,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (customerService.getCustomerOrders(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59",
        OrderStatus,
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)));
  }
}
