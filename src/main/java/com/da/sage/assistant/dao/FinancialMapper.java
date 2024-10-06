/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditDate          : 2025-07-27 09:21:06                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.FinancialBalance;
import com.da.sage.assistant.model.FinancialInvoicePay;
import com.da.sage.assistant.model.FinancialInvoiceSumAmount;

@Mapper
public interface FinancialMapper {
  List<FinancialBalance> findAccountBalanceForAll(
      @Param("Site") String Site,
      @Param("Year") String Year);

  // AccountNO AccountNOList only one
  List<FinancialBalance> findAccountBalanceByAccountNO(
      @Param("Site") String Site,
      @Param("Year") String Year,
      @Param("AccountNOs") String[] AccountNOs);

  List<FinancialInvoiceSumAmount> findInvoiceSumAmount(
      @Param("Site") String Site,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateType") String DateType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("PayStatus") String PayStatus,
      @Param("Interval") String Interval);

  List<FinancialInvoicePay> findInvoicePay(
      @Param("Site") String Site,
      @Param("CustomerCode") String CustomerCode,
      @Param("DateType") String DateType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("PayStatus") String PayStatus);
}
