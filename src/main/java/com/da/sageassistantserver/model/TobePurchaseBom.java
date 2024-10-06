/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-20 00:13:17                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import java.util.Date;
import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TobePurchaseBom {

  private String ProjectNO;
  private String OrderType;
  private String WorkOrderNO;
  private String BomSeq;
  private String CustomerCode;
  private String CustomerName;
  private String ForPN;
  private String PN;
  private String Description;
  private Integer Qty;
  private Integer AvaQty;
  private Integer ShortQty;
  private Integer AllQty;
  private String Unit;
  private Date CreateDate;
  private String CreateUser;
}
