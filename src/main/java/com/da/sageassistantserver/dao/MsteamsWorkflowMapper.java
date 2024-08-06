/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2024-06-02 17:50:39                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-08-07 00:16:31                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.da.sageassistantserver.model.MsteamsWorkflow;

@Mapper
@DS("slave")
public interface MsteamsWorkflowMapper extends BaseMapper<MsteamsWorkflow> {
}
