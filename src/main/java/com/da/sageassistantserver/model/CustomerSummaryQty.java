/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-31 16:23:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:20:34                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/CustomerSummaryQty.java                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerSummaryQty {

    private String Site;
    private String CustomerCode;
    private String CountType;
    private int Qty;
}
