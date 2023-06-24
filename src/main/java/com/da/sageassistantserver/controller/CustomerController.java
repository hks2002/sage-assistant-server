/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-31 16:29:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 18:19:17                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/CustomerController.java              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.service.CustomerService;

@CrossOrigin
@RestController
public class CustomerController {

    @Autowired
    private CustomerService CustomerService;

    @GetMapping("/Data/CustomerHelper")
    public String getCustomerName(
        @RequestParam(value = "CustomerName", required = false, defaultValue = "%%") String CustomerCodeOrName,
        @RequestParam(value = "Count", required = false, defaultValue = "50") Integer count
    ) {
        return JSON.toJSONString(CustomerService.getCustomerByCodeOrName(CustomerCodeOrName, count));
    }

    @GetMapping("/Data/CustomerDetails")
    public String getCustomerDetails(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerDetails(CustomerCode));
    }

    @GetMapping("/Data/CustomerTotalAmount")
    public String getCustomerTotalAmount(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerTotalAmount(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerTotalProjectQty")
    public String getCustomerTotalProjectQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerTotalProjectQty(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerTotalItemQty")
    public String getCustomerTotalItemQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerTotalItemQty(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerTotalQty")
    public String getCustomerTotalQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerTotalQty(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerTotalProductQty")
    public String getCustomerTotalProductQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerTotalProductQty(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerOpenAmount")
    public String getCustomerOpenAmount(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerOpenAmount(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenProjectQty")
    public String getCustomerOpenProjectQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerOpenProjectQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenItemQty")
    public String getCustomerOpenItemQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerOpenItemQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenQty")
    public String getCustomerOpenQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerOpenQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenProductQty")
    public String getCustomerOpenProductQty(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerOpenProductQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerDeliveryHistory")
    public String getCustomerDeliveryHistory(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerDeliveryHistory(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerDelayHistory")
    public String getCustomerDelayHistory(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(CustomerService.getCustomerDelayHistory(CustomerCode, DateFrom, DateTo));
    }

    @GetMapping("/Data/CustomerOpenItems")
    public String getCustomerOpenItems(
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "NULL") String CustomerCode
    ) {
        return JSON.toJSONString(CustomerService.getCustomerOpenItems(CustomerCode));
    }
}
