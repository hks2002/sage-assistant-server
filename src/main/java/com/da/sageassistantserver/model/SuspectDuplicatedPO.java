/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-09-21 09:17:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-22 13:49:15                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class SuspectDuplicatedPO {

  private String ProjectNO;
  private String PN;
  private String PurchaseNO;
  private Date PurchaseDate;
  private String PurchaseLine;
  private Integer PurchaseQty;
  private BigDecimal Cost;
  private String Currency;
  private Integer TotalPurchaseQty;
  private Integer TotalSalesQty;
  private String Purchaser;
  private Integer Seq;
}
