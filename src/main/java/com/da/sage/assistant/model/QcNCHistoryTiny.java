/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-30 16:45:45                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class QcNCHistoryTiny {

  private String Site;
  private String SupplierCode;
  private String NCNo;
  private String ProjectCode;
  private Integer Req;
  private Date Date;
  private String Cat;
  private String Type;
  private String Cri;
  private String Des;
  private String CreatedBy;
}
