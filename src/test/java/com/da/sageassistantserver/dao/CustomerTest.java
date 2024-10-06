/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-03-12 21:46:07                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-17 14:27:49                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTest {

  @Autowired
  private CustomerMapper customerMapper;

  @Test
  void testFindCustomerByCodeOrName() {
    customerMapper.findCustomerByCodeOrName("00870", 5);
  }

  @Test
  void testFindCustomerDetailsByCode() {
    customerMapper.findCustomerDetailsByCode("00870");
  }

  @Test
  void testFindCustomerSumAmount() {
    customerMapper.findCustomerSumAmount(
      "ZHU",
      "ALL",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    customerMapper.findCustomerSumAmount(
      "ZHU",
      "ALL",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    customerMapper.findCustomerSumAmount(
      "ZHU",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    customerMapper.findCustomerSumAmount(
      "ALL",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
  }

  @Test
  void testFindCustomerOTD() {
    customerMapper.findCustomerOTD(
      "ZHU",
      "ALL",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    customerMapper.findCustomerOTD(
      "ZHU",
      "ALL",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    customerMapper.findCustomerOTD(
      "ZHU",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    customerMapper.findCustomerOTD(
      "ALL",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
  }
}
