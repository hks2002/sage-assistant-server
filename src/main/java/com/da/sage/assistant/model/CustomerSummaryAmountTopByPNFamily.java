/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2025-01-01 17:28:39                                                                       *
 * @LastEditDate          : 2025-07-25 23:07:40                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerSummaryAmountTopByPNFamily {

  private String Site;
  private String PNFamily;
  private Float SiteAllUSD;
  private Float SumUSD;
  private Float SumLocal;
  private String LocalCurrency;
}
