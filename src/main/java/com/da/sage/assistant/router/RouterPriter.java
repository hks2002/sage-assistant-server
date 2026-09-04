/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-14 21:15:24                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-28 11:25:07                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.router;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.impl.RouteImpl;
import io.vertx.ext.web.impl.RouterImpl;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RouterPriter {

  public static void print(Router root) {
    print(root, "");
  }

  public static void print(Router router, String prefix) {
    if (!(router instanceof RouterImpl ri)) {
      return;
    }

    ri.getRoutes().forEach(route -> {
      if (!(route instanceof RouteImpl r)) {
        return;
      }

      String path = r.getPath();
      String fullPath = prefix;

      if (path != null) {
        if (path.equals("/*")) {
        } else if (path.endsWith("/*")) {
          fullPath = prefix + path.substring(1, path.length() - 2);
        } else {
          fullPath = prefix + path.substring(1, path.length());
        }
      }

      // 只有“真正业务 route”才打印
      if (r.getSubRouter() == null && path != null) {
        var methods = r.methods();
        String methodStr = (methods == null || methods.isEmpty()) ? "[ALL]" : methods.toString();

        log.info("{} {}", String.format("%10s", methodStr), fullPath);
      }

      // recursively print subRouter
      if (r.getSubRouter() != null) {
        print(r.getSubRouter(), fullPath);
      }
    });
  }
}
