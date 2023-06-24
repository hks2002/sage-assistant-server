/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-06-06 15:47:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 12:11:06                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/TrackingNotes.java                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingNotes {

    private String TrackCode;
    private String Note;
    private String NoteBy;
    private Date NoteDate;
}
