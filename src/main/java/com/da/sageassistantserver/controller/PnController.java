/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 19:06:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 22:40:25                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/PnController.java                    *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.service.PnService;
import com.da.sageassistantserver.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class PnController {


    @Autowired
    private PnService pnService;

    @GetMapping("/Data/PNHelper")
    public String getPNs(
        @RequestParam(value = "PN", required = false, defaultValue = "%%") String pnOrPnRoot,
        @RequestParam(value = "Count", required = false, defaultValue = "200") Integer Count
    ) {
        return JSON.toJSONString(pnService.findPnByStartWith(pnOrPnRoot, Count));
    }

    @GetMapping("/Data/MakeShortPn")
    public String makeShortPn(@RequestParam(value = "Pn", required = true) String PN) {
        return Utils.makeShortPn(PN);
    }

    @GetMapping("/Data/PNsInFamily")
    public String getPNsInFamily(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findAllPnByPnRoot(PnRoot));
    }

    @GetMapping("/Data/SalesHistory")
    public String getSalesHistory(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findSalesHistoryByPnRoot(PnRoot));
    }

    @GetMapping("/Data/QuoteHistory")
    public String getQuoteHistory(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findQuoteHistoryByPnRoot(PnRoot));
    }

    @GetMapping("/Data/CostHistory")
    public String getCostHistory(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findCostHistoryByPnRoot(PnRoot));
    }

    @GetMapping("/Data/DeliveryDuration")
    public String getDeliveryDuration(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findDeliveryDurationByPnRoot(PnRoot));
    }

    @GetMapping("/Data/InventoryStock")
    public String getInventoryStock(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findStockInfoByPnRoot(PnRoot));
    }
}
