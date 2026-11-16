/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-09 23:29:08                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-18 09:41:44                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.serviceStatic;

import java.security.MessageDigest;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FS {
  // Static class, must be initialized, be careful the null value

  public static volatile Vertx vertx = null;
  public static volatile FileSystem fs = null;

  public static void setup(Vertx vertx) {
    FS.vertx = vertx;
    FS.fs = vertx.fileSystem();
  }

  /**
   * Compute MD5 checksum for a file
   */
  public static Future<String> computerMd5(String content) {
    return vertx.executeBlocking(() -> {
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = content.getBytes();
        md.update(bytes);
        byte[] digest = md.digest();

        return new String(digest);
      } catch (Exception e) {
        log.error("Failed to compute MD5 {}: {}", content, e.getMessage());
        throw e;
      }
    });
  }

}