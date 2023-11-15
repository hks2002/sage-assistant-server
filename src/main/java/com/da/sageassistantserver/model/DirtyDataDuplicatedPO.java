/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-09-21 09:17:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-15 14:25:41                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import lombok.Data;

@Data
public class DirtyDataDuplicatedPO {

    private String ProjectNO;
    private String PN;
    private String PurchaseNO;
    private String PurchaseLine;
    private Integer PurchaseQty;
    private Integer TotalPurchaseQty;
    private Integer TotalSalesQty;
    private Integer Req;
}
