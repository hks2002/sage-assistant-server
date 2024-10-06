/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-31 16:29:00                                                                      *
 * @LastEditDate          : 2025-07-27 22:04:39                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.CustomerSummaryAmount;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByCustomer;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sage.assistant.model.DeliveryLines;
import com.da.sage.assistant.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class DeliveryController {
  private final DeliveryService deliveryService;

  @GetMapping("/DeliverySummaryAmount")
  public List<CustomerSummaryAmount> getDeliverySummaryAmount(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (deliveryService.getDeliverySummaryAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval));
  }

  @GetMapping("/DeliverySummaryAmountTotalUSD")
  public Integer getDeliverySummaryAmountTotalUSD(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo) {
    return (deliveryService.getDeliverySummaryAmountTotalUSD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"));
  }

  @GetMapping("/DeliverySummaryAmountTopByCustomer")
  public List<CustomerSummaryAmountTopByCustomer> getDeliverySummaryAmountTopByCustomer(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (deliveryService.getDeliverySummaryAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)));
  }

  @GetMapping("/DeliverySummaryAmountTopByPNFamily")
  public List<CustomerSummaryAmountTopByPNFamily> getDeliverySummaryAmountTopByPNFamily(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "OrderType", required = true) String OrderType,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (deliveryService.getDeliverySummaryAmountTopByPNFamily(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)));
  }

  @GetMapping("/DeliveryLinesCnt")
  public Integer getDeliveryLinesCnt(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (deliveryService.getDeliveryLinesCnt(
        Site,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"));
  }

  @GetMapping("/DeliveryLines")
  public List<DeliveryLines> getDeliveryLines(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "Offset", required = true) String Offset,
      @RequestParam(value = "Limit", required = true) String Limit) {
    return (deliveryService.getDeliveryLines(
        Site,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)));
  }
}
