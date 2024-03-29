/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:21:52                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/SupplierDelayHistory.java                      *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;


@Data
public class SupplierDelayHistory {

    private String Site;
    private String SupplierCode;
    private String PurchaseNO;
    private String ProjectNO;
    private String PN;
    private String Description;
    private Date AckDate;
    private Date ExpectDate;
    private Date OrderDate;
    private String ReceiptNO;
    private Date ReceiptDate;
    private int DaysDelay;
}
