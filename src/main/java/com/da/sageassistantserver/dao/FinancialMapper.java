/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2022-03-26 17:55:00                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2023-11-17 10:24:22                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sageassistantserver.model.FinancialBalance;
import com.da.sageassistantserver.model.FinancialInvoicePay;
import com.da.sageassistantserver.model.FinancialInvoicePayPro;

@Mapper
public interface FinancialMapper {
    List<FinancialBalance> findAccountBalanceForAll(@Param("Site") String Site, @Param("Year") String Year);

    // AccountNO AccountNOList only one
    List<FinancialBalance> findAccountBalanceByAccountNO(
            @Param("Site") String Site,
            @Param("Year") String Year,
            @Param("AccountNOs") String[] AccountNOs);

    List<FinancialInvoicePay> findInvoicePay(
            @Param("Site") String Site,
            @Param("CustomerCode") String CustomerCode,
            @Param("DateType") String DateType,
            @Param("DateFrom") String DateFrom,
            @Param("DateTo") String DateTo);

    List<FinancialInvoicePayPro> findInvoicePayPro(
            @Param("Site") String Site,
            @Param("CustomerCode") String CustomerCode,
            @Param("DateType") String DateType,
            @Param("DateFrom") String DateFrom,
            @Param("DateTo") String DateTo);
}
