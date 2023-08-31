/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 20:13:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2023-08-31 23:41:46                                                                       *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/StatusController.java                 *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.model.TobeClosedWO;
import com.da.sageassistantserver.model.TobeDealWithOrderLine;
import com.da.sageassistantserver.model.TobeDelivery;
import com.da.sageassistantserver.model.TobePurchaseBom;
import com.da.sageassistantserver.model.TobeReceive;
import com.da.sageassistantserver.model.TobeTrackingPurchaseOrderLine;
import com.da.sageassistantserver.model.TobeTrackingQALine;
import com.da.sageassistantserver.model.TobeTrackingReceiptLine;
import com.da.sageassistantserver.model.TobeTrackingSalesOrderLine;
import com.da.sageassistantserver.service.StatusService;

@CrossOrigin
@RestController
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/Data/TobeDelivery")
    public List<TobeDelivery> getTobeDelivery(@RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
        return statusService.findTobeDeliveryBySite(Site);
    }

    @GetMapping("/Data/TobeReceive")
    public List<TobeReceive> getTobeReceive(@RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
        return statusService.findTobeReceiveBySite(Site);
    }

    @GetMapping("/Data/TobeDealWithOrderLine")
    public List<TobeDealWithOrderLine> getTobeDealWithOrderLineBySite(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site
    ) {
        return (statusService.findTobeDealWithOrderLineBySite(Site));
    }

    @GetMapping("/Data/TobePurchaseBom")
    public List<TobePurchaseBom> getTobePurchaseBom(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site
    ) {
        return (statusService.findTobePurchaseBomBySite(Site));
    }

    @GetMapping("/Data/TobeClosedWO")
    public List<TobeClosedWO> getTobeClosedWO(@RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
        return (statusService.findTobeClosedWOBySite(Site));
    }

    @GetMapping("/Data/TobeTrackingSalesOrderLineCnt")
    public String getTobeTracking(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode
    ) {
        return String.valueOf(
            statusService.findTobeTrackingSalesOrderLineCnt(Site, OrderType, CustomerCode, VendorCode)
        );
    }

    @GetMapping("/Data/TobeTrackingSalesOrderLine")
    public List<TobeTrackingSalesOrderLine> getTobeTrackingSalesOrderLine(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
        @RequestParam(value = "OrderBy", required = false, defaultValue = "DaysLeft") String OrderBy,
        @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
        @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
        @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit
    ) {
        return statusService.findTobeTrackingSalesOrderLine(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            OrderBy,
            Descending,
            Offset,
            Limit
        );
    }

    @GetMapping("/Data/TobeTrackingBOMLine")
    public String getTobeTrackingBOMLine(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
        @RequestParam(value = "OrderBy", required = false, defaultValue = "DaysLeft") String OrderBy,
        @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
        @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
        @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit
    ) {
        return JSON.toJSONString(
            statusService.findTobeTrackingBOMLine(
                Site,
                OrderType,
                CustomerCode,
                VendorCode,
                OrderBy,
                Descending,
                Offset,
                Limit
            )
        );
    }

    @GetMapping("/Data/TobeTrackingPurchaseOrderLine")
    public List<TobeTrackingPurchaseOrderLine> getTobeTrackingPurchaseOrderLine(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
        @RequestParam(value = "OrderBy", required = false, defaultValue = "DaysLeft") String OrderBy,
        @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
        @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
        @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit
    ) {
        return (
            statusService.findTobeTrackingPurchaseOrderLine(
                Site,
                OrderType,
                CustomerCode,
                VendorCode,
                OrderBy,
                Descending,
                Offset,
                Limit
            )
        );
    }

    @GetMapping("/Data/TobeTrackingReceiptLine")
    public List<TobeTrackingReceiptLine> getTobeTrackingReceiptLine(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
        @RequestParam(value = "OrderBy", required = false, defaultValue = "DaysLeft") String OrderBy,
        @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
        @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
        @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit
    ) {
        return (
            statusService.findTobeTrackingReceiptLine(
                Site,
                OrderType,
                CustomerCode,
                VendorCode,
                OrderBy,
                Descending,
                Offset,
                Limit
            )
        );
    }

    @GetMapping("/Data/TobeTrackingQALine")
    public List<TobeTrackingQALine> getTobeTrackingQALine(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
        @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
        @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
        @RequestParam(value = "OrderBy", required = false, defaultValue = "DaysLeft") String OrderBy,
        @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
        @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
        @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit
    ) {
        return (
            statusService.findTobeTrackingQALine(
                Site,
                OrderType,
                CustomerCode,
                VendorCode,
                OrderBy,
                Descending,
                Offset,
                Limit
            )
        );
    }
}
