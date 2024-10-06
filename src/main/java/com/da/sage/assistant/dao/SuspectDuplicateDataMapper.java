/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                       *
 * @LastEditDate          : 2025-07-27 21:52:08                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.SuspectDuplicatedPO;

@Mapper
public interface SuspectDuplicateDataMapper {
  List<SuspectDuplicatedPO> findDuplicatedPOBySite(
      @Param("Site") String Site,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo);
}
