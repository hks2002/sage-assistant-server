/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 20:39:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 20:19:37                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/StockController.java                 *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.service.StockService;
import com.da.sageassistantserver.utils.Utils;

@CrossOrigin
@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/Data/CheckPN")
    public String checkPN(@RequestParam(value = "PN", required = false, defaultValue = "--") String PN) {
        return stockService.checkPN(PN);
    }

    @GetMapping("/Data/StockOptionPN")
    public String getStockOptionPN(@RequestParam(value = "PN", required = false, defaultValue = "--") String PN) {
        return stockService.getStockOptionPN(PN);
    }

    @GetMapping("/Data/StockQty")
    public String getStockQty(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "PN", required = false, defaultValue = "--") String PN
    ) {
        return JSON.toJSONString(stockService.getStockQty(Site, PN), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/StockSummary")
    public String getStockSummary(@RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
        return stockService.getStockSummary(Site).toString();
    }

    @GetMapping("/Data/StockHistory")
    public String getStockHistory(
        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
        @RequestParam(value = "PnOrName", required = false, defaultValue = "%%") String PnOrName,
        @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
        @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo
    ) {
        return JSON.toJSONString(stockService.getStockHistory(Site, PnOrName, DateFrom, DateTo), Utils.JSON2Ctx());
    }
}
