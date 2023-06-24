/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 18:04:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 10:48:04                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/CommonController.java                *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.da.sageassistantserver.service.CommonService;

@CrossOrigin
@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @GetMapping("/Data/Sites")
    public String getAllSites() {
        return JSONArray.toJSONString(commonService.getAllSites());
    }
}
