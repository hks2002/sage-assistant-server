/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-09-21 12:36:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 22:03:24                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/AnalysesSales.java                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysesSales {

    private String OrderNO;
    private Date OrderDate;
    private String CustomerCode;
    private String CustomerName;
    private Double NetPrice;
    private Integer QTY;
    private Integer ROWNUM;
}
