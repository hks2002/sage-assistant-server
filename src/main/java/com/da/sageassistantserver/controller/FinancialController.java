/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 22:30:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-24 14:23:53                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import com.da.sageassistantserver.model.FinancialBalance;
import com.da.sageassistantserver.model.FinancialInvoicePay;
import com.da.sageassistantserver.model.FinancialInvoiceSumAmount;
import com.da.sageassistantserver.service.FinancialService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
public class FinancialController {

  @Autowired
  private FinancialService financialService;

  @GetMapping("/Data/FinancialBalance")
  public List<FinancialBalance> getFinancialBalance(
    @RequestParam(
      value = "Site",
      required = false,
      defaultValue = "ZHU"
    ) String Site,
    @RequestParam(
      value = "Year",
      required = false,
      defaultValue = ""
    ) String Year,
    @RequestParam(
      value = "AccountNO",
      required = false,
      defaultValue = ""
    ) String AccountNO
  ) {
    if (AccountNO.equals("")) {
      return (financialService.getAccountBalanceForAll(Site, Year));
    } else {
      return (
        financialService.getAccountBalanceForAccountNO(Site, Year, AccountNO)
      );
    }
  }

  @GetMapping("/Data/FinancialBalanceM")
  public List<FinancialBalance> getFinancialBalanceA(
    @RequestParam(
      value = "Site",
      required = false,
      defaultValue = "ZHU"
    ) String Site,
    @RequestParam(
      value = "Year",
      required = false,
      defaultValue = ""
    ) String Year,
    @RequestParam(
      value = "AccountNO",
      required = false,
      defaultValue = ""
    ) String AccountNO
  ) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "M");
  }

  @GetMapping("/Data/FinancialBalanceB")
  public List<FinancialBalance> getFinancialBalanceB(
    @RequestParam(
      value = "Site",
      required = false,
      defaultValue = "ZHU"
    ) String Site,
    @RequestParam(
      value = "Year",
      required = false,
      defaultValue = ""
    ) String Year,
    @RequestParam(
      value = "AccountNO",
      required = false,
      defaultValue = ""
    ) String AccountNO
  ) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "B");
  }

  @GetMapping("/Data/FinancialBalanceC")
  public List<FinancialBalance> getFinancialBalanceC(
    @RequestParam(
      value = "Site",
      required = false,
      defaultValue = "ZHU"
    ) String Site,
    @RequestParam(
      value = "Year",
      required = false,
      defaultValue = ""
    ) String Year,
    @RequestParam(
      value = "AccountNO",
      required = false,
      defaultValue = ""
    ) String AccountNO
  ) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "C");
  }

  @GetMapping("/Data/FinancialBalanceD")
  public List<FinancialBalance> getFinancialBalanceD(
    @RequestParam(
      value = "Site",
      required = false,
      defaultValue = "ZHU"
    ) String Site,
    @RequestParam(
      value = "Year",
      required = false,
      defaultValue = ""
    ) String Year,
    @RequestParam(
      value = "AccountNO",
      required = false,
      defaultValue = ""
    ) String AccountNO
  ) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "D");
  }

  private List<FinancialBalance> getFinancialBalanceCDMB(
    String Site,
    String Year,
    String AccountNO,
    String Cat
  ) {
    List<FinancialBalance> list1 = new ArrayList<>();

    if (Site.equals("") || Year.equals("")) {
      log.info("Site or Year is empty, Must set Site and Year");
      return list1;
    }
    if (AccountNO.equals("")) {
      log.info(
        "AccountNO is empty, Must set AccountNO, if more than one AccountNO, use ',' between AccountNOs"
      );
      return list1;
    }
    return (
      financialService.getAccountBalanceForAccountNOByCat(
        Site,
        Year,
        Cat,
        AccountNO
      )
    );
  }

  @GetMapping("/Data/FinancialInvoiceSumAmount")
  public List<FinancialInvoiceSumAmount> getFinancialInvoiceSumAmount(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateType", required = true) String DateType,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "PayStatus", required = true) String PayStatus,
    @RequestParam(value = "Interval", required = true) String Interval
  ) {
    return (
      financialService.getInvoiceSumAmount(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59.999",
        PayStatus,
        Interval
      )
    );
  }

  @GetMapping("/Data/FinancialInvoicePay")
  public List<FinancialInvoicePay> getFinancialInvoicePay(
    @RequestParam(value = "Site", required = true) String Site,
    @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
    @RequestParam(value = "DateType", required = true) String DateType,
    @RequestParam(value = "DateFrom", required = true) String DateFrom,
    @RequestParam(value = "DateTo", required = true) String DateTo,
    @RequestParam(value = "PayStatus", required = true) String PayStatus
  ) {
    return (
      financialService.getInvoicePay(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59.999",
        PayStatus
      )
    );
  }
}
