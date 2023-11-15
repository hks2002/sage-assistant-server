/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:57:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-15 21:04:23                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

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

    public List<DirtyDataDuplicatedPO> findDuplicatedPOBySite(String Site, String DateFrom, String OnlyForSales) {
        List<DirtyDataDuplicatedPO> listPage = dirtyDataMapper.findDuplicatedPOBySite(Site, DateFrom, OnlyForSales);

        return listPage;
    }

    public List<DirtyDataDuplicatedRA> findDuplicatedRABySite(String Site, String DateFrom, String OnlyForSales) {
        List<DirtyDataDuplicatedRA> listPage = dirtyDataMapper.findDuplicatedRABySite(Site, DateFrom, OnlyForSales);

        return listPage;
    }

}
