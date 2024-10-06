/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 22:30:00                                                                       *
 * @LastEditDate          : 2025-07-27 22:07:08                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.LinksDeliveryLine;
import com.da.sage.assistant.model.LinksInvoiceLine;
import com.da.sage.assistant.model.LinksPurchaseLine;
import com.da.sage.assistant.model.LinksReceiptLine;
import com.da.sage.assistant.model.LinksSalesLine;
import com.da.sage.assistant.service.LinksService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class LinksController {

  private final LinksService linksService;

  @GetMapping("/LinksLineCnt")
  public String getTobeTracking(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO) {
    return String.valueOf(
        linksService.findLinksLineCnt(
            Site,
            ProjectNO,
            OrderType,
            DateFrom,
            DateTo + " 23:59:59.999",
            CustomerCode,
            VendorCode));
  }

  @GetMapping("/LinksSalesLine")
  public List<LinksSalesLine> findLinksSalesLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "10") Integer Limit) {
    return (linksService.findLinksSalesLine(
        Site,
        ProjectNO,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59.999",
        CustomerCode,
        VendorCode,
        Offset,
        Limit));
  }

  @GetMapping("/LinksPurchaseLine")
  public List<LinksPurchaseLine> findLinksPurchaseLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "10") Integer Limit) {
    return linksService.findLinksPurchaseLine(
        Site,
        ProjectNO,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59.999",
        CustomerCode,
        VendorCode,
        Offset,
        Limit);
  }

  @GetMapping("/LinksReceiptLine")
  public List<LinksReceiptLine> findLinksReceiptLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "10") Integer Limit) {
    return linksService.findLinksReceiptLine(
        Site,
        ProjectNO,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59.999",
        CustomerCode,
        VendorCode,
        Offset,
        Limit);
  }

  @GetMapping("/LinksDeliveryLine")
  public List<LinksDeliveryLine> findLinksDeliveryLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "10") Integer Limit) {
    return linksService.findLinksDeliveryLine(
        Site,
        ProjectNO,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59.999",
        CustomerCode,
        VendorCode,
        Offset,
        Limit);
  }

  @GetMapping("/LinksInvoiceLine")
  public List<LinksInvoiceLine> findLinksInvoiceLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2000-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "1999-12-31") String DateTo,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "10") Integer Limit) {
    return linksService.findLinksInvoiceLine(
        Site,
        ProjectNO,
        OrderType,
        DateFrom,
        DateTo + " 23:59:59.999",
        CustomerCode,
        VendorCode,
        Offset,
        Limit);
  }
}
