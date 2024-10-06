/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 20:13:00                                                                       *
 * @LastEditDate          : 2025-07-27 21:07:46                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.TrackingBOMLine;
import com.da.sage.assistant.model.TrackingNCLine;
import com.da.sage.assistant.model.TrackingPurchaseOrderLine;
import com.da.sage.assistant.model.TrackingReceiptLine;
import com.da.sage.assistant.model.TrackingSalesOrderLine;
import com.da.sage.assistant.service.TrackingService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class TrackingController {

  private final TrackingService trackingService;

  @GetMapping("/TrackingSalesOrderLineCnt")
  public String getTracking(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO) {
    return String.valueOf(
        trackingService.findTrackingSalesOrderLineCnt(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            ProjectNO));
  }

  @GetMapping("/TrackingSalesOrderLine")
  public List<TrackingSalesOrderLine> getTrackingSalesOrderLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "OrderBy", required = false, defaultValue = "daysleft") String OrderBy,
      @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit) {
    return trackingService.findTrackingSalesOrderLine(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
  }

  @GetMapping("/TrackingBOMLine")
  public List<TrackingBOMLine> getTrackingBOMLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "OrderBy", required = false, defaultValue = "daysleft") String OrderBy,
      @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit) {
    return trackingService.findTrackingBOMLine(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
  }

  @GetMapping("/TrackingPurchaseOrderLine")
  public List<TrackingPurchaseOrderLine> getTrackingPurchaseOrderLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "OrderBy", required = false, defaultValue = "daysleft") String OrderBy,
      @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit) {
    return trackingService.findTrackingPurchaseOrderLine(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
  }

  @GetMapping("/TrackingReceiptLine")
  public List<TrackingReceiptLine> getTrackingReceiptLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "OrderBy", required = false, defaultValue = "daysleft") String OrderBy,
      @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit) {
    return trackingService.findTrackingReceiptLine(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
  }

  @GetMapping("/TrackingNCLine")
  public List<TrackingNCLine> getTrackingNCLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "OrderType", required = false, defaultValue = "") String OrderType,
      @RequestParam(value = "CustomerCode", required = false, defaultValue = "") String CustomerCode,
      @RequestParam(value = "VendorCode", required = false, defaultValue = "") String VendorCode,
      @RequestParam(value = "ProjectNO", required = false, defaultValue = "") String ProjectNO,
      @RequestParam(value = "OrderBy", required = false, defaultValue = "daysleft") String OrderBy,
      @RequestParam(value = "Descending", required = false, defaultValue = "ASC") String Descending,
      @RequestParam(value = "Offset", required = false, defaultValue = "0") Integer Offset,
      @RequestParam(value = "Limit", required = false, defaultValue = "50") Integer Limit) {
    return trackingService.findTrackingNCLine(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
  }
}
