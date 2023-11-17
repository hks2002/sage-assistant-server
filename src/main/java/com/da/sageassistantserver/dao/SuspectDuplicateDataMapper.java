/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:55:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-17 13:07:03                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sageassistantserver.model.SuspectDuplicatedPO;
import com.da.sageassistantserver.model.SuspectDuplicatedRA;

@Mapper
public interface SuspectDuplicateDataMapper {

        List<SuspectDuplicatedPO> findDuplicatedPOBySite(@Param("Site") String Site,
                        @Param("DateFrom") String DateFrom, @Param("OnlyForSales") String OnlyForSales);

        List<SuspectDuplicatedRA> findDuplicatedRABySite(@Param("Site") String Site,
                        @Param("DateFrom") String DateFrom, @Param("OnlyForSales") String OnlyForSales);
}
