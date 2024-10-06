/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-27 18:11:17                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class ProjectProfit {

  private String Site;
  private String ProjectNO;
  private String PN;
  private String Description;
  private String ProductFamily;
  private Date ProjectDate;
  private String OrderNO;
  private String OrderLine;
  private Integer QTY;
  private String Currency;
  private String LocalCurrency;
  private BigDecimal ProjectSalesPrice;
  private BigDecimal ProjectSalesLocalPrice;
  private BigDecimal ProjectLocalCost;
  private BigDecimal Profit;
  private Float ProfitRate;
}
