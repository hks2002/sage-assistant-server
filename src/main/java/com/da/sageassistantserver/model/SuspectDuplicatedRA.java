/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-09-21 09:17:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-22 13:51:03                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class SuspectDuplicatedRA {

  private String ProjectNO;
  private String PN;
  private String ReceiptNO;
  private String ReceiptLine;
  private Date ReceiptDate;
  private String Receiptor;
  private String PurchaseNO;
  private String PurchaseLine;
  private Date PurchaseDate;
  private String Purchaser;
  private Integer ReceiptQty;
  private BigDecimal ReceiptAmount;
  private Integer TotalReceiptQty;
  private Integer TotalSalesQty;
  private String Currency;
  private Integer Seq;
}
