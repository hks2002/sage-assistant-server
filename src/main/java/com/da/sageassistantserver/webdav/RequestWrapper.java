/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-16 23:24:10                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-01 00:09:49                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.webdav;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
  public RequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
  }

  @Override
  public String getHeader(String name) {
    String superHeader = super.getHeader(name);
    // Browsers use If-None-Match send the ETag value back to Server, here change it
    // to
    // W/\"1000000-1000000000000\"
    // So that the Server don't return 304,
    if ("If-None-Match".equals(name)) {
      return "W/\"1000000-1000000000000\"";
    }

    // use-agent: Microsoft-WebDAV-MiniRedir doesn't sent the ETag back to Server
    // it send "if-modified-since" instead,
    // Refresh the page to will get a content after keep-alive timeout
    if ("if-modified-since".equals(name)) {
      return "Mon, 01 Jan 1990 00:00:00 GMT";
    }
    return superHeader;
  }

}