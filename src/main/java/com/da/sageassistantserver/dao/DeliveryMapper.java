/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:25:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:23:39                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByCustomer;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sageassistantserver.model.DeliveryLines;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeliveryMapper {
  List<CustomerSummaryAmount> findDeliverySumAmount(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Interval") String Interval
  );

  Integer findDeliverySumAmountTotalUSD(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo
  );

  List<CustomerSummaryAmountTopByCustomer> findDeliverySumAmountTopByCustomer(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Limit") Integer Limit
  );

  List<CustomerSummaryAmountTopByPNFamily> findDeliverySumAmountTopByPNFamily(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Limit") Integer Limit
  );

  Integer findDeliveryLinesCnt(
    @Param("Site") String Site,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo
  );

  List<DeliveryLines> findDeliveryLines(
    @Param("Site") String Site,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Offset") Integer Offset,
    @Param("Limit") Integer Limit
  );
}
