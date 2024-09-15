/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 22:07:59                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/SalesHistory.java                         *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class SalesHistory {

    private String SalesSite;
    private String OrderNO;
    private String PN;
    private BigDecimal NetPrice;
    private String Currency;
    private Integer Qty;
    private String CustomerName;
    private String CustomerCode;
    private Date OrderDate;
    private BigDecimal USD;
    private Float Rate;
    private String TradeTerm;
}
