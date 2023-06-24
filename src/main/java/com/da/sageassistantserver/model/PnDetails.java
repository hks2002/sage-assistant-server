/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 21:20:13                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/PnDetails.java                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import lombok.Data;

@Data
public class PnDetails {

    private Integer ROWID;
    private String PNROOT;
    private String PN;
    private String Cat;
    private String Version;
    private String Comment;
    private String Desc1;
    private String Desc2;
    private String Desc3;
    private Integer Status;
    private Date CreateDate;
}
