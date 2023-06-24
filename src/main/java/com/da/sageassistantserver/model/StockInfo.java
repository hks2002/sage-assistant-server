/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:21:45                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/StockInfo.java                                 *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import lombok.Data;


@Data
public class StockInfo {

    private String StockSite;
    private String PN;
    private Integer Qty;
}
