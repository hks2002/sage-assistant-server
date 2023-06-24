/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-09-21 09:17:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 20:27:05                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/AnalysesPurchase.java                     *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysesPurchase {

    private String ProjectNO;
    private String PurchaseNO;
    private Double NetPrice;
    private Date ProjectDate;
    private Date PurchaseDate;
    private Integer ROWNUM;
}
