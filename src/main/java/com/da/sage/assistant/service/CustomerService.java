/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-31 16:27:00                                                                      *
 * @LastEditDate          : 2025-08-07 00:15:40                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.CustomerMapper;
import com.da.sage.assistant.model.CustomerDOD;
import com.da.sage.assistant.model.CustomerDetails;
import com.da.sage.assistant.model.CustomerName;
import com.da.sage.assistant.model.CustomerOTD;
import com.da.sage.assistant.model.CustomerOrder;
import com.da.sage.assistant.model.CustomerSummaryAmountByTarget;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByCustomer;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sage.assistant.model.CustomerSummaryAmountTopByRepresentative;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerMapper customerMapper;

  public List<CustomerName> getCustomerByCodeOrName(
      String cond,
      Integer count) {
    if (cond.equals("%%")) {
      List<CustomerName> list = new ArrayList<>();

      CustomerName o = new CustomerName();
      o.setCustomerCode("ALL");
      o.setCustomerName("ALL");
      list.add(o);
      return list;
    }

    List<CustomerName> listPage = customerMapper.findCustomerByCodeOrName(
        "%" + cond + "%",
        count);

    return listPage;
  }

  public List<CustomerDetails> getCustomerDetails(
      @Param("CustomerCode") String CustomerCode) {
    return customerMapper.findCustomerDetailsByCode(CustomerCode);
  }

  public List<CustomerSummaryAmountByTarget> getCustomerSummaryAmount(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      String Interval) {
    return customerMapper.findCustomerSumAmount(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<CustomerSummaryAmountTopByCustomer> getCustomerSummaryAmountTopByCustomer(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return customerMapper.findCustomerSumAmountTopByCustomer(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Limit);
  }

  public List<CustomerSummaryAmountTopByRepresentative> getCustomerSummaryAmountTopByRepresentative(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return customerMapper.findCustomerSumAmountTopByRepresentative(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Limit);
  }

  public List<CustomerSummaryAmountTopByPNFamily> getCustomerSummaryAmountTopByPNFamily(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return customerMapper.findCustomerSumAmountTopByPNFamily(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Limit);
  }

  public List<CustomerOTD> getCustomerOTD(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo,
      String Interval) {
    return customerMapper.findCustomerOTD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<CustomerDOD> getCustomerDOD(
      String Site,
      String OrderType,
      String CustomerCode,
      String DateFrom,
      String DateTo) {
    return customerMapper.findCustomerDOD(
        Site,
        OrderType,
        CustomerCode,
        DateFrom,
        DateTo);
  }

  public Integer getCustomerOrdersCnt(
      String Site,
      String CustomerCode,
      String DateType,
      String DateFrom,
      String DateTo,
      String OrderStatus) {
    return customerMapper.findCustomerOrdersCnt(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo,
        OrderStatus);
  }

  public List<CustomerOrder> getCustomerOrders(
      String Site,
      String CustomerCode,
      String DateType,
      String DateFrom,
      String DateTo,
      String OrderStatus,
      Integer Offset,
      Integer Limit) {
    return customerMapper.findCustomerOrders(
        Site,
        CustomerCode,
        DateType,
        DateFrom,
        DateTo,
        OrderStatus,
        Offset,
        Limit);
  }
}
