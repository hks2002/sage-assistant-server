/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 21:53:03                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.SuspectDuplicateDataMapper;
import com.da.sage.assistant.model.SuspectDuplicatedPO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuspectDuplicateDataService {

  private final SuspectDuplicateDataMapper dirtyDataMapper;

  public List<SuspectDuplicatedPO> findDuplicatedPOBySite(String Site, String DateFrom, String DateTo) {
    List<SuspectDuplicatedPO> listPage = dirtyDataMapper.findDuplicatedPOBySite(Site, DateFrom, DateTo);

    return listPage;
  }

}
