/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-03-12 19:32:19                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-03 17:09:06                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SupplierMapperTest {

  @Autowired
  SupplierMapper supplierMapper;

  @Test
  void testFindSupplierByCodeOrName() {
    supplierMapper.findSupplierByCodeOrName("00870", 5);
  }

  @Test
  void testFindSupplierDetailsByCode() {
    supplierMapper.findSupplierDetailsByCode("00870");
  }

  @Test
  void testFindSupplierSumAmount() {
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierSumAmount(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierSumAmount(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierSumAmount(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
  }

  @Test
  void testFindSupplierOTD() {
    supplierMapper.findSupplierOTD(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierOTD(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierOTD(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierOTD(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
  }

  @Test
  void testFindSupplierQPY() {
    supplierMapper.findSupplierQPY(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierQPY(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierQPY(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierQPY(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
  }

  @Test
  void testFindSupplierNCSummary() {
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierNCSummary(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Month"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "ALL",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "MAIN",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "OTHERS",
      "ALL",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierNCSummary(
      "ZHU",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
    supplierMapper.findSupplierNCSummary(
      "ALL",
      "",
      "00870",
      "2024-01-01",
      "2024-12-31",
      "Year"
    );
  }

  @Test
  void testFindSupplierNCHistoryCnt() {
    supplierMapper.findSupplierNCHistoryCnt(
      "ZHU",
      "ALL",
      "00870",
      "2024-01-01",
      "2024-12-31"
    );
    supplierMapper.findSupplierNCHistoryCnt(
      "ZHU",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31"
    );
    supplierMapper.findSupplierNCHistoryCnt(
      "ALL",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31"
    );
  }

  @Test
  void testFindSupplierNCHistory() {
    supplierMapper.findSupplierNCHistory(
      "ZHU",
      "ALL",
      "00870",
      "2024-01-01",
      "2024-12-31",
      0,
      100
    );
    supplierMapper.findSupplierNCHistory(
      "ZHU",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31",
      0,
      100
    );
    supplierMapper.findSupplierNCHistory(
      "ALL",
      "ALL",
      "",
      "2024-01-01",
      "2024-12-31",
      0,
      100
    );
  }

  @Test
  void testFindSupplierOrdersCnt() {
    supplierMapper.findSupplierOrdersCnt(
      "ZHU",
      "ALL",
      "00870",
      "ORDER",
      "2024-01-01",
      "2024-12-31",
      "OPEN"
    );
    supplierMapper.findSupplierOrdersCnt(
      "ZHU",
      "MAIN",
      "00870",
      "ORDER",
      "2024-01-01",
      "2024-12-31",
      "OPEN"
    );
    supplierMapper.findSupplierOrdersCnt(
      "ZHU",
      "OTHERS",
      "00870",
      "ORDER",
      "2024-01-01",
      "2024-12-31",
      "OPEN"
    );
  }

  @Test
  void testFindSupplierOrders() {
    supplierMapper.findSupplierOrders(
      "ZHU",
      "ALL",
      "00870",
      "ORDER",
      "2024-01-01",
      "2024-12-31",
      "OPEN",
      0,
      100
    );
    supplierMapper.findSupplierOrders(
      "ZHU",
      "MAIN",
      "00870",
      "ORDER",
      "2024-01-01",
      "2024-12-31",
      "OPEN",
      0,
      100
    );
    supplierMapper.findSupplierOrders(
      "ZHU",
      "OTHERS",
      "00870",
      "ORDER",
      "2024-01-01",
      "2024-12-31",
      "OPEN",
      0,
      100
    );
  }
}
