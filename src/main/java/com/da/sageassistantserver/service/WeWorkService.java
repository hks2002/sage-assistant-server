/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-11-23 20:45:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-09-05 18:44:08                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.net.http.HttpResponse;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeWorkService {
  private static HashMap<String, Queue<String>> robotMapQueue = new HashMap<>();

  @Scheduled(cron = "0 0/1 * * * MON-FRI")
  private static void runWithLimit() {
    // loop over all robots
    robotMapQueue.forEach((robotId, queue) -> {
      // check if the queue is empty
      if (queue.isEmpty()) {
        return;
      }

      // get 20 messages from the queue, wework webhook api has a limit of
      // 20/min
      int limit = 20;
      for (int i = 0; i < limit && !queue.isEmpty(); i++) {
        String data = robotMapQueue.get(robotId).poll();

        HttpResponse<String> response = HttpService.request(
            "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + robotId,
            "POST", data);

        try {
          String html = response.body();
          JSONObject json = JSONObject.parseObject(html);
          if (json.getInteger("errcode").equals(0)) {
            log.debug("Send message: [{}] success!", data);
          } else {
            log.error("Send message: [{}], error: {}", data,
                json.getString("errmsg"));
          }
        } catch (Exception e) {
          log.error("Send message", e.getMessage());
        }
      }
    });
  }

  /**
   * Send a message
   *
   * @param robotId
   * @param data    JsonObject for webhook api
   */
  private static void sendMessage(String robotId, JSONObject data) {
    Queue<String> queue = robotMapQueue.get(robotId);
    if (queue == null) {
      queue = new ArrayDeque<>();
    }

    queue.offer(data.toJSONString());
    robotMapQueue.put(robotId, queue);
  }

  /**
   * Send simple message, text, or markdown
   * Group chart message robot doesn't need token
   *
   * @param robotId
   * @param messageType
   * @param message
   */
  public static void sendMessage(String robotId, String message,
      String messageType) {
    Utils.splitStringByByteSize(message, 2048).forEach(limitLenMsg -> {
      JSONObject data = new JSONObject();
      JSONObject msg = new JSONObject();

      data.put("msgtype", messageType);

      StringBuilder sb = new StringBuilder();
      String[] paragraphs = limitLenMsg.split("\n\n");

      for (String paragraph : paragraphs) {

        String[] lines = paragraph.split("\n");
        for (String line : lines) {
          if (line.indexOf(":\t") > -1) {
            String[] factKV = line.split("\t");
            sb.append(Utils.withRightPad(factKV[0], 5, '\u3000')); // 全角空格
            if (factKV.length == 2) {
              sb.append(factKV[1]);
              sb.append('\n');
            }
          } else {
            sb.append(line);
            sb.append('\n');
          }
        }

        sb.append('\n');
      }

      msg.put("content", sb.toString());
      data.put(messageType, msg);

      sendMessage(robotId, data);
    });
  }

  public static void sendMessage(String robotId, String message) {
    sendMessage(robotId, message, "text");
  }
}
