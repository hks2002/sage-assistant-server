/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-24 14:25:00                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import com.da.sageassistantserver.model.FinancialBalance;
import com.da.sageassistantserver.model.FinancialInvoicePay;
import com.da.sageassistantserver.model.FinancialInvoiceSumAmount;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FinancialMapper {
  List<FinancialBalance> findAccountBalanceForAll(
    @Param("Site") String Site,
    @Param("Year") String Year
  );

  // AccountNO AccountNOList only one
  List<FinancialBalance> findAccountBalanceByAccountNO(
    @Param("Site") String Site,
    @Param("Year") String Year,
    @Param("AccountNOs") String[] AccountNOs
  );

  List<FinancialInvoiceSumAmount> findInvoiceSumAmount(
    @Param("Site") String Site,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateType") String DateType,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("PayStatus") String PayStatus,
    @Param("Interval") String Interval
  );

  List<FinancialInvoicePay> findInvoicePay(
    @Param("Site") String Site,
    @Param("CustomerCode") String CustomerCode,
    @Param("DateType") String DateType,
    @Param("DateFrom") String DateFrom,
    @Param("DateTo") String DateTo,
    @Param("PayStatus") String PayStatus
  );
}
