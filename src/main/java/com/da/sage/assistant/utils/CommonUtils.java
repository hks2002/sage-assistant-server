/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-09 23:29:08                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-06-11 10:04:34                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.utils;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonUtils {

  public static String normalizePath(String path) {
    try {
      URI uri = new URI(path);
      return uri.normalize().getPath();
    } catch (URISyntaxException e) {
      return null;
    }
  }

}
