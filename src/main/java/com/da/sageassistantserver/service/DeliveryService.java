/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:27:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:24:49                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.service;

import com.da.sageassistantserver.dao.DeliveryMapper;
import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByCustomer;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sageassistantserver.model.DeliveryLines;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryService {

  @Autowired
  private DeliveryMapper deliveryMapper;

  public List<CustomerSummaryAmount> getDeliverySummaryAmount(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    String Interval
  ) {
    return deliveryMapper.findDeliverySumAmount(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Interval
    );
  }

  public Integer getDeliverySummaryAmountTotalUSD(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo
  ) {
    return deliveryMapper.findDeliverySumAmountTotalUSD(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo
    );
  }

  public List<CustomerSummaryAmountTopByCustomer> getDeliverySummaryAmountTopByCustomer(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    Integer Limit
  ) {
    return deliveryMapper.findDeliverySumAmountTopByCustomer(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Limit
    );
  }

  public List<CustomerSummaryAmountTopByPNFamily> getDeliverySummaryAmountTopByPNFamily(
    String Site,
    String OrderType,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    Integer Limit
  ) {
    return deliveryMapper.findDeliverySumAmountTopByPNFamily(
      Site,
      OrderType,
      CustomerCode,
      DateFrom,
      DateTo,
      Limit
    );
  }

  public Integer getDeliveryLinesCnt(
    String Site,
    String CustomerCode,
    String DateFrom,
    String DateTo
  ) {
    return deliveryMapper.findDeliveryLinesCnt(
      Site,
      CustomerCode,
      DateFrom,
      DateTo
    );
  }

  public List<DeliveryLines> getDeliveryLines(
    String Site,
    String CustomerCode,
    String DateFrom,
    String DateTo,
    Integer Offset,
    Integer Limit
  ) {
    return deliveryMapper.findDeliveryLines(
      Site,
      CustomerCode,
      DateFrom,
      DateTo,
      Offset,
      Limit
    );
  }
}
