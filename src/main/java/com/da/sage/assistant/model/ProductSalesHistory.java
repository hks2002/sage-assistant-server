/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-09-05 16:57:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class ProductSalesHistory {

  private String SalesSite;
  private Date OrderDate;
  private String OrderNO;
  private String PN;
  private String CustomerCode;
  private String CustomerName;
  private String TradeTerm;
  private Float NetPrice;
  private String Currency;
  private Integer Qty;
  private Float USD;
  private Float Rate;
}
