/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:22:28                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/TobeDealWithOrderLine.java                     *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class TobeDealWithOrderLine {

    private String SalesOrderNO;
    private String ProjectNO;
    private String OrderType;
    private String OrderCategory;
    private String PN;
    private Integer Qty;
    private String Unit;
    private String Description;
    private String CustomerCode;
    private String CustomerName;
    private Date OrderDate;
    private Date DemandDate;
}
