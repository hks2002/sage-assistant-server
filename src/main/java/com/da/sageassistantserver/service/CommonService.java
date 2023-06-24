/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 16:00:40                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/service/CommonService.java                      *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.da.sageassistantserver.dao.CommonMapper;

@Service
public class CommonService {

    @Autowired
    private CommonMapper commonMapper;

    public List<String> getAllSites() {
        return commonMapper.getAllSites();
    }
}
