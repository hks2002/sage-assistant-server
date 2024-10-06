/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-25 11:29:11                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class ComponentDeliveryDuration {

  private String PurchaseSite;
  private String PN;
  private String PurchaseNO;
  private String Line;
  private String VendorCode;
  private String VendorName;
  private Date PurchaseDate;
  private Date ReceiptDate;
  private Integer Duration;
  private Integer Qty;
  private String Unit;
}
