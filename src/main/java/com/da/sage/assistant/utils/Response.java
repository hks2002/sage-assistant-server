/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2023-02-19 20:31:38                                                                       *
 * @LastEditDate          : 2025-08-03 21:15:32                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.utils;

import java.io.IOException;

import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Response {

  public static void missingAuth(HttpServletResponse res) throws IOException {
    res.setContentType(MediaType.APPLICATION_JSON_VALUE);
    res.setCharacterEncoding("UTF-8");
    res.setStatus(401);
    res.getWriter().write(ResponseJson.missingAuth().toString());
  }

  public static void paraRequired(HttpServletResponse res) throws IOException {
    res.setContentType(MediaType.APPLICATION_JSON_VALUE);
    res.setCharacterEncoding("UTF-8");
    res.setStatus(401);
    res.getWriter().write(ResponseJson.unauthorized("Login failed").toString());
  }

  public static void unauthorized(HttpServletResponse res) throws IOException {
    res.setContentType(MediaType.APPLICATION_JSON_VALUE);
    res.setCharacterEncoding("UTF-8");
    res.setStatus(401);
    res.getWriter().write(ResponseJson.unauthorized("Unauthorized").toString());
  }

}
