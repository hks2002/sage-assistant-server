/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2023-03-12 23:02:47                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2023-11-18 12:01:53                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FinancialMapperTest {

    @Autowired
    private FinancialMapper financialMapper;

    @Test
    void testFindAccountBalanceByAccountNO() {
        String[] accountNO = new String[1];
        accountNO[0] = "Z00001";
        financialMapper.findAccountBalanceByAccountNO("ZHU", "2022", accountNO);
    }

    @Test
    void testFindAccountBalanceForAll() {
        financialMapper.findAccountBalanceForAll("ZHU", "2022");
    }

    @Test
    void testFindInvoicePay() {
        financialMapper.findInvoicePay("ZHU", "00870", "invoiceDay", "2022-01-01", "2022-12-31");
    }

    @Test
    void testFindInvoicePayPro() {
        financialMapper.findInvoicePayPro("ZHU", "00870", "invoiceDay", "2022-01-01", "2022-12-31");
    }
}
