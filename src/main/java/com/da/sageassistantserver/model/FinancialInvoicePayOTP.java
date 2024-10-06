/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-27 17:16:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-02-10 10:18:32                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class FinancialInvoicePayOTP {

  private String Site;
  private String CustomerCode;
  private String Target;
  private Integer OTCnt;
  private Integer AllCnt;
  private Float OTP;
}
