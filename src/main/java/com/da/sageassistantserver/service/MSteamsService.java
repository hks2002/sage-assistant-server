/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-11-23 20:45:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-09-05 18:44:33                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.net.http.HttpResponse;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MSteamsService {
  private static HashMap<String, Queue<String>> robotMapQueue = new HashMap<>();

  @Scheduled(cron = "0 0/1 * * * MON-FRI")
  private static void runWithLimit() {
    // loop over all robots
    robotMapQueue.forEach((workFlowUrl, queue) -> {
      // check if the queue is empty
      if (queue.isEmpty()) {
        return;
      }

      // get 4 messages from the queue, wework webhook api has a limit of
      // 4/min
      int limit = 4;
      for (int i = 0; i < limit && !queue.isEmpty(); i++) {
        String data = robotMapQueue.get(workFlowUrl).poll();

        HttpResponse<String> response = HttpService.request(workFlowUrl, "POST", data);

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
          log.debug("Send message: [{}] success!", data);
        }
      }
    });
  }

  /**
   * Send a message
   * Group chart message robot doesn't need token
   * 
   * @param workFlowUrl
   * @param data
   */
  public static void sendMessage(String workFlowUrl, JSONObject data) {
    Queue<String> queue = robotMapQueue.get(workFlowUrl);
    if (queue == null) {
      queue = new ArrayDeque<>();
    }

    queue.offer(data.toJSONString());
    robotMapQueue.put(workFlowUrl, queue);
  }

  /**
   * Send simple message, text
   * 
   * @reference https://adaptivecards.io/explorer/AdaptiveCard.html
   * @param workFlowUrl
   * @param msg
   */
  public static void sendMessage(String workFlowUrl, String totalMessage) {
    Utils.splitStringByByteSize(totalMessage, 2048).forEach(msg -> {
      JSONObject message = new JSONObject();
      JSONArray attachments = new JSONArray();
      JSONObject attachment = new JSONObject();
      JSONObject content = new JSONObject();
      JSONArray body = new JSONArray();

      String[] paragraphs = msg.split("\n\n");
      for (String pText : paragraphs) {
        JSONObject p = new JSONObject();
        boolean isLineText = pText.contains(":\t");
        String[] lines = pText.split("\n");

        if (isLineText) {
          JSONArray FactSet = new JSONArray();
          for (String line : lines) {
            JSONObject Fact = new JSONObject();
            if (line.contains(":\t")) {
              String[] factKV = line.split(":\t");
              Fact.put("title", factKV[0]);
              Fact.put("value", factKV[1]);
            } else {
              Fact.put("title", line);
            }
            FactSet.add(Fact);
          }

          p.put("type", "FactSet");
          p.put("facts", FactSet);
          p.put("separator", true);
        } else {
          p.put("type", "TextBlock");
          p.put("wrap", true);
          p.put("weight", "Bolder");
          p.put("size", "Large");
          p.put("text", pText);
        }

        body.add(p);
      }

      content.put("type", "AdaptiveCard");
      content.put("version", "1.3");
      content.put("fallbackText", "Fallback text");
      content.put("$schema", "http://adaptivecards.io/schemas/adaptive-card.json");
      content.put("body", body);
      attachment.put("contentType", "application/vnd.microsoft.card.adaptive");
      attachment.put("content", content);
      attachments.add(attachment);
      message.put("attachments", attachments);

      log.debug(message.toJSONString());
      sendMessage(workFlowUrl, message);
    });

  }

}
