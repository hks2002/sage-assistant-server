/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 19:06:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 20:26:43                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/PnController.java                    *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.da.sageassistantserver.service.PnService;
import com.da.sageassistantserver.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class PnController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private PnService pnService;

    @GetMapping("/Data/PNHelper")
    public String getPNs(
        @RequestParam(value = "PN", required = false, defaultValue = "%%") String pnOrPnRoot,
        @RequestParam(value = "Count", required = false, defaultValue = "200") Integer Count
    ) {
        log.info("PNHelper: " + JSONArray.toJSONString(pnService.findPnByStartWith(pnOrPnRoot, Count)));
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
        return JSON.toJSONString(pnService.findSalesHistoryByPnRoot(PnRoot), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/QuoteHistory")
    public String getQuoteHistory(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findQuoteHistoryByPnRoot(PnRoot), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/CostHistory")
    public String getCostHistory(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findCostHistoryByPnRoot(PnRoot), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/DeliveryDuration")
    public String getDeliveryDuration(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findDeliveryDurationByPnRoot(PnRoot), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/InventoryStock")
    public String getInventoryStock(
        @RequestParam(value = "PnRoot", required = false, defaultValue = "NULL") String PnRoot
    ) {
        return JSON.toJSONString(pnService.findStockInfoByPnRoot(PnRoot), Utils.JSON2Ctx());
    }
}
