/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 20:13:00                                                                       *
 * @LastEditDate          : 2025-07-27 21:00:57                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.DeadPurchaseLine;
import com.da.sage.assistant.model.TodoClosedWO;
import com.da.sage.assistant.model.TodoDealWithOrderLine;
import com.da.sage.assistant.model.TodoDelivery;
import com.da.sage.assistant.model.TodoInvoice;
import com.da.sage.assistant.model.TodoLongTimeNC;
import com.da.sage.assistant.model.TodoLongTimeNoQC;
import com.da.sage.assistant.model.TodoPurchaseBom;
import com.da.sage.assistant.model.TodoReceive;
import com.da.sage.assistant.service.TodoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class StatusController {

  private final TodoService todoService;

  @GetMapping("/TodoDelivery")
  public List<TodoDelivery> getTodoDelivery(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return todoService.findTodoDeliveryBySite(Site);
  }

  @GetMapping("/TodoReceive")
  public List<TodoReceive> getTodoReceive(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return todoService.findTodoReceiveBySite(Site);
  }

  @GetMapping("/TodoDealWithOrderLine")
  public List<TodoDealWithOrderLine> getTodoDealWithOrderLineBySite(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findTodoDealWithOrderLineBySite(Site));
  }

  @GetMapping("/TodoPurchaseBom")
  public List<TodoPurchaseBom> getTodoPurchaseBom(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findTodoPurchaseBomBySite(Site));
  }

  @GetMapping("/TodoClosedWO")
  public List<TodoClosedWO> getTodoClosedWO(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findTodoClosedWOBySite(Site));
  }

  @GetMapping("/NoAckDatePO")
  public List<TodoReceive> getNoActPO(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findNoAckDatePOBySite(Site));
  }

  @GetMapping("/WrongProjectPO")
  public List<TodoReceive> getWrongProjectPO(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findWrongProjectPOBySite(Site));
  }

  @GetMapping("/NoInvoicePO")
  public List<TodoInvoice> getNoInvoicePO(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findNoInvoicePOBySite(Site));
  }

  @GetMapping("/DeadPurchaseLine")
  public List<DeadPurchaseLine> getDeadPurchaseLine(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site) {
    return (todoService.findDeadPurchaseLineBySite(Site));
  }

  @GetMapping("/LongTimeNC")
  public List<TodoLongTimeNC> getLongTimeNC(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Days", required = false, defaultValue = "60") Integer Days) {
    return (todoService.findLongTimeNCBySite(Site, Days));
  }

  @GetMapping("/LongTimeNoQC")
  public List<TodoLongTimeNoQC> getLongTimeNoQC(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "Days", required = false, defaultValue = "60") Integer Days) {
    return (todoService.findLongTimeNoQCBySite(Site, Days));
  }
}
