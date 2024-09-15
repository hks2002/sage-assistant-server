/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-30 00:19:48                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-09-04 19:34:29                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
@TableName("msteams_workflow")
public class MsteamsWorkflow {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String notice_code;
  private String flow_code;
  private String flow_name;
  private String flow_url;
  private String sites;
  private Boolean enable;
}
