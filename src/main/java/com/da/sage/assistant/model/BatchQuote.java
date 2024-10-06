/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-09-21 12:32:00                                                                       *
 * @LastEditDate          : 2025-07-27 10:56:36                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class BatchQuote {

  private String QuoteNO;
  private Date QuoteDate;
  private String CustomerCode;
  private String CustomerName;
  private Double NetPrice;
  private String Currency;
  private String OrderNO;
  private Integer OrderFlag;
  private Integer QTY;
  private Integer ROWNUM;
}
