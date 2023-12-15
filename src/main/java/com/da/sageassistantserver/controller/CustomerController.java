/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-31 16:29:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-12-14 14:03:17                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sageassistantserver.model.CustomerDelayHistory;
import com.da.sageassistantserver.model.CustomerDeliveryHistory;
import com.da.sageassistantserver.model.CustomerDetails;
import com.da.sageassistantserver.model.CustomerName;
import com.da.sageassistantserver.model.CustomerOpenItems;
import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryQty;
import com.da.sageassistantserver.service.CustomerService;

@CrossOrigin
@RestController
public class CustomerController {

    @Autowired
    private CustomerService CustomerService;

    @GetMapping("/Data/CustomerHelper")
    public List<CustomerName> getCustomerName(
            @RequestParam(value = "customerName", required = false, defaultValue = "%%") String CustomerCodeOrName,
            @RequestParam(value = "count", required = false, defaultValue = "50") Integer count) {
        if (CustomerCodeOrName.equals("%%")) {
            CustomerName name = new CustomerName();
            name.setCustomerName("ALL");
            name.setCustomerCode("%%");
            return (List.of(name));
        }
        return (CustomerService.getCustomerByCodeOrName(CustomerCodeOrName, count));
    }

    @GetMapping("/Data/CustomerDetails")
    public List<CustomerDetails> getCustomerDetails(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerDetails(CustomerCode));
    }

    @GetMapping("/Data/CustomerTotalAmount")
    public List<CustomerSummaryAmount> getCustomerTotalAmount(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerTotalAmount(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerTotalProjectQty")
    public List<CustomerSummaryQty> getCustomerTotalProjectQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerTotalProjectQty(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerTotalItemQty")
    public List<CustomerSummaryQty> getCustomerTotalItemQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerTotalItemQty(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerTotalQty")
    public List<CustomerSummaryQty> getCustomerTotalQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerTotalQty(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerTotalProductQty")
    public List<CustomerSummaryQty> getCustomerTotalProductQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerTotalProductQty(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerOpenAmount")
    public List<CustomerSummaryAmount> getCustomerOpenAmount(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerOpenAmount(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenProjectQty")
    public List<CustomerSummaryQty> getCustomerOpenProjectQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerOpenProjectQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenItemQty")
    public List<CustomerSummaryQty> getCustomerOpenItemQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerOpenItemQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenQty")
    public List<CustomerSummaryQty> getCustomerOpenQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerOpenQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerOpenProductQty")
    public List<CustomerSummaryQty> getCustomerOpenProductQty(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerOpenProductQty(CustomerCode));
    }

    @GetMapping("/Data/CustomerDeliveryHistory")
    public List<CustomerDeliveryHistory> getCustomerDeliveryHistory(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerDeliveryHistory(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerDelayHistory")
    public List<CustomerDelayHistory> getCustomerDelayHistory(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (CustomerService.getCustomerDelayHistory(CustomerCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/CustomerOpenItems")
    public List<CustomerOpenItems> getCustomerOpenItems(
            @RequestParam(value = "customerCode", required = false, defaultValue = "NULL") String CustomerCode) {
        return (CustomerService.getCustomerOpenItems(CustomerCode));
    }
}
