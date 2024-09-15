/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-31 16:12:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 13:36:36                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/CustomerDeliveryHistory.java              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class CustomerDeliveryHistory {

    private String Site;
    private String CustomerCode;
    private String OrderNO;
    private String ProjectNO;
    private String PN;
    private String Description;
    private Date OrderDate;
    private Date DemandDate;
    private Date ShipDate;
    private String DeliveryNO;
    private int DaysNeed;
}
