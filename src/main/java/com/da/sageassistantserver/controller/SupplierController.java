/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 21:35:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 20:22:40                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/SupplierController.java              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.service.SupplierService;
import com.da.sageassistantserver.utils.Utils;

@CrossOrigin
@RestController
public class SupplierController {

    @Autowired
    private SupplierService SupplierService;

    @GetMapping("/Data/SupplierHelper")
    public String getSupplierName(
        @RequestParam(value = "SupplierName", required = false, defaultValue = "%%") String SupplierCodeOrName,
        @RequestParam(value = "Count", required = false, defaultValue = "50") Integer count
    ) {
        return JSON.toJSONString(SupplierService.getSupplierByCodeOrName(SupplierCodeOrName, count), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierDetails")
    public String getSupplierDetails(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierDetails(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierTotalAmount")
    public String getSupplierTotalAmount(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            SupplierService.getSupplierTotalAmount(SupplierCode, DateFrom, DateTo),
            Utils.JSON2Ctx()
        );
    }

    @GetMapping("/Data/SupplierTotalProjectQty")
    public String getSupplierTotalProjectQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            SupplierService.getSupplierTotalProjectQty(SupplierCode, DateFrom, DateTo),
            Utils.JSON2Ctx()
        );
    }

    @GetMapping("/Data/SupplierTotalItemQty")
    public String getSupplierTotalItemQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            SupplierService.getSupplierTotalItemQty(SupplierCode, DateFrom, DateTo),
            Utils.JSON2Ctx()
        );
    }

    @GetMapping("/Data/SupplierTotalQty")
    public String getSupplierTotalQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(SupplierService.getSupplierTotalQty(SupplierCode, DateFrom, DateTo), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierTotalProductQty")
    public String getSupplierTotalProductQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            SupplierService.getSupplierTotalProductQty(SupplierCode, DateFrom, DateTo),
            Utils.JSON2Ctx()
        );
    }

    @GetMapping("/Data/SupplierOpenAmount")
    public String getSupplierOpenAmount(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierOpenAmount(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierOpenProjectQty")
    public String getSupplierOpenProjectQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierOpenProjectQty(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierOpenItemQty")
    public String getSupplierOpenItemQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierOpenItemQty(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierOpenQty")
    public String getSupplierOpenQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierOpenQty(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierOpenProductQty")
    public String getSupplierOpenProductQty(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierOpenProductQty(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/SupplierDeliveryHistory")
    public String getSupplierDeliveryHistory(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            SupplierService.getSupplierDeliveryHistory(SupplierCode, DateFrom, DateTo),
            Utils.JSON2Ctx()
        );
    }

    @GetMapping("/Data/SupplierDelayHistory")
    public String getSupplierDelayHistory(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(
            SupplierService.getSupplierDelayHistory(SupplierCode, DateFrom, DateTo),
            Utils.JSON2Ctx()
        );
    }

    @GetMapping("/Data/SupplierOpenItems")
    public String getSupplierOpenItems(
        @RequestParam(value = "SupplierCode", required = false, defaultValue = "NULL") String SupplierCode
    ) {
        return JSON.toJSONString(SupplierService.getSupplierOpenItems(SupplierCode), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/PurchaseDate")
    public String getPurchaseDate(
        @RequestParam(value = "PurchaseNO", required = true, defaultValue = "NULL") String PurchaseNO
    ) {
        return SupplierService.getPurchaseDate(PurchaseNO);
    }
}
