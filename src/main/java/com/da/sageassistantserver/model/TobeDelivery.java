/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:01:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-09-16 01:20:27                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TobeDelivery {

    private String ProjectNO;
    private String OrderNO;
    private String OrderType;
    private String PN;
    private Integer Qty;
    private String Description;
    private String CustomerCode;
    private String CustomerName;
    private String Currency;
    private BigDecimal NetPrice;
    private BigDecimal USD;
    private Float Rate;
    private Date OrderDate;
    private Date RequestDate;
    private Date PlanedDate;
}
