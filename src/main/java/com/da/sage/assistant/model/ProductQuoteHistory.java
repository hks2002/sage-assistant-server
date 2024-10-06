/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-25 09:19:05                                                                       *
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
  private Integer OrderFlag;
  private String OrderNO;
  private String PN;
  private Float NetPrice;
  private String Currency;
  private Integer Qty;
  private String CustomerName;
  private String CustomerCode;
  private Date QuoteDate;
  private String QuoteNO;
  private Float USD;
  private Float Rate;
  private String TradeTerm;
}
