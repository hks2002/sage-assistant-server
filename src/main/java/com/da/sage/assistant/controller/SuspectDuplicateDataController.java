/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 20:13:00                                                                       *
 * @LastEditDate          : 2025-07-27 21:56:08                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.model.SuspectDuplicatedPO;
import com.da.sage.assistant.service.SuspectDuplicateDataService;

@CrossOrigin
@RestController
@RequestMapping("/sa-api")
public class SuspectDuplicateDataController {

  @Autowired
  private SuspectDuplicateDataService dirtyDataService;

  @GetMapping("/DuplicatedPO")
  public List<SuspectDuplicatedPO> findDuplicatedPOBySite(
      @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
      @RequestParam(value = "DateFrom", required = false, defaultValue = "2999-01-01") String DateFrom,
      @RequestParam(value = "DateTo", required = false, defaultValue = "2999-01-01") String DateTo) {
    return dirtyDataService.findDuplicatedPOBySite(Site, DateFrom, DateTo);
  }

}
