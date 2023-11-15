/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 20:13:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-15 21:04:57                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sageassistantserver.model.DirtyDataDuplicatedPO;
import com.da.sageassistantserver.model.DirtyDataDuplicatedRA;
import com.da.sageassistantserver.service.DirtyDataService;

@CrossOrigin
@RestController
public class DirtyDataController {

        @Autowired
        private DirtyDataService dirtyDataService;

        @GetMapping("/Data/DuplicatedPO")
        public List<DirtyDataDuplicatedPO> findDuplicatedOuterPOBySite(
                        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
                        @RequestParam(value = "DateFrom", required = false, defaultValue = "2999-01-01") String DateFrom,
                        @RequestParam(value = "OnlyForSales", required = false, defaultValue = "N") String OnlyForSales) {
                return dirtyDataService.findDuplicatedPOBySite(
                                Site, DateFrom, OnlyForSales);
        }

        @GetMapping("/Data/DuplicatedRA")
        public List<DirtyDataDuplicatedRA> findDuplicatedRABySite(
                        @RequestParam(value = "Site", required = false, defaultValue = "ZHU") String Site,
                        @RequestParam(value = "DateFrom", required = false, defaultValue = "2999-01-01") String DateFrom,
                        @RequestParam(value = "OnlyForSales", required = false, defaultValue = "N") String OnlyForSales) {
                return dirtyDataService.findDuplicatedRABySite(
                                Site, DateFrom, OnlyForSales);
        }

}
