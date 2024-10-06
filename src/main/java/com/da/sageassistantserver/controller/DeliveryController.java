/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:29:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:26:05                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByCustomer;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sageassistantserver.model.DeliveryLines;
import com.da.sageassistantserver.service.DeliveryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DeliveryController {

  @Autowired
  private DeliveryService deliveryService;

  @GetMapping("/Data/DeliverySummaryAmount")
  public List<CustomerSummaryAmount> getDeliverySummaryAmount(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Interval", required = true) String Interval
  ) {
    return (
      deliveryService.getDeliverySummaryAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval
      )
    );
  }

  @GetMapping("/Data/DeliverySummaryAmountTotalUSD")
  public Integer getDeliverySummaryAmountTotalUSD(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo
  ) {
    return (
      deliveryService.getDeliverySummaryAmountTotalUSD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"
      )
    );
  }

  @GetMapping("/Data/DeliverySummaryAmountTopByCustomer")
  public List<CustomerSummaryAmountTopByCustomer> getDeliverySummaryAmountTopByCustomer(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      deliveryService.getDeliverySummaryAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)
      )
    );
  }

  @GetMapping("/Data/DeliverySummaryAmountTopByPNFamily")
  public List<CustomerSummaryAmountTopByPNFamily> getDeliverySummaryAmountTopByPNFamily(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      deliveryService.getDeliverySummaryAmountTopByPNFamily(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)
      )
    );
  }

  @GetMapping("/Data/DeliveryLinesCnt")
  public Integer getDeliveryLinesCnt(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Offset", required = true) String Offset,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      deliveryService.getDeliveryLinesCnt(
        Site,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"
      )
    );
  }

  @GetMapping("/Data/DeliveryLines")
  public List<DeliveryLines> getDeliveryLines(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Offset", required = true) String Offset,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      deliveryService.getDeliveryLines(
        Site,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)
      )
    );
  }
}
