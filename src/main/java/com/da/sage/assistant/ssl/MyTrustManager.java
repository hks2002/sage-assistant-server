/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-06-03 09:42:01                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-01-22 15:41:30                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/

package com.da.sage.assistant.ssl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements X509TrustManager {
  @Override
  public void checkClientTrusted(X509Certificate[] chain, String authType) {
    // Do nothing to trust all clients,
    // but this is not recommended for production use.
  }

  @Override
  public void checkServerTrusted(X509Certificate[] chain, String authType) {
    // Do nothing to trust all server,
    // but this is not recommended for production use.
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[0];
  }
}
