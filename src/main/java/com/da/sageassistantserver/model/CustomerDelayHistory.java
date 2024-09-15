/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-31 16:08:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 13:22:13                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/CustomerDelayHistory.java                 *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerDelayHistory {

    private String Site;
    private String CustomerCode;
    private String OrderNO;
    private String ProjectNO;
    private String PN;
    private String Description;
    private Date AckDate;
    private Date DemandDate;
    private Date OrderDate;
    private String DeliveryNO;
    private Date ShipDate;
    private int DaysDelay;
}
