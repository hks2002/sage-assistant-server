/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-31 16:25:00                                                                      *
 * @LastEditDate          : 2025-08-07 00:16:12                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.CustomerSummaryAmountByTarget;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByCustomer;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sage.assistant.model.DeliveryLines;

@Mapper
public interface DeliveryMapper {
  List<CustomerSummaryAmountByTarget> findDeliveryPlanSumAmount(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<CustomerSummaryAmountTopByCustomer> findDeliveryPlanSumAmountTopByCustomer(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  List<CustomerSummaryAmountByTarget> findDeliverySumAmount(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<CustomerSummaryAmountTopByCustomer> findDeliverySumAmountTopByCustomer(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  List<CustomerSummaryAmountTopByPNFamily> findDeliverySumAmountTopByPNFamily(
      @Param("Site") String Site,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  Integer findDeliveryLinesCnt(
      @Param("Site") String Site,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo);

  List<DeliveryLines> findDeliveryLines(
      @Param("Site") String Site,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);
}
