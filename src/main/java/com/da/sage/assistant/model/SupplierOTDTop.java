/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-08-22 18:20:22                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/


package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class SupplierOTDTop {

  private String Site;
  private String SupplierCode;
  private String SupplierName;
  private Integer OTCnt;
  private Integer AllCnt;
  private Float OTD;
  private Integer OTTotalCnt;
  private Integer AllTotalCnt;
  private Float OTDTotal;
}
