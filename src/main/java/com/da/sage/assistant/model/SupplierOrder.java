/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-08-07 13:29:02                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class SupplierOrder {

  private Integer ItemNO;
  private String Site;
  private String SupplierCode;
  private String SupplierName;
  private String PurchaseNO;
  private String PurchaseLine;
  private String ProjectNO;
  private String PN;
  private String Description;
  private Integer Qty;
  private Float NetPrice;
  private Float NetPriceWithTax;
  private Float TotalAmount;
  private Float TotalAmountWithTax;
  private Float TotalLocalAmount;
  private Float TotalLocalAmountWithTax;
  private String Currency;
  private String LocalCurrency;
  private Date OrderDate;
  private Date AckDate;
  private Date ExpectDate;
  private String LastReceiveNO;
  private Date LastReceiveDate;
  private Integer TotalReceiveQty;
  private Integer DaysDelay;
}
