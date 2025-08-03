/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-31 16:25:00                                                                      *
 * @LastEditDate          : 2025-08-07 00:15:44                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.CustomerDOD;
import com.da.sage.assistant.model.CustomerDetails;
import com.da.sage.assistant.model.CustomerName;
import com.da.sage.assistant.model.CustomerOTD;
import com.da.sage.assistant.model.CustomerOrder;
import com.da.sage.assistant.model.CustomerSummaryAmountByTarget;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByCustomer;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByRepresentative;

@Mapper
public interface CustomerMapper {
  List<CustomerDetails> findCustomerDetailsByCode(
      @Param("CustomerCode") String CustomerCode);

  List<CustomerName> findCustomerByCodeOrName(
      @Param("CustomerCodeOrName") String CustomerCodeOrName,
      @Param("Count") Integer Count);

  List<CustomerSummaryAmountByTarget> findCustomerSumAmount(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<CustomerSummaryAmountTopByCustomer> findCustomerSumAmountTopByCustomer(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  List<CustomerSummaryAmountTopByRepresentative> findCustomerSumAmountTopByRepresentative(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  List<CustomerSummaryAmountTopByPNFamily> findCustomerSumAmountTopByPNFamily(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  List<CustomerOTD> findCustomerOTD(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<CustomerDOD> findCustomerDOD(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo);

  Integer findCustomerOrdersCnt(
      @Param("Site") String Site,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateType") String DateType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("OrderStatus") String OrderStatus);

  List<CustomerOrder> findCustomerOrders(
      @Param("Site") String Site,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateType") String DateType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("OrderStatus") String OrderStatus,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);
}
