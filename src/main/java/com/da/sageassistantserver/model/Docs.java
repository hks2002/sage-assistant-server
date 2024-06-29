/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-30 00:19:48                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-06-30 00:19:48                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.model;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data

@TableName("docs")
public class Docs {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String file_name;
    private String location;
    private Integer size;
    private Timestamp doc_create_date;
    private Timestamp doc_modified_date;
    private String md5;
    private Timestamp create_at;
    private Long create_by;
    private Timestamp update_at;
    private Long update_by;
}
