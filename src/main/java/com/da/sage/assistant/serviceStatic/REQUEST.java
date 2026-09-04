/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-09 23:29:08                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-03-25 14:24:44                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.serviceStatic;

import java.nio.charset.StandardCharsets;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.Utils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class REQUEST {

  private static final String[] IP_HEADERS = {
      "X-Forwarded-For",
      "Proxy-Client-IP",
      "WL-Proxy-Client-IP",
      "X-Real-IP",
      "HTTP_CLIENT_IP",
      "HTTP_X_FORWARDED_FOR"
  };

  public static String getTrueRemoteIp(RoutingContext ctx) {
    HttpServerRequest request = ctx.request();

    for (String header : IP_HEADERS) {
      String ip = request.getHeader(header);
      if (ip != null && ip.length() > 0) {
        if ("X-Forwarded-For".equalsIgnoreCase(header) && ip.contains(",")) {
          ip = ip.split(",")[0].trim();
        }
        return ip.trim();
      }
    }

    return request.connection().remoteAddress(true).hostAddress();
  }

  public static UsernamePasswordCredentials getCredentials(RoutingContext ctx) {
    String authorization = ctx.request().getHeader("Authorization");
    if (authorization == null) {
      return null;
    }
    int idx = authorization.indexOf(' ');
    if (idx == -1) {
      return null;
    }
    String userName;
    String password;
    String decoded = new String(Utils.base64Decode(authorization.substring(idx + 1)), StandardCharsets.UTF_8);
    int colonIdx = decoded.indexOf(":");
    if (colonIdx != -1) {
      userName = decoded.substring(0, colonIdx);
      password = decoded.substring(colonIdx + 1);
    } else {
      userName = decoded;
      password = null;
    }

    return new UsernamePasswordCredentials(userName, password);
  }

  public static JsonObject getQueryJson(RoutingContext ctx) {
    JsonObject json = new JsonObject();
    ctx.queryParams().forEach(entry -> json.put(entry.getKey(), entry.getValue()));
    return json;
  }

  public static boolean isFileUpload(RoutingContext ctx) {
    String contentType = ctx.request().getHeader("Content-Type");

    if (contentType == null || !contentType.startsWith("multipart/form-data")) {
      return false;
    }
    return true;
  }
}