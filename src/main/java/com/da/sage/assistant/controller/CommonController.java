/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 18:04:00                                                                       *
 * @LastEditDate          : 2025-07-18 14:03:40                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.da.sage.assistant.service.CommonService;
import com.da.sage.assistant.utils.HttpUtils;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/sa-api")
public class CommonController {

  @Autowired
  CommonService commonService;

  @GetMapping("/Sites")
  public List<String> getAllSites() {
    return (commonService.getAllSites());
  }

  @GetMapping("/ClientIP")
  public String getRequestIP(HttpServletRequest request) {
    for (String header : HttpUtils.IP_HEADERS) {
      String value = request.getHeader(header);
      if (value == null || value.isEmpty()) {
        continue;
      }
      String[] parts = value.split("\\s*,\\s*");
      return parts[0];
    }
    return request.getRemoteAddr();
  }
}
