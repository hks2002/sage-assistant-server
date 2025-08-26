/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-08-25 16:53:54                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/


package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerSummaryAmountTopByCustomer {

  private String Site;
  private String CustomerCode;
  private String CustomerName;
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
