/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-21 23:32:12                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.CommonMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonService {

  private final CommonMapper commonMapper;

  public List<String> getAllSites() {
    List<String> list = commonMapper.getAllSites();
    return list;
  }
}
