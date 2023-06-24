/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:20:38                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/DeliveryDuration.java                          *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;


@Data
public class DeliveryDuration {

    private String SalesSite;
    private String PN;
    private Date OrderDate;
    private Date ShipDate;
    private Integer Duration;
}
