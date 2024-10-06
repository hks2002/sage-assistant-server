/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:29:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:26:43                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import com.da.sageassistantserver.model.CustomerDOD;
import com.da.sageassistantserver.model.CustomerDetails;
import com.da.sageassistantserver.model.CustomerName;
import com.da.sageassistantserver.model.CustomerOTD;
import com.da.sageassistantserver.model.CustomerOrder;
import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByCustomer;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByRepresentative;
import com.da.sageassistantserver.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping("/Data/CustomerHelper")
  public List<CustomerName> getCustomerName(
    @RequestParam(
      value = "customerName",
      required = true
    ) String CustomerCodeOrName,
    @RequestParam(
      value = "count",
      required = false,
      defaultValue = "50"
    ) Integer count
  ) {
    if (CustomerCodeOrName.equals("%%")) {
      CustomerName name = new CustomerName();
      name.setCustomerName("ALL");
      name.setCustomerCode("%%");
      return (List.of(name));
    }
    return (customerService.getCustomerByCodeOrName(CustomerCodeOrName, count));
  }

  @GetMapping("/Data/CustomerDetails")
  public List<CustomerDetails> getCustomerDetails(
    @RequestParam(value = "customerCode", required = true) String CustomerCode
  ) {
    return (customerService.getCustomerDetails(CustomerCode));
  }

  @GetMapping("/Data/CustomerSummaryAmount")
  public List<CustomerSummaryAmount> getCustomerSummaryAmount(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Interval", required = true) String Interval
  ) {
    return (
      customerService.getCustomerSummaryAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval
      )
    );
  }

  @GetMapping("/Data/CustomerSummaryAmountTotalUSD")
  public Integer getCustomerSummaryAmountTotalUSD(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo
  ) {
    return (
      customerService.getCustomerSummaryAmountTotalUSD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"
      )
    );
  }

  @GetMapping("/Data/CustomerSummaryAmountTopByCustomer")
  public List<CustomerSummaryAmountTopByCustomer> getCustomerSummaryAmountTopByCustomer(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      customerService.getCustomerSummaryAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)
      )
    );
  }

  @GetMapping("/Data/CustomerSummaryAmountTopByRepresentative")
  public List<CustomerSummaryAmountTopByRepresentative> getCustomerSummaryAmountTopByRepresentative(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      customerService.getCustomerSummaryAmountTopByRepresentative(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)
      )
    );
  }

  @GetMapping("/Data/CustomerSummaryAmountTopByPNFamily")
  public List<CustomerSummaryAmountTopByPNFamily> getCustomerSummaryAmountTopByPNFamily(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      customerService.getCustomerSummaryAmountTopByPNFamily(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Integer.parseInt(Limit)
      )
    );
  }

  @GetMapping("/Data/CustomerOTD")
  public List<CustomerOTD> getCustomerOTD(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "Interval", required = true) String Interval
  ) {
    return (
      customerService.getCustomerOTD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59",
        Interval
      )
    );
  }

  @GetMapping("/Data/CustomerDOD")
  public List<CustomerDOD> getCustomerDOD(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "OrderType", required = true) String OrderType,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo
  ) {
    return (
      customerService.getCustomerDOD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo + " 23:59:59"
      )
    );
  }

  @GetMapping("/Data/CustomerOrdersCnt")
  public Integer getCustomerOrdersCnt(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateType", required = true) String DateType,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "OrderStatus", required = true) String OrderStatus,
    @RequestParam(value = "Offset", required = true) String Offset,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      customerService.getCustomerOrdersCnt(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59",
        OrderStatus
      )
    );
  }

  @GetMapping("/Data/CustomerOrders")
  public List<CustomerOrder> getCustomerOrders(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateType", required = true) String DateType,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "OrderStatus", required = true) String OrderStatus,
    @RequestParam(value = "Offset", required = true) String Offset,
    @RequestParam(value = "Limit", required = true) String Limit
  ) {
    return (
      customerService.getCustomerOrders(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59",
        OrderStatus,
        Integer.parseInt(Offset),
        Integer.parseInt(Limit)
      )
    );
  }
}
