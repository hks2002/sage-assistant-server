/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-25 15:19:00                                                                       *
 * @LastEditDate          : 2025-07-27 22:09:26                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.service.SageActionService;
import com.da.sage.assistant.utils.ResponseJson;

@CrossOrigin
@RestController
@RequestMapping("/sa-api")
public class SageRecorderController {

  @PutMapping("/Sage/DeliveryPlanDate")
  public JSONObject updateSageDeliveryPlanDate(
      @RequestHeader(value = "authorization", required = false) String Auth,
      @RequestParam(value = "OrderNO", required = false) String OrderNO,
      @RequestParam(value = "Line", required = false) Integer Line,
      @RequestParam(value = "PlanDate", required = false) String PlanDate) {
    if (Auth == null) {
      return ResponseJson.missingAuth();
    }
    if (OrderNO == null) {
      return ResponseJson.paraRequired("OrderNO");
    }
    if (Line == null) {
      return ResponseJson.paraRequired("Line");
    }
    return SageActionService.updateSageField(
        Auth,
        "SalesOrder",
        OrderNO,
        Line,
        "EA33",
        PlanDate);
  }

  @PutMapping("/Sage/ProjectStatus")
  public JSONObject updateSageProjectStatus(
      @RequestHeader(value = "authorization", required = false) String Auth,
      @RequestParam(value = "OrderNO", required = false) String OrderNO,
      @RequestParam(value = "Line", required = false) Integer Line,
      @RequestParam(value = "Status", required = false) String Status) {
    if (Auth == null) {
      return ResponseJson.missingAuth();
    }
    if (OrderNO == null) {
      return ResponseJson.paraRequired("OrderNO");
    }
    if (Line == null) {
      return ResponseJson.paraRequired("Line");
    }
    return SageActionService.updateSageField(
        Auth,
        "SalesOrder",
        OrderNO,
        Line,
        "EA72",
        Status);
  }

  @PutMapping("/Sage/ProjectBlockReason")
  public JSONObject updateSageProjectBlockReason(
      @RequestHeader(value = "authorization", required = false) String Auth,
      @RequestParam(value = "OrderNO", required = false) String OrderNO,
      @RequestParam(value = "Line", required = false) Integer Line,
      @RequestParam(value = "Reason", required = false) String Reason) {
    if (Auth == null) {
      return ResponseJson.missingAuth();
    }
    if (OrderNO == null) {
      return ResponseJson.paraRequired("OrderNO");
    }
    if (Line == null) {
      return ResponseJson.paraRequired("Line");
    }
    return SageActionService.updateSageField(
        Auth,
        "SalesOrder",
        OrderNO,
        Line,
        "EA73",
        Reason);
  }

  @PutMapping("/Sage/PurchaseAckDate")
  public JSONObject updateSagePurchaseAckDate(
      @RequestHeader(value = "authorization", required = false) String Auth,
      @RequestParam(value = "OrderNO", required = false) String OrderNO,
      @RequestParam(value = "Line", required = false) Integer Line,
      @RequestParam(value = "AckDate", required = false) String AckDate) {
    if (Auth == null) {
      return ResponseJson.missingAuth();
    }
    if (OrderNO == null) {
      return ResponseJson.paraRequired("OrderNO");
    }
    if (Line == null) {
      return ResponseJson.paraRequired("Line");
    }
    return SageActionService.updateSageField(
        Auth,
        "PurchaseOrder",
        OrderNO,
        Line,
        "CA66",
        AckDate);
  }
}
