/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-11-10 14:18:00                                                                       *
 * @LastEditDate          : 2025-07-27 10:41:31                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TrackingBOMLine {

  private String Site;
  private String BomProjectNO;
  private String WorkOrderNO;
  private String BomLine;
  private String BomPN;
  private String StockPN;
  private String BomDesc;
  private Integer BomQty;
  private String BomUnit;
  private Integer AvaQty;
  private Integer AllQty;
  private Integer ShortQty;
  private String Creator;
  private Date BomRequestDate;
  private LineNote LineNote;
}
