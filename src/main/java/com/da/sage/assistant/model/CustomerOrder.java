/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-31 16:19:00                                                                       *
 * @LastEditDate          : 2025-08-23 19:08:26                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/


package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerOrder {

  private Integer ItemNO;
  private String Site;
  private String Representative;
  private String CustomerCode;
  private String CustomerName;
  private String OrderType;
  private String OrderNO;
  private String OrderLine;
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
  private Date OrderRequestDate;
  private Date OrderPlanedDate;
  private String LastDeliveryNO;
  private Integer TotalDeliveryQty;
  private Date LastShipDate;
  private Integer DaysDelay;
}
