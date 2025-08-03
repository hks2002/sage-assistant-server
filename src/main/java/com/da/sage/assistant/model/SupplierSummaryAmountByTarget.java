/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-08-07 15:01:17                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class SupplierSummaryAmountByTarget {

  private String Site;
  private String SupplierCode;
  private String Target;
  private Float SumUSDTarget;
  private Float SumUSDWithTaxTarget;
  private Float SumLocalTarget;
  private Float SumLocalWithTaxTarget;
  private String LocalCurrency;
}
