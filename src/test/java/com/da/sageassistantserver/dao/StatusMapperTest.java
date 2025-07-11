/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-03-12 22:01:15                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-04-07 14:54:37                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatusMapperTest {

  @Autowired
  private StatusMapper statusMapper;

  @Test
  void testFindTobeClosedWOBySite() {
    statusMapper.findTobeClosedWOBySite("ZHU");
  }

  @Test
  void testFindTobeDealWithOrderLineBySite() {
    statusMapper.findTobeDealWithOrderLineBySite("ZHU");
  }

  @Test
  void testFindTobeDeliveryBySite() {
    statusMapper.findTobeDeliveryBySite("ZHU");
  }

  @Test
  void testFindTobePurchaseBomBySite() {
    statusMapper.findTobePurchaseBomBySite("ZHU");
  }

  @Test
  void testFindTobeReceiveBySite() {
    statusMapper.findTobeReceiveBySite("ZHU");
  }

  List<String> getSite() {
    List<String> Sites = new ArrayList<>();
    Sites.add("ZHU");
    return Sites;
  }

  @Test
  void testFindTobeTrackingBOMLineBySite() {
    statusMapper.findTobeTrackingBOMLineBySite(
                                               "ZHU",
                                               "NOR",
                                               "00870",
                                               "",
                                               "",
                                               "DaysLeft",
                                               "DESC",
                                               0,
                                               10);
  }

  @Test
  void testFindTobeTrackingPurchaseOrderLineBySite() {
    statusMapper.findTobeTrackingPurchaseOrderLineBySite(
                                                         "ZHU",
                                                         "NOR",
                                                         "00870",
                                                         "",
                                                         "",
                                                         "DaysLeft",
                                                         "DESC",
                                                         0,
                                                         10);
  }

  @Test
  void testFindTobeTrackingNCLineBySite() {
    statusMapper.findTobeTrackingNCLineBySite(
                                              "ZHU",
                                              "NOR",
                                              "00870",
                                              "",
                                              "",
                                              "DaysLeft",
                                              "DESC",
                                              0,
                                              10);
  }

  @Test
  void testFindTobeTrackingReceiptLineBySite() {
    statusMapper.findTobeTrackingReceiptLineBySite(
                                                   "ZHU",
                                                   "NOR",
                                                   "00870",
                                                   "",
                                                   "",
                                                   "DaysLeft",
                                                   "DESC",
                                                   0,
                                                   10);
  }

  @Test
  void testFindTobeTrackingSalesOrderLineBySite() {
    statusMapper.findTobeTrackingSalesOrderLineBySite(
                                                      "ZHU",
                                                      "NOR",
                                                      "00870",
                                                      "",
                                                      "",
                                                      "DaysLeft",
                                                      "DESC",
                                                      0,
                                                      10);
  }

  @Test
  void testFindTobeTrackingSalesOrderLineCntBySite() {
    statusMapper.findTobeTrackingSalesOrderLineCntBySite(
                                                         "ZHU",
                                                         "NOR",
                                                         "",
                                                         "",
                                                         "");
  }
}
