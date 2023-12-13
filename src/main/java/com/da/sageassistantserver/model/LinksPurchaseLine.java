/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2023-11-24 16:53:52                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2023-12-07 14:59:13                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.model;

import java.sql.Date;

import lombok.Data;

@Data
public class LinksPurchaseLine {

    private String PurchaseNO;
    private String PurchaseLine;
    private String PurchaseProjectNO;
    private String PurchaseVendorCode;
    private String PurchaseVendorName;
    private Date PurchaseDate;
    private String PurchasePN;
    private String PurchasePNDescription;
    private Float PurchaseQty;
    private String PurchaseUnit;
    private Double PurchaseAmount;
    private String PurchaseCurrency;
}
