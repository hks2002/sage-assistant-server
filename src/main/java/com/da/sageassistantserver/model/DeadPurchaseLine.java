/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:01:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-19 15:22:45                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;

@Data
public class DeadPurchaseLine {

    private String ProjectNO;
    private String OrderNO;
    private String PN;
    private Integer Qty;
    private Date OrderDate;
    private String PurchaseNO;
    private String PurchaseLine;
    private Date PurchaseDate;
    private String Purchaser;
}
