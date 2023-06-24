/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 21:57:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 20:09:05                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/InvoiceController.java               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import com.alibaba.fastjson2.JSON;
import com.da.sageassistantserver.service.InvoiceService;
import com.da.sageassistantserver.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/Data/InvoiceNO")
    public String getInvoiceNO(
        @RequestParam(value = "InvoiceNO", required = false, defaultValue = "NULL") String InvoiceNO,
        @RequestParam(value = "Count", required = false, defaultValue = "10") Integer Count
    ) {
        return invoiceService.findInvoiceNOByInvoiceNO(InvoiceNO, Count);
    }

    @GetMapping("/Data/InvoiceHeaderByInvoiceNO")
    public String getInvoiceHeader(
        @RequestParam(value = "InvoiceNO", required = false, defaultValue = "NULL") String InvoiceNO
    ) {
        return JSON.toJSONString(invoiceService.findInvoiceHeaderByInvoiceNO(InvoiceNO), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/InvoiceBodyByInvoiceNO")
    public String getInvoiceBody(
        @RequestParam(value = "InvoiceNO", required = false, defaultValue = "NULL") String InvoiceNO
    ) {
        return JSON.toJSONString(invoiceService.findInvoiceBodyByInvoiceNO(InvoiceNO), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/InvoiceHeaderByFaPiao")
    public String getInvoiceHeaderByFaPiao(
        @RequestParam(value = "FaPiao", required = false, defaultValue = "NULL") String FaPiao
    ) {
        return JSON.toJSONString(invoiceService.findInvoiceHeaderByFaPiao(FaPiao), Utils.JSON2Ctx());
    }

    @GetMapping("/Data/InvoiceBodyByFaPiao")
    public String getInvoiceBodyByFaPiao(
        @RequestParam(value = "FaPiao", required = false, defaultValue = "NULL") String FaPiao
    ) {
        return JSON.toJSONString(invoiceService.findInvoiceBodyByFaPiao(FaPiao), Utils.JSON2Ctx());
    }
}
