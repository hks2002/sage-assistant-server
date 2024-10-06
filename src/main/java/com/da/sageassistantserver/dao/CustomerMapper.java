/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:25:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:23:55                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import com.da.sageassistantserver.model.CustomerDOD;
import com.da.sageassistantserver.model.CustomerDetails;
import com.da.sageassistantserver.model.CustomerName;
import com.da.sageassistantserver.model.CustomerOTD;
import com.da.sageassistantserver.model.CustomerOrder;
import com.da.sageassistantserver.model.CustomerSummaryAmount;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByCustomer;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByPNFamily;
import com.da.sageassistantserver.model.CustomerSummaryAmountTopByRepresentative;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerMapper {
  List<CustomerDetails> findCustomerDetailsByCode(
    @Param("CustomerCode") String CustomerCode
  );

  List<CustomerName> findCustomerByCodeOrName(
    @Param("CustomerCodeOrName") String CustomerCodeOrName,
    @Param("Count") Integer Count
  );

  List<CustomerSummaryAmount> findCustomerSumAmount(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Interval") String Interval
  );
  Integer findCustomerSumAmountTotalUSD(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo
  );

  List<CustomerSummaryAmountTopByCustomer> findCustomerSumAmountTopByCustomer(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Limit") Integer Limit
  );

  List<CustomerSummaryAmountTopByRepresentative> findCustomerSumAmountTopByRepresentative(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Limit") Integer Limit
  );

  List<CustomerSummaryAmountTopByPNFamily> findCustomerSumAmountTopByPNFamily(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Limit") Integer Limit
  );

  List<CustomerOTD> findCustomerOTD(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("Interval") String Interval
  );

  List<CustomerDOD> findCustomerDOD(
    @Param("Site") String Site,
    @Param("OrderType") String OrderType,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo
  );

  Integer findCustomerOrdersCnt(
    @Param("Site") String Site,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateType") String DateType,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("OrderStatus") String OrderStatus
  );

  List<CustomerOrder> findCustomerOrders(
    @Param("Site") String Site,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateType") String DateType,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("OrderStatus") String OrderStatus,
    @Param("Offset") Integer Offset,
    @Param("Limit") Integer Limit
  );
}
