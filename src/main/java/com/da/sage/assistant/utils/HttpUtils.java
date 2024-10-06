/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2025-07-08 21:31:42                                                                      *
 * @LastEditDate          : 2025-07-18 13:57:05                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.utils;

import java.util.List;

import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {
  public static MediaType getAccept(HttpServletRequest request) {
    List<MediaType> accepts = MediaType.parseMediaTypes(request.getHeader("Accept"));
    MediaType accept = MediaType.TEXT_PLAIN;

    if (accepts != null) {
      accept = accepts.get(0);
    }
    return accept;
  }

  /* clang-format off */
  public static final String[] IP_HEADERS = {
      "X-Forwarded-For",
      "Proxy-Client-IP",
      "WL-Proxy-Client-IP",
      "HTTP_X_FORWARDED_FOR",
      "HTTP_X_FORWARDED",
      "HTTP_X_CLUSTER_CLIENT_IP",
      "HTTP_CLIENT_IP",
      "HTTP_FORWARDED_FOR",
      "HTTP_FORWARDED",
      "HTTP_VIA",
      "REMOTE_ADDR"
      // you can add more matching headers here ...
  };
}
