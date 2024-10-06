/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-31 16:21:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2024-12-30 17:13:15                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import java.math.BigDecimal;
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
