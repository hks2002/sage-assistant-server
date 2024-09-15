/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-11-10 14:18:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-29 14:52:31                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TobeTrackingPurchaseOrderLine {

    private String PurchaseNO;
    private String PurchaseLine;
    private String PurchaseProjectNO;
    private String PurchasePN;
    private String PurchasePNVersion;
    private String PurchasePNDesc;
    private Integer PurchaseQTY;
    private String PurchaseUnit;
    private String VendorCode;
    private String VendorName;
    private Date PurchaseAckDate;
    private Date PurchaseExpectDate;
    private String PurchaseComment;
    private Date PurchaseDate;
    private String PurchaseUser;
}
