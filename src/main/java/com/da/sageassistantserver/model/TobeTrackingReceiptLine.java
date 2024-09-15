/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-11-10 14:18:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:23:04                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/TobeTrackingReceiptLine.java                   *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TobeTrackingReceiptLine {

    private String ReceiptNO;
    private String ReceiptLine;
    private String ReceiptPurchaseNO;
    private String ReceiptPurchaseLine;
    private Date ReceiptDate;
    private String Receiptor;
    private Integer ReceiptQty;
}
