/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:01:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-22 20:46:22                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TobeInvoice {

  private String PurchaseLine;
  private Date PurchaseDate;
  private String Purchaser;
  private String PurchaseNO;
  private String ProjectNO;
  private String VendorCode;
  private String VendorName;
  private String PN;
  private String Description;
  private Integer Qty;
  private String Unit;
  private BigDecimal Price;
  private String Currency;
  private String ReceiveNO;
  private String ReceiveLine;
  private Date ReceiveDate;
  private String Receiptor;
}
