/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-06 16:39:59                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-06-08 00:31:21                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.da.sageassistantserver.model.LogRaw;

@Mapper
@DS("slave")
public interface LogRawMapper {
    List<LogRaw> findLogs(@Param("TCode") String TCode,
            @Param("TGroup") String TGroup,
            @Param("DateFrom") String DateFrom,
            @Param("DateTo") String DateTo,
            @Param("Language") String Language);
}
