/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-27 19:57:54                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TodoLongTimeNoQC {

  private String ProjectNO;
  private String PurchaseNO;
  private String Line;
  private String VendorCode;
  private String VendorName;
  private String PN;
  private Integer Qty;
  private String Unit;
  private String Description;
  private BigDecimal NetPrice;
  private String Currency;
  private BigDecimal USD;
  private Float Rate;
  private Date OrderDate;
  private Date AckDate;
  private Date ExpectDate;
  private String CreateUser;
  private Date ReceiptDate;
  private String Receiptor;
}
