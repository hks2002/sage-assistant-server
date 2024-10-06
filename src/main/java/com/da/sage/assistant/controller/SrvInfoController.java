/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-25 15:19:00                                                                       *
 * @LastEditDate          : 2025-07-21 15:01:52                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.config.ProjectProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class SrvInfoController {

  final ProjectProperties projectInfo;

  @GetMapping("/SrvVersion")
  public String getVersion() {
    return projectInfo.getVersion();
  }

  @GetMapping("/SrvName")
  public String getName() {
    return projectInfo.getName();
  }

  @GetMapping("/SrvInfo")
  public JSONObject getInfo() {
    JSONObject obj = new JSONObject();
    obj.put("name", getName());
    obj.put("version", getVersion());
    obj.put("springBootVersion", projectInfo.getParentVersion());
    return obj;
  }

  @GetMapping("/SrvProjectDependencies")
  public JSONArray getDependencies() {
    String str = "";
    str = projectInfo.getDependencies().replace("Dependency", "");
    str = str.replaceAll("=", ":");
    str = str.replaceAll(":([^,}]+)", ":\"$1\"");
    JSONArray json = JSONArray.parseArray(str);
    return json;
  }
}
