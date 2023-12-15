/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 21:35:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-12-14 14:09:12                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sageassistantserver.model.SupplierDelayHistory;
import com.da.sageassistantserver.model.SupplierDeliveryHistory;
import com.da.sageassistantserver.model.SupplierDetails;
import com.da.sageassistantserver.model.SupplierName;
import com.da.sageassistantserver.model.SupplierOpenItems;
import com.da.sageassistantserver.model.SupplierSummaryAmount;
import com.da.sageassistantserver.model.SupplierSummaryQty;
import com.da.sageassistantserver.service.SupplierService;

@CrossOrigin
@RestController
public class SupplierController {

    @Autowired
    private SupplierService SupplierService;

    @GetMapping("/Data/SupplierHelper")
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

    @GetMapping("/Data/SupplierDetails")
    public List<SupplierDetails> getSupplierDetails(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierDetails(SupplierCode));
    }

    @GetMapping("/Data/SupplierTotalAmount")
    public List<SupplierSummaryAmount> getSupplierTotalAmount(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierTotalAmount(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierTotalProjectQty")
    public List<SupplierSummaryQty> getSupplierTotalProjectQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierTotalProjectQty(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierTotalItemQty")
    public List<SupplierSummaryQty> getSupplierTotalItemQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierTotalItemQty(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierTotalQty")
    public List<SupplierSummaryQty> getSupplierTotalQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierTotalQty(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierTotalProductQty")
    public List<SupplierSummaryQty> getSupplierTotalProductQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierTotalProductQty(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierOpenAmount")
    public List<SupplierSummaryAmount> getSupplierOpenAmount(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierOpenAmount(SupplierCode));
    }

    @GetMapping("/Data/SupplierOpenProjectQty")
    public List<SupplierSummaryQty> getSupplierOpenProjectQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierOpenProjectQty(SupplierCode));
    }

    @GetMapping("/Data/SupplierOpenItemQty")
    public List<SupplierSummaryQty> getSupplierOpenItemQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierOpenItemQty(SupplierCode));
    }

    @GetMapping("/Data/SupplierOpenQty")
    public List<SupplierSummaryQty> getSupplierOpenQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierOpenQty(SupplierCode));
    }

    @GetMapping("/Data/SupplierOpenProductQty")
    public List<SupplierSummaryQty> getSupplierOpenProductQty(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierOpenProductQty(SupplierCode));
    }

    @GetMapping("/Data/SupplierDeliveryHistory")
    public List<SupplierDeliveryHistory> getSupplierDeliveryHistory(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierDeliveryHistory(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierDelayHistory")
    public List<SupplierDelayHistory> getSupplierDelayHistory(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode,
            @RequestParam(value = "dateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
            @RequestParam(value = "dateTo", required = false, defaultValue = "1999-12-31") String DateTo) {
        return (SupplierService.getSupplierDelayHistory(SupplierCode, DateFrom, DateTo + " 23:59:59.999"));
    }

    @GetMapping("/Data/SupplierOpenItems")
    public List<SupplierOpenItems> getSupplierOpenItems(
            @RequestParam(value = "supplierCode", required = false, defaultValue = "NULL") String SupplierCode) {
        return (SupplierService.getSupplierOpenItems(SupplierCode));
    }

    @GetMapping("/Data/PurchaseDate")
    public String getPurchaseDate(
            @RequestParam(value = "purchaseNO", required = true, defaultValue = "NULL") String PurchaseNO) {
        return SupplierService.getPurchaseDate(PurchaseNO);
    }
}
