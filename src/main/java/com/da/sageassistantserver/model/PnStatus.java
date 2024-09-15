/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 15:07:31                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/PnStatus.java                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class PnStatus {

    private String PN;
    private String desc1;
    private String Desc2;
    private String Desc3;
    private String PNStatus;
    private String WC;
    private String ProjectNO;
    private String CustomerCode;
    private String CustomerName;
}
