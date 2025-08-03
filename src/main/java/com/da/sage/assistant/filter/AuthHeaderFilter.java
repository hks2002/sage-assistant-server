/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2024-07-04 09:39:40                                                                       *
 * @LastEditDate          : 2025-08-06 10:52:55                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.service.LoginService;
import com.da.sage.assistant.utils.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AuthHeaderFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(
      @SuppressWarnings("null") HttpServletRequest req) throws ServletException {
    return (req.getRequestURI().startsWith("/sa-api/Login") ||
        req.getRequestURI().startsWith("/sa-api/Logout") ||
        req.getRequestURI().startsWith("/sa-api/Batch"))
            ? true
            : false;
  }

  @Override
  protected void doFilterInternal(
      @SuppressWarnings("null") HttpServletRequest req,
      @SuppressWarnings("null") HttpServletResponse res,
      @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
    String auth = req.getHeader("Authorization");

    if (auth == null) {
      Response.missingAuth(res);
    } else {
      JSONObject rtn = LoginService.doLogin(auth);
      if (!rtn.getBoolean("success")) {
        Response.unauthorized(res);
      } else {
        filterChain.doFilter(req, res);
      }
    }
  }
}
