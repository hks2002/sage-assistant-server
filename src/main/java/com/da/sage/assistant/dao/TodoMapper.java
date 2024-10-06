/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditDate          : 2025-07-27 20:39:38                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.DeadPurchaseLine;
import com.da.sage.assistant.model.TodoClosedWO;
import com.da.sage.assistant.model.TodoDealWithOrderLine;
import com.da.sage.assistant.model.TodoDelivery;
import com.da.sage.assistant.model.TodoInvoice;
import com.da.sage.assistant.model.TodoLongTimeNC;
import com.da.sage.assistant.model.TodoLongTimeNoQC;
import com.da.sage.assistant.model.TodoPurchaseBom;
import com.da.sage.assistant.model.TodoReceive;

@Mapper
public interface TodoMapper {
  List<TodoDelivery> findTodoDeliveryBySite(@Param("Site") String Site);

  List<TodoReceive> findTodoReceiveBySite(@Param("Site") String Site);

  List<TodoDealWithOrderLine> findTodoDealWithOrderLineBySite(@Param("Site") String site);

  List<TodoPurchaseBom> findTodoPurchaseBomBySite(@Param("Site") String Site);

  List<TodoClosedWO> findTodoClosedWOBySite(@Param("Site") String Site);

  List<TodoInvoice> findNoInvoicePOBySite(@Param("Site") String Site);

  List<DeadPurchaseLine> findDeadPurchaseLineBySite(@Param("Site") String Site);

  List<TodoReceive> findNoAckDatePO(@Param("Site") String Site);

  List<TodoReceive> findWrongProjectPO(@Param("Site") String Site);

  List<TodoReceive> findLongTimeNoReceive(@Param("Site") String Site, @Param("Days") Integer Days);

  List<TodoInvoice> findLongTimeNoInvoice(@Param("Site") String Site, @Param("Days") Integer Days);

  List<TodoLongTimeNC> findLongTimeNC(@Param("Site") String Site, @Param("Days") Integer Days);

  List<TodoLongTimeNoQC> findLongTimeNoQC(@Param("Site") String Site, @Param("Days") Integer Days);

}
