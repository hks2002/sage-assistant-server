/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:27:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:25:16                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.service;

import com.da.sageassistantserver.dao.CustomerMapper;
import com.da.sageassistantserver.model.CustomerDOD;
import com.da.sageassistantserver.model.CustomerDetails;
import com.da.sageassistantserver.model.CustomerName;
import com.da.sageassistantserver.model.CustomerOTD;
import com.da.sageassistantserver.model.CustomerOrder;
import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByCustomer;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByRepresentative;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {

  @Autowired
  private CustomerMapper customerMapper;

  public List<CustomerName> getCustomerByCodeOrName(
    String cond,
    Integer count
  ) {
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
      count
    );

    return listPage;
  }

  public List<CustomerDetails> getCustomerDetails(
    @Param("CustomerCode") String CustomerCode
  ) {
    return customerMapper.findCustomerDetailsByCode(CustomerCode);
  }

  public List<CustomerSummaryAmount> getCustomerSummaryAmount(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    String Interval
  ) {
    return customerMapper.findCustomerSumAmount(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Interval
    );
  }

  public Integer getCustomerSummaryAmountTotalUSD(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo
  ) {
    return customerMapper.findCustomerSumAmountTotalUSD(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo
    );
  }

  public List<CustomerSummaryAmountTopByCustomer> getCustomerSummaryAmountTopByCustomer(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    Integer Limit
  ) {
    return customerMapper.findCustomerSumAmountTopByCustomer(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Limit
    );
  }

  public List<CustomerSummaryAmountTopByRepresentative> getCustomerSummaryAmountTopByRepresentative(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    Integer Limit
  ) {
    return customerMapper.findCustomerSumAmountTopByRepresentative(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Limit
    );
  }

  public List<CustomerSummaryAmountTopByPNFamily> getCustomerSummaryAmountTopByPNFamily(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    Integer Limit
  ) {
    return customerMapper.findCustomerSumAmountTopByPNFamily(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Limit
    );
  }

  public List<CustomerOTD> getCustomerOTD(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    String Interval
  ) {
    return customerMapper.findCustomerOTD(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Interval
    );
  }

  public List<CustomerDOD> getCustomerDOD(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo
  ) {
    return customerMapper.findCustomerDOD(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo
    );
  }

  public Integer getCustomerOrdersCnt(
    String Site,
    String CustomerCode,
    String DateType,
    String DateFrom,
    String DateTo,
    String OrderStatus
  ) {
    return customerMapper.findCustomerOrdersCnt(
      Site,
      CustomerCode,
      DateType,
      DateFrom,
      DateTo,
      OrderStatus
    );
  }

  public List<CustomerOrder> getCustomerOrders(
    String Site,
    String CustomerCode,
    String DateType,
    String DateFrom,
    String DateTo,
    String OrderStatus,
    Integer Offset,
    Integer Limit
  ) {
    return customerMapper.findCustomerOrders(
      Site,
      CustomerCode,
      DateType,
      DateFrom,
      DateTo,
      OrderStatus,
      Offset,
      Limit
    );
  }
}
