/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-09-21 12:32:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 20:27:54                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/AnalysesQuote.java                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysesQuote {

    private String QuoteNO;
    private Date QuoteDate;
    private String CustomerCode;
    private String CustomerName;
    private Double NetPrice;
    private String OrderNO;
    private Integer OrderFlag;
    private Integer QTY;
    private Integer ROWNUM;
}
