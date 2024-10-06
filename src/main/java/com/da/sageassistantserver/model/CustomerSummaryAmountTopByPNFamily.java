/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2025-01-01 17:28:39                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-01 17:28:39                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.model;

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
