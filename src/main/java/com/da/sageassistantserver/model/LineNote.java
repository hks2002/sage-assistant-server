/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-09-21 12:36:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-08-14 15:36:33                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
@TableName("line_note")
public class LineNote {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String line_type;
  private String project;
  private String line;
  private String note;
  private String note_user;
  private Date note_at;
}
