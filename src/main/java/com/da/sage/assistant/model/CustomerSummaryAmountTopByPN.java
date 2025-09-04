/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2025-01-01 17:28:39                                                                       *
 * @LastEditDate          : 2025-08-28 09:44:10                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/


package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerSummaryAmountTopByPN {

  private String Site;
  private String PN;
  private Float SiteAllUSD;
  private Float SiteAllUSDWithTax;
  private Float SiteAllLocal;
  private Float SiteAllLocalWithTax;
  private Float SumUSD;
  private Float SumUSDWithTax;
  private Float SumLocal;
  private Float SumLocalWithTax;
  private Float TargetAllUSD;
  private Float TargetAllUSDWithTax;
  private Float TargetAllLocal;
  private Float TargetAllLocalWithTax;
  private Float AllUSD;
  private Float AllUSDWithTax;
  private Float AllLocal;
  private Float AllLocalWithTax;
  private String LocalCurrency;
}
