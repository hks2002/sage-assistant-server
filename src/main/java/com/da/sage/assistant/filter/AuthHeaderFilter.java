/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2024-07-04 09:39:40                                                                       *
 * @LastEditDate          : 2025-07-24 22:05:16                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import com.da.sage.assistant.utils.ResponseJson;

//import com.da.sage.assistant.utils.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Order(2)
@Log4j2
@Component
public class AuthHeaderFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      @SuppressWarnings("null") HttpServletRequest req,
      @SuppressWarnings("null") HttpServletResponse res,
      @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
    String auth = req.getHeader("Authorization");

    if (auth == null) {
      // res.setContentType(MediaType.APPLICATION_JSON_VALUE);
      // res.setCharacterEncoding("UTF-8");
      // res.setStatus(401);
      // res.getWriter().write(ResponseJson.missingAuth().toString());
      filterChain.doFilter(req, res);
    } else {
      filterChain.doFilter(req, res);
    }
  }
}
