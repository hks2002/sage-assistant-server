/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-09-21 09:17:00                                                                       *
 * @LastEditDate          : 2025-07-27 14:56:13                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class BatchPurchase {

  private String ProjectNO;
  private String ProjectOriNO;
  private Date ProjectDate;
  private Double LocalCostNoTax;
  private Double LocalCostWithTax;
  private String Currency;
}
