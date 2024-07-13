/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-11-23 20:45:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-13 20:44:47                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeWorkService {

  /**
   * Caffeine cache
   * Key is Object {corpId: "xxx", cropSecret: "xxxx"} Value is String, a token
   * WeWork access token expires in 7200 seconds, 120 minutes
   * We update it before 100 minutes
   *
   */
  private static LoadingCache<JSONObject, String> accessTokenCache = Caffeine.newBuilder()
      .maximumSize(10 * 3)
      .expireAfterWrite(100, TimeUnit.MINUTES) // 100 minutes, WeWork access token expires in 7200 seconds, 120 minutes
      .build(new CacheLoader<JSONObject, String>() {
        @Override
        public String load(JSONObject key) {
          log.debug("load key: {}", key);

          String corpId = key.getString("corpId");
          String cropSecret = key.getString("cropSecret");

          return getAccessToken(corpId, cropSecret);
        }
      });

  /**
   * Internal method to get we work access token, using <em>accessTokenCache</em>
   * to get
   * token
   * from cache
   */
  public static String getAccessToken(String corpId, String cropSecret) {
    HttpResponse<String> response = HttpService.request(
        "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + cropSecret, "GET", null);
    String html = response.body();
    JSONObject json = JSONObject.parseObject(html);
    try {
      if (json.getInteger("errcode").equals(0)) {
        return json.getString("access_token");
      } else {
        log.error("get access token error: {}", json.getString("errmsg"));
        return null;
      }
    } catch (Exception e) {
      log.error("get access token error: {}", e.getMessage());
      return null;
    }
  }

  /**
   * Send a message
   * Group chart message robot doesn't need token
   * 
   * @param robotId
   * @param data
   */
  public static void sendMessage(String robotId, JSONObject data) {
    HttpResponse<String> response = HttpService.request(
        "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + robotId, "POST",
        data.toJSONString());

    try {
      String html = response.body();
      JSONObject json = JSONObject.parseObject(html);
      if (json.getInteger("errcode").equals(0)) {
        log.debug("Send message: [{}] success!", data.toJSONString());
      } else {
        log.error("Send message: [{}], error: {}", data.toJSONString(), json.getString("errmsg"));
      }
    } catch (Exception e) {
      log.error("Send message", e.getMessage());
    }
  }

  /**
   * Send simple message, text, or markdown
   * Group chart message robot doesn't need token
   * 
   * @param robotId
   * @param messageType
   * @param message
   */
  public static void sendMessage(String robotId, String messageType, String message) {
    JSONObject data = new JSONObject();
    data.put("msgtype", messageType);

    JSONObject msg = new JSONObject();
    msg.put("content", message);

    data.put(messageType, msg);

    sendMessage(robotId, data);
  }

}
