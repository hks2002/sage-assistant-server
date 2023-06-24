/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 12:03:14                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/Attachment.java                           *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {

    private String PN;
    private String DocType;
    private String Path;
    private String File;
    private String Cat;
}
