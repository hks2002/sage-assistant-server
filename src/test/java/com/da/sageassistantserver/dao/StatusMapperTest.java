/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2023-03-12 22:01:15                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-06-03 22:34:49                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.dao;

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

    @Test
    void testFindTobeTrackingBOMLineBySite() {
        statusMapper.findTobeTrackingBOMLineBySite("ZHU", "NOR", "00870", "", "", "DaysLeft", "DESC", 0,
                10);
    }

    @Test
    void testFindTobeTrackingPurchaseOrderLineBySite() {
        statusMapper.findTobeTrackingPurchaseOrderLineBySite("ZHU", "NOR", "00870", "", "", "DaysLeft", "DESC", 0, 10);
    }

    @Test
    void testFindTobeTrackingQALineBySite() {
        statusMapper.findTobeTrackingQALineBySite("ZHU", "NOR", "00870", "", "", "DaysLeft", "DESC", 0, 10);
    }

    @Test
    void testFindTobeTrackingReceiptLineBySite() {
        statusMapper.findTobeTrackingReceiptLineBySite("ZHU", "NOR", "00870", "", "", "DaysLeft", "DESC", 0, 10);
    }

    @Test
    void testFindTobeTrackingSalesOrderLineBySite() {
        statusMapper.findTobeTrackingSalesOrderLineBySite("ZHU", "NOR", "00870", "", "", "DaysLeft", "DESC", 0, 10);
    }

    @Test
    void testFindTobeTrackingSalesOrderLineCntBySite() {
        statusMapper.findTobeTrackingSalesOrderLineCntBySite("ZHU", "NOR", "", "", "");
    }
}
