/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-11-23 20:45:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-08-06 23:22:06                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MSteamsService {

  /**
   * Send a message
   * Group chart message robot doesn't need token
   * 
   * @param workFlowUrl
   * @param data
   */
  public static void sendMessage(String workFlowUrl, JSONObject data) {
    HttpResponse<String> response = HttpService.request(workFlowUrl, "POST", data.toJSONString());

    if (response.statusCode() != 202) {
      try {
        String html = response.body();
        JSONObject json = JSONObject.parseObject(html);
        JSONObject error = json.getJSONObject("error");

        log.error("Send message: [{}], error: {}", error.getString("code"), error.getString("message"));

      } catch (Exception e) {
        log.error("Send message", e.getMessage());
      }
    } else {
      log.debug("Send message: [{}] success!", data.toJSONString());
    }
  }

  /**
   * Send simple message, text
   * 
   * @param workFlowUrl
   * @param message
   */
  public static void sendMessage(String workFlowUrl, String message) {
    JSONObject messageObject = new JSONObject();

    JSONArray attachmentsArray = new JSONArray();

    JSONObject attachmentObject = new JSONObject();
    attachmentObject.put("contentType", "application/vnd.microsoft.card.adaptive");

    JSONObject contentObject = new JSONObject();
    contentObject.put("type", "AdaptiveCard");

    JSONArray bodyArray = new JSONArray();

    JSONObject textBlockObject = new JSONObject();
    textBlockObject.put("type", "TextBlock");
    textBlockObject.put("text", message);

    bodyArray.add(textBlockObject);

    contentObject.put("body", bodyArray);
    attachmentObject.put("content", contentObject);
    attachmentsArray.add(attachmentObject);
    messageObject.put("attachments", attachmentsArray);

    sendMessage(workFlowUrl, messageObject);
  }

}
