/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-27 16:43:00                                                                       *
 * @LastEditDate          : 2025-07-27 22:01:37                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.math.BigDecimal;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class FinancialInvoiceSumAmount {

  private String Site;
  private String CustomerCode;
  private String Target;
  private BigDecimal SumUSDTarget;
  private BigDecimal SumUSDPayTarget;
  private BigDecimal SumLocalTarget;
  private BigDecimal SumLocalPayTarget;
  private String LocalCurrency;
}
