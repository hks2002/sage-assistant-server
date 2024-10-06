/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-25 09:18:55                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class ProductCostHistory {

  private String PurchaseSite;
  private String ProjectNO;
  private String OrderPN;
  private Date OrderDate;
  private String PurchaseNO;
  private String Line;
  private String VendorCode;
  private String VendorName;
  private String PurchasePN;
  private String Description;
  private String Currency;
  private Float NetPrice;
  private Integer Qty;
  private Float USD;
  private Float Rate;
}
