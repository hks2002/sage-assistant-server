/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-03-12 21:57:31                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2024-12-25 14:51:27                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockMapperTest {

  @Autowired
  private StockMapper stockMapper;

  @Test
  void testCheckPN() {
    stockMapper.checkPN("956A1001G01");
  }

  @Test
  void testFindStockHistoryBySite() {
    stockMapper.findStockHistoryBySite(
      "ZHU",
      "956A1001G01",
      "2019-01-01",
      "2019-12-31"
    );
  }

  @Test
  void testFindStockInfoByPnRoot() {
    stockMapper.findStockInfoByPnRoot("956A1001G01");
  }

  @Test
  void testFindStockOptionPN() {
    stockMapper.findStockOptionPN("956A1001G01");
  }

  @Test
  void testFindStockQty() {
    stockMapper.findStockQty("ZHU", "956A1001G01");
  }

  @Test
  void testFindStockSummaryBySite() {
    stockMapper.findStockSummaryBySite("ZHU");
  }
}
