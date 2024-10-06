/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2023-02-19 20:31:38                                                                       *
 * @LastEditDate          : 2025-07-21 23:20:34                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.utils;

import com.alibaba.fastjson2.JSONObject;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResponseJson {

  public enum MsgTyp {
    RESULT(0),
    INFO(1),
    QUESTION(2),
    WARN(3),
    ERROR(4);

    private Integer value;

    MsgTyp(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }

  public static JSONObject obj(Boolean success, MsgTyp msgTyp, String msg) {
    switch (msgTyp) {
      case RESULT:
        break;
      case INFO:
        break;
      case QUESTION:
        break;
      case WARN:
        break;
      case ERROR:
        log.error(msg);
        break;
      default:
        break;
    }
    JSONObject rtn = new JSONObject();
    rtn.put("success", success);
    rtn.put("msgTyp", msgTyp);
    rtn.put("msg", msg);
    return rtn;
  }

  public static JSONObject obj(Boolean success, MsgTyp msgTyp, JSONObject msg) {
    JSONObject rtn = new JSONObject();
    rtn.put("success", success);
    rtn.put("msgTyp", msgTyp);
    rtn.put("msg", msg);
    return rtn;
  }

  public static JSONObject success() {
    return obj(true, MsgTyp.RESULT, "Success!");
  }

  public static JSONObject success(String msg) {
    return obj(true, MsgTyp.RESULT, msg);
  }

  public static JSONObject success(JSONObject o) {
    return obj(true, MsgTyp.RESULT, o);
  }

  public static JSONObject missingAuth() {
    return obj(false, MsgTyp.WARN, "Authorization is required");
  }

  public static JSONObject paraRequired(String name) {
    return obj(false, MsgTyp.ERROR, name + " is required");
  }

  public static JSONObject internalError() {
    return obj(false, MsgTyp.ERROR, "Server error");
  }

  public static JSONObject internalError(String msg) {
    return obj(false, MsgTyp.ERROR, msg);
  }

  public static JSONObject unauthorized() {
    return obj(false, MsgTyp.INFO, "Unauthorized");
  }

  public static JSONObject unauthorized(String msg) {
    return obj(false, MsgTyp.INFO, msg);
  }

  public static JSONObject forbidden() {
    return obj(false, MsgTyp.INFO, "Forbidden");
  }

  public static JSONObject forbidden(String msg) {
    return obj(false, MsgTyp.INFO, msg);
  }
}
