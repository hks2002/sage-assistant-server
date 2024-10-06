/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:21:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-10 14:28:09                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class DeliveryLines {

  private Integer ItemNO;
  private String Site;
  private String CustomerCode;
  private String CustomerName;
  private String DeliveryNO;
  private String DeliveryLine;
  private String OrderType;
  private String OrderNO;
  private String OrderLine;
  private String ProjectNO;
  private String PN;
  private String Description;
  private BigDecimal Price;
  private BigDecimal LocalPrice;
  private String Currency;
  private String LocalCurrency;
  private Integer Qty;
  private Date DeliveryDate;
  private Date OrderPlanedDate;
  private Integer DaysDelay;
}
