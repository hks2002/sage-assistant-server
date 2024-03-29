/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-11-10 14:18:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:22:50                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/TobeTrackingBOMLine.java                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import lombok.Data;


@Data
public class TobeTrackingBOMLine {

    private String BomProjectNO;
    private String WorkOrderNO;
    private String BomSeq;
    private String BomPN;
    private String BomDesc;
    private Integer BomQTY;
    private String BomUnit;
    private Integer ShortQty;
    private Integer AllQty;
    private String BomRequestDate;
    private String StockPN;
    private Integer AvaQty;
}
