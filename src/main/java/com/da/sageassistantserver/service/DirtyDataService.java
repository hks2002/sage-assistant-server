/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 16:03:39                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/service/StatusService.java                      *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.da.sageassistantserver.dao.DirtyDataMapper;
import com.da.sageassistantserver.model.DirtyDataDuplicatedPO;
import com.da.sageassistantserver.model.DirtyDataDuplicatedRA;

@Service
public class DirtyDataService {

    @Autowired
    private DirtyDataMapper dirtyDataMapper;

    public List<DirtyDataDuplicatedPO> findDuplicatedInterPOBySite(String Site) {
        List<DirtyDataDuplicatedPO> listPage = dirtyDataMapper.findDuplicatedInterPOBySite(Site);

        return listPage;
    }

    public List<DirtyDataDuplicatedPO> findDuplicatedOuterPOBySite(String Site, String DateFrom) {
        List<DirtyDataDuplicatedPO> listPage = dirtyDataMapper.findDuplicatedOuterPOBySite(Site, DateFrom);

        return listPage;
    }

    public List<DirtyDataDuplicatedRA> findDuplicatedRABySite(String Site, String DateFrom) {
        List<DirtyDataDuplicatedRA> listPage = dirtyDataMapper.findDuplicatedRABySite(Site, DateFrom);

        return listPage;
    }

}
