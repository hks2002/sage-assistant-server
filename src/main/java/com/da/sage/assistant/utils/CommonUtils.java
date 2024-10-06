/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2023-03-10 15:42:04                                                                      *
 * @LastEditDate          : 2025-07-27 10:09:44                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonUtils {

  public static boolean isWin() {
    String os = System.getProperty("os.name");
    if (os.toLowerCase().startsWith("win")) { // windows
      return true;
    } else { // linux and mac
      return false;
    }
  }

  public static Boolean isZhuhaiClient(String ip) {
    if (ip.startsWith("192.168.0.") ||
        ip.startsWith("192.168.8.") ||
        ip.startsWith("192.168.13.") ||
        ip.startsWith("192.168.253.")) {
      return true;
    }
    return false;
  }

  public static Boolean isZhuhaiServer() {
    try {
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

      while (networkInterfaces.hasMoreElements()) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
          InetAddress address = addresses.nextElement();
          if (!address.isLoopbackAddress() &&
              address.isSiteLocalAddress() &&
              address.getHostAddress().startsWith("192.168.0.")) {
            return true;
          }
        }
      }
      return false;
    } catch (SocketException e) {
      return false;
    }
  }

  public static String decodeBasicAuth(String basicAuth) {
    // if str end with more than one = , remove it
    byte[] decodedBytes = Base64
        .getDecoder()
        .decode(basicAuth.replaceFirst("Basic\\s+", ""));
    return new String(decodedBytes, StandardCharsets.UTF_8);
  }

  public static String encodeBasicAuth(String username, String password) {
    byte[] encodedBytes = Base64
        .getEncoder()
        .encode((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    return "Basic " + new String(encodedBytes, StandardCharsets.UTF_8);
  }

}
