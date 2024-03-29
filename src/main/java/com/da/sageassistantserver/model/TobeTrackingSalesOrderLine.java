/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-06-09 13:13:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-29 14:52:46                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;

@Data
public class TobeTrackingSalesOrderLine {

    private Integer ItemNO;
    private String OrderNO;
    private String OrderLine;
    private String TrackingNO;
    private String ProjectNO;
    private String OriProjectNO;
    private String OrderType;
    private String OrderPN;
    private String OrderPNVersion;
    private String OrderPNDesc;
    private Integer OrderQTY;
    private Float OrderPrice;
    private String OrderCurrency;
    private String CustomerCode;
    private String CustomerName;
    private Date OrderDate;
    private Date OrderRequestDate;
    private Date OrderPlanedDate;
    private String OrderSADFlag;
    private Date OrderSADDate;
    private String OrderProductFlag;
    private Date OrderProductDate;
    private String OrderDeliveryFlag;
    private String ProjectStatus;
    private String ProjectBlockReason;
    private String ProjectComment;
    private String ProjectAction;
    private Integer DaysLeft;
}
