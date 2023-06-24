/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 22:30:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 18:20:33                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/FinancialController.java             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.service.FinancialService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class FinancialController {


    @Autowired
    private FinancialService financialService;

    @GetMapping("/Data/FinancialBalance")
    public String getFinancialBalance(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
        @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO
    ) {
        if (AccountNO.equals("")) {
            return JSON.toJSONString(financialService.getAccountBalanceForAll(Site, Year) );
        } else {
            return JSON.toJSONString(
                financialService.getAccountBalanceForAccountNO(Site, Year, AccountNO)
                
            );
        }
    }

    @GetMapping("/Data/FinancialBalanceM")
    public String getFinancialBalanceA(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
        @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO
    ) {
        return getFinancialBalanceCDMB(Site, Year, AccountNO, "M");
    }

    @GetMapping("/Data/FinancialBalanceB")
    public String getFinancialBalanceB(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
        @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO
    ) {
        return getFinancialBalanceCDMB(Site, Year, AccountNO, "B");
    }

    @GetMapping("/Data/FinancialBalanceC")
    public String getFinancialBalanceC(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
        @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO
    ) {
        return getFinancialBalanceCDMB(Site, Year, AccountNO, "C");
    }

    @GetMapping("/Data/FinancialBalanceD")
    public String getFinancialBalanceD(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "Year", required = false, defaultValue = "") String Year,
        @RequestParam(value = "AccountNO", required = false, defaultValue = "") String AccountNO
    ) {
        return getFinancialBalanceCDMB(Site, Year, AccountNO, "D");
    }

    private String getFinancialBalanceCDMB(String Site, String Year, String AccountNO, String Cat) {
        if (Site.equals("") || Year.equals("")) {
            log.info("Site or Year is empty");
            return "Must set Site and Year";
        }
        if (AccountNO.equals("")) {
            log.info("AccountNO is empty");
            return "Must set AccountNO, if more than one AccountNO, use ',' between AccountNOs";
        }
        return JSON.toJSONString(
            financialService.getAccountBalanceForAccountNOByCat(Site, Year, Cat, AccountNO)
            
        );
    }

    @GetMapping("/Data/FinancialInvoicePay")
    public String getFinancialInvoicePay(
        @RequestParam(value = "Site", required = true, defaultValue = "ZHU") String Site,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            financialService.getInvoicePay(Site, CustomerCode, DateFrom, DateTo)
            
        );
    }

    @GetMapping("/Data/FinancialInvoicePayPro")
    public String getFinancialInvoicePayPro(
        @RequestParam(value = "Site", required = true, defaultValue = "ZHU") String Site,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            financialService.getInvoicePayPro(Site, CustomerCode, DateFrom, DateTo)
            
        );
    }
}
