/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2024-07-04 09:39:40                                                                       *
 * @LastEditDate          : 2025-07-18 14:23:06                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.filter;

import java.io.IOException;
import java.security.Principal;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import com.da.sage.assistant.utils.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Order(1)
@Log4j2
@Component
public class SessionFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(
      @SuppressWarnings("null") HttpServletRequest req) throws ServletException {
    return (req.getRequestURI().startsWith("/sage-assistant-api/Login") ||
        req.getRequestURI().startsWith("/sage-assistant-api/Logout") ||
        req.getRequestURI().startsWith("/sage-assistant-api/Analyses") ||
        req.getRequestURI().startsWith("/sage-assistant-web/") ||
        req.getRequestURI().startsWith("/.well-known/appspecific/com.chrome.devtools.json") ||
        req.getRequestURI().startsWith("/favicon.ico"))
            ? true
            : false;
  }

  @Override
  protected void doFilterInternal(
      @SuppressWarnings("null") HttpServletRequest req,
      @SuppressWarnings("null") HttpServletResponse res,
      @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
    Principal userPrincipal = req.getUserPrincipal();

    if (userPrincipal == null) {
      filterChain.doFilter(req, res);
    } else {
      filterChain.doFilter(req, res);
    }
  }
}
