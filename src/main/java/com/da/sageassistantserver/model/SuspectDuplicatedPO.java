/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-09-21 09:17:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-16 09:20:58                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.sql.Date;

import lombok.Data;

@Data
public class SuspectDuplicatedPO {

    private String ProjectNO;
    private String PN;
    private String PurchaseNO;
    private Date PurchaseDate;
    private String PurchaseLine;
    private Integer PurchaseQty;
    private Integer TotalPurchaseQty;
    private Integer TotalSalesQty;
    private String Purchaser;
    private Integer Seq;
}
