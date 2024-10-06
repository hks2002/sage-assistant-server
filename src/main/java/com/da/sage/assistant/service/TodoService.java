/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 20:59:30                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.TodoMapper;
import com.da.sage.assistant.model.DeadPurchaseLine;
import com.da.sage.assistant.model.TodoClosedWO;
import com.da.sage.assistant.model.TodoDealWithOrderLine;
import com.da.sage.assistant.model.TodoDelivery;
import com.da.sage.assistant.model.TodoInvoice;
import com.da.sage.assistant.model.TodoLongTimeNC;
import com.da.sage.assistant.model.TodoLongTimeNoQC;
import com.da.sage.assistant.model.TodoPurchaseBom;
import com.da.sage.assistant.model.TodoReceive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoMapper todoMapper;

  public List<TodoDelivery> findTodoDeliveryBySite(String Site) {
    return todoMapper.findTodoDeliveryBySite(Site);
  }

  public List<TodoReceive> findTodoReceiveBySite(String Site) {
    return todoMapper.findTodoReceiveBySite(Site);
  }

  public List<TodoDealWithOrderLine> findTodoDealWithOrderLineBySite(String Site) {
    return todoMapper.findTodoDealWithOrderLineBySite(Site);
  }

  public List<TodoPurchaseBom> findTodoPurchaseBomBySite(String Site) {
    return todoMapper.findTodoPurchaseBomBySite(Site);
  }

  public List<TodoClosedWO> findTodoClosedWOBySite(String Site) {
    return todoMapper.findTodoClosedWOBySite(Site);
  }

  public List<TodoInvoice> findNoInvoicePOBySite(String Site) {
    return todoMapper.findNoInvoicePOBySite(Site);
  }

  public List<DeadPurchaseLine> findDeadPurchaseLineBySite(String Site) {
    return todoMapper.findDeadPurchaseLineBySite(Site);
  }

  public List<TodoReceive> findNoAckDatePOBySite(String Site) {
    return todoMapper.findNoAckDatePO(Site);
  }

  public List<TodoReceive> findWrongProjectPOBySite(String Site) {
    return todoMapper.findWrongProjectPO(Site);
  }

  public List<TodoReceive> findLongTimeNoReceiveBySite(String Site, Integer Days) {
    return todoMapper.findLongTimeNoReceive(Site, Days);
  }

  public List<TodoInvoice> findLongTimeNoInvoiceBySite(String Site, Integer Days) {
    return todoMapper.findLongTimeNoInvoice(Site, Days);
  }

  public List<TodoLongTimeNC> findLongTimeNCBySite(String Site, Integer days) {
    return todoMapper.findLongTimeNC(Site, days);
  }

  public List<TodoLongTimeNoQC> findLongTimeNoQCBySite(String Site, Integer days) {
    return todoMapper.findLongTimeNoQC(Site, days);
  }

}
