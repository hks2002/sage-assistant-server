/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-30 00:19:48                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-09-04 19:34:00                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data

@TableName("wework_robot")
public class WeworkRobot {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String notice_code;
  private String robot_code;
  private String robot_name;
  private String robot_uuid;
  private String sites;
  private Boolean enable;
}
