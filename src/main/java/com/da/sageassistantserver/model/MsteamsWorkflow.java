/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-30 00:19:48                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-08-07 00:15:03                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("msteams_workflow")
public class MsteamsWorkflow {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String flow_code;
  private String flow_name;
  private String flow_url;
  private String sites;
}
