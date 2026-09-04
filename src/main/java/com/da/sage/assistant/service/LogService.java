/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 15:17:16                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-08-28 19:21:09                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogService {
  private static DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  public static Future<JsonObject> searchLog(JsonObject obj) {
    return MapperService.query("LogMapper.log", obj);
  }

  public static Future<Long> addLog(
      String TCode,
      String ip,
      String log_by,
      String v0,
      String v1,
      String v2,
      String v3,
      String v4,
      String v5,
      String v6,
      String v7,
      String v8,
      String v9) {
    return MapperService.query("LogTemplateMapper.logTemplate", JsonObject.of("template_code", TCode))
        .compose(result -> {
          JsonArray data = result.getJsonArray("data");
          if (data.size() == 0) {
            var msg = "template code " + TCode + " not found";
            log.info(msg);
            return Future.succeededFuture();

          } else if (data.size() == 1) {
            JsonObject log = new JsonObject();

            log.put("template_id", data.getJsonObject(0).getInteger("id"));
            log.put("ip", ip);
            log.put("log_by", log_by);
            log.put("log_at", LocalDateTime.now().format(fmt));
            log.put("v0", v0);
            log.put("v1", v1);
            log.put("v2", v2);
            log.put("v3", v3);
            log.put("v4", v4);
            log.put("v5", v5);
            log.put("v6", v6);
            log.put("v7", v7);
            log.put("v8", v8);
            log.put("v9", v9);

            return MapperService.insert("LogMapper.log", log);
          } else {
            var msg = "template code " + TCode + " more than one";
            log.error(msg);
            return Future.failedFuture(msg);
          }
        });
  }

  public static Future<Long> addLog(
      String TCode,
      String ip,
      String log_by,
      String v0,
      String v1,
      String v2,
      String v3,
      String v4,
      String v5,
      String v6,
      String v7,
      String v8) {
    return addLog(TCode, ip, log_by, v0, v1, v2, v3, v4, v5, v6, v7, v8, null);
  }

  public static Future<Long> addLog(
      String TCode,
      String ip,
      String log_by,
      String v0,
      String v1,
      String v2,
      String v3,
      String v4,
      String v5,
      String v6,
      String v7) {
    return addLog(TCode, ip, log_by, v0, v1, v2, v3, v4, v5, v6, v7, null);
  }

  public static Future<Long> addLog(
      String TCode,
      String ip,
      String log_by,
      String v0,
      String v1,
      String v2,
      String v3,
      String v4,
      String v5,
      String v6) {
    return addLog(TCode, ip, log_by, v0, v1, v2, v3, v4, v5, v6, null, null, null);
  }

  public static Future<Long> addLog(
      String TCode,
      String ip,
      String log_by,
      String v0,
      String v1,
      String v2,
      String v3,
      String v4,
      String v5) {
    return addLog(TCode, ip, log_by, v0, v1, v2, v3, v4, v5, null, null, null, null);
  }

  public static Future<Long> addLog(
      String TCode,
      String ip,
      String log_by,
      String v0,
      String v1,
      String v2,
      String v3,
      String v4) {
    return addLog(TCode, ip, log_by, v0, v1, v2, v3, v4, null, null, null, null, null);
  }

  public static Future<Long> addLog(String TCode,
      String ip,
      String log_by, String v0, String v1, String v2, String v3) {
    return addLog(TCode, ip, log_by, v0, v1, v2, v3, null, null, null, null, null, null);
  }

  public static Future<Long> addLog(String TCode,
      String ip,
      String log_by, String v0, String v1, String v2) {
    return addLog(TCode, ip, log_by, v0, v1, v2, null, null, null, null, null, null, null);
  }

  public static Future<Long> addLog(String TCode,
      String ip,
      String log_by, String v0, String v1) {
    return addLog(TCode, ip, log_by, v0, v1, null, null, null, null, null, null, null, null);
  }

  public static Future<Long> addLog(String TCode,
      String ip,
      String log_by, String v0) {
    return addLog(TCode, ip, log_by, v0, null, null, null, null, null, null, null, null, null);
  }

  public static Future<Long> addLog(String TCode,
      String ip,
      String log_by) {
    return addLog(TCode, ip, log_by, null, null, null, null, null, null, null, null, null, null);
  }
}
