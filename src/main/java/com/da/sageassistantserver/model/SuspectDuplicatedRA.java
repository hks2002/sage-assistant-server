/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-09-21 09:17:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-17 13:08:21                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import lombok.Data;

@Data
public class SuspectDuplicatedRA {

    private String ProjectNO;
    private String PN;
    private String ReceiptNO;
    private String ReceiptLine;
    private String Receiptor;
    private String PurchaseNO;
    private String PurchaseLine;
    private String Purchaser;
    private Integer ReceiptQty;
    private Float ReceiptAmount;
    private Integer TotalReceiptQty;
    private Integer TotalSalesQty;
    private String Currency;
    private Integer Seq;
}
