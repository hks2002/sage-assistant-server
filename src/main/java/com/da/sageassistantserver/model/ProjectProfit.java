/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:01:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-19 18:18:44                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ProjectProfit {

  private String ProjectNO;
  private String OrderNO;
  private String PN;
  private String Description;
  private String CategoryCode;
  private Integer Qty;
  private BigDecimal OrderLocalPrice;
  private BigDecimal OrderPrice;
  private Date OrderDate;
  private BigDecimal ProjectLocalPrice;
  private BigDecimal ProjectLocalCost;
  private BigDecimal Profit;
}
