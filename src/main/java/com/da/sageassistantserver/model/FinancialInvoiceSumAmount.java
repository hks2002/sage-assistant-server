/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-27 16:43:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-24 13:09:37                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import java.math.BigDecimal;
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
