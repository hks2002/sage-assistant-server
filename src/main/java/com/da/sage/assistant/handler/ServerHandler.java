/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-01-26 15:13:09                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-16 23:26:58                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.da.sage.assistant.serviceStatic.RESPONSE;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServerHandler {

  public void info(RoutingContext ctx) {
    Properties props = new Properties();

    try {
      InputStream is = getClass().getClassLoader()
          .getResourceAsStream("app.properties");
      props.load(is);

      JsonObject obj = new JsonObject();
      obj.put("name", props.getProperty("app.name"));
      obj.put("version", props.getProperty("app.version"));

      RESPONSE.success(ctx, obj);

    } catch (IOException e) {
      e.printStackTrace();
      RESPONSE.internalError(ctx, "Failed to load app.properties");
    }
  }

  public void dependencies(RoutingContext ctx) {
    Properties props = new Properties();

    try {
      InputStream is = getClass().getClassLoader()
          .getResourceAsStream("app.properties");
      props.load(is);

      String raw = props.getProperty("app.dependencies");
      String json = raw
          .replace("Dependency {", "{")
          .replace("=", "\":\"")
          .replace(", ", "\",\"")
          .replace("{", "{\"")
          .replace("}", "\"}")
          .replace("\"{", "{")
          .replace("}\"", "}");
      log.debug(json);
      JsonArray finalDeps = new JsonArray(json);

      RESPONSE.success(ctx, finalDeps);

    } catch (IOException e) {
      RESPONSE.internalError(ctx, e.getMessage());
    }
  }
}