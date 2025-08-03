/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-30 16:45:43                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class QcQPYTop {

  private String Site;
  private String SupplierCode;
  private String SupplierName;
  private Integer NcCntByProject;
  private Integer SameCnt;
  private Integer ProjectCnt;
  private Float QPY;
}
