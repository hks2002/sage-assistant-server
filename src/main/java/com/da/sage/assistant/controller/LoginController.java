/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-25 15:19:00                                                                       *
 * @LastEditDate          : 2025-07-18 19:35:56                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sa-api")
public class LoginController {

  @PostMapping("/Login")
  public JSONObject doLogin(@RequestHeader(value = "Authorization") String Auth,
      HttpServletRequest request) {
    return LoginService.doLogin(Auth);
  }

  @PostMapping("/Logout")
  public JSONObject doLogout(@RequestHeader(value = "Authorization") String Auth,
      HttpServletRequest request) {
    return LoginService.doLogout(Auth);
  }

}
