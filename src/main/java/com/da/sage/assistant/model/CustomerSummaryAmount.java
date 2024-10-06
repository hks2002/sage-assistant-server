/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-31 16:21:00                                                                       *
 * @LastEditDate          : 2025-07-25 23:07:25                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import java.math.BigDecimal;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerSummaryAmount {

  private String Site;
  private String CustomerCode;
  private String Target;
  private BigDecimal SumUSDTarget;
  private BigDecimal SumLocalTarget;
  private String LocalCurrency;
}
