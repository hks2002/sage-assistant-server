/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:21:33                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/QuoteHistory.java                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class QuoteHistory {

    private String SalesSite;
    private Integer OrderFlag;
    private String OrderNO;
    private String PN;
    private BigDecimal NetPrice;
    private String Currency;
    private Integer Qty;
    private String CustomerName;
    private String CustomerCode;
    private Date QuoteDate;
    private String QuoteNO;
    private BigDecimal USD;
    private Float Rate;
    private String TradeTerm;
}
