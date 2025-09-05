/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-09-05 09:30:27                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class ProductQuoteHistory {

  private String SalesSite;
  private String PN;
  private String QuoteNO;
  private Date QuoteDate;
  private String CustomerCode;
  private String CustomerName;
  private Float NetPrice;
  private String Currency;
  private Integer Qty;
  private String TradeTerm;
  private Float USD;
  private Float Rate;
  private Integer OrderFlag;
  private String OrderNO;
}
