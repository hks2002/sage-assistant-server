/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 22:30:00                                                                      *
 * @LastEditDate          : 2025-07-27 22:03:34                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.FinancialBalance;
import com.da.sage.assistant.model.FinancialInvoicePay;
import com.da.sage.assistant.model.FinancialInvoiceSumAmount;
import com.da.sage.assistant.service.FinancialService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class FinancialController {

  private final FinancialService financialService;

  @GetMapping("/FinancialBalance")
  public List<FinancialBalance> getFinancialBalance(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
      @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO) {
    if (AccountNO.equals("")) {
      return (financialService.getAccountBalanceForAll(Site, Year));
    } else {
      return (financialService.getAccountBalanceForAccountNO(Site, Year, AccountNO));
    }
  }

  @GetMapping("/FinancialBalanceM")
  public List<FinancialBalance> getFinancialBalanceA(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
      @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "M");
  }

  @GetMapping("/FinancialBalanceB")
  public List<FinancialBalance> getFinancialBalanceB(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
      @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "B");
  }

  @GetMapping("/FinancialBalanceC")
  public List<FinancialBalance> getFinancialBalanceC(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
      @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "C");
  }

  @GetMapping("/FinancialBalanceD")
  public List<FinancialBalance> getFinancialBalanceD(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
      @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO) {
    return getFinancialBalanceCDMB(Site, Year, AccountNO, "D");
  }

  private List<FinancialBalance> getFinancialBalanceCDMB(
      String Site,
      String Year,
      String AccountNO,
      String Cat) {
    List<FinancialBalance> list1 = new ArrayList<>();

    if (Site.equals("") || Year.equals("")) {
      log.info("Site or Year is empty, Must set Site and Year");
      return list1;
    }
    if (AccountNO.equals("")) {
      log.info(
          "AccountNO is empty, Must set AccountNO, if more than one AccountNO, use ',' between AccountNOs");
      return list1;
    }
    return (financialService.getAccountBalanceForAccountNOByCat(
        Site,
        Year,
        Cat,
        AccountNO));
  }

  @GetMapping("/FinancialInvoiceSumAmount")
  public List<FinancialInvoiceSumAmount> getFinancialInvoiceSumAmount(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateType", required = true) String DateType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "PayStatus", required = true) String PayStatus,
      @RequestParam(value = "Interval", required = true) String Interval) {
    return (financialService.getInvoiceSumAmount(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59.999",
        PayStatus,
        Interval));
  }

  @GetMapping("/FinancialInvoicePay")
  public List<FinancialInvoicePay> getFinancialInvoicePay(
      @RequestParam(value = "Site", required = true) String Site,
      @RequestParam(value = "CustomerCode", required = true) String CustomerCode,
      @RequestParam(value = "DateType", required = true) String DateType,
      @RequestParam(value = "DateFrom", required = true) String DateFrom,
      @RequestParam(value = "DateTo", required = true) String DateTo,
      @RequestParam(value = "PayStatus", required = true) String PayStatus) {
    return (financialService.getInvoicePay(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo + " 23:59:59.999",
        PayStatus));
  }
}
