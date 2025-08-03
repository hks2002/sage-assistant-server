/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-31 16:27:00                                                                      *
 * @LastEditDate          : 2025-08-07 00:16:01                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.DeliveryMapper;
import com.da.sage.assistant.model.CustomerSummaryAmountByTarget;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByCustomer;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sage.assistant.model.DeliveryLines;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryMapper deliveryMapper;

  public List<CustomerSummaryAmountByTarget> getDeliveryPlanSummaryAmount(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      String Interval) {
    return deliveryMapper.findDeliveryPlanSumAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<CustomerSummaryAmountTopByCustomer> getDeliveryPlanSummaryAmountTopByCustomer(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return deliveryMapper.findDeliveryPlanSumAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Limit);
  }

  public List<CustomerSummaryAmountByTarget> getDeliverySummaryAmount(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      String Interval) {
    return deliveryMapper.findDeliverySumAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<CustomerSummaryAmountTopByCustomer> getDeliverySummaryAmountTopByCustomer(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return deliveryMapper.findDeliverySumAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Limit);
  }

  public List<CustomerSummaryAmountTopByPNFamily> getDeliverySummaryAmountTopByPNFamily(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return deliveryMapper.findDeliverySumAmountTopByPNFamily(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Limit);
  }

  public Integer getDeliveryLinesCnt(
      String Site,
      String CustomerCode,
      String DateFrom,
      String DateTo) {
    return deliveryMapper.findDeliveryLinesCnt(
        Site,
        CustomerCode,
        DateFrom,
        DateTo);
  }

  public List<DeliveryLines> getDeliveryLines(
      String Site,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Offset,
      Integer Limit) {
    return deliveryMapper.findDeliveryLines(
        Site,
        CustomerCode,
        DateFrom,
        DateTo,
        Offset,
        Limit);
  }
}
