/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-11-10 14:18:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2024-12-25 14:43:15                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TrackingPurchaseOrderLine {

  private String Site;
  private String PurchaseNO;
  private String PurchaseLine;
  private String PurchaseProjectNO;
  private String PurchasePN;
  private String PurchasePNVersion;
  private String PurchasePNDesc;
  private Integer PurchaseQty;
  private Float PurchasePrice;
  private String LocalCurrency;
  private String PurchaseUnit;
  private String VendorCode;
  private String VendorName;
  private Date PurchaseAckDate;
  private Date PurchaseExpectDate;
  private Date PurchaseDate;
  private String PurchaseUser;
  private Integer DaysLeft;
  private LineNote LineNote;
}
