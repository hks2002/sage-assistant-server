/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 19:32:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-22 11:40:03                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import org.junit.jupiter.api.Test;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Tuple;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestJson {

  @Test
  public void testJson1() {
    JsonObject obj = new JsonObject();
    obj.put("key1", "value1");
    obj.put("key2", "value2");
    obj.put("number", 12345);
    obj.put("boolean", true);
    // obj.put("date", new java.util.Date());
    obj.put("date2", "2000-12-31");

    String jsonString = obj.encodePrettily();
    log.info("JSON String: \n{}", jsonString);
    log.info("JSON String: \n{}", JsonObject.of("Success", true, "msg", obj).encodePrettily());

    JsonObject obj2 = new JsonObject();
    obj2.put("Success", true);
    obj2.put("msg", obj);

    jsonString = obj2.encodePrettily();
    log.info("JSON String: \n{}", jsonString);

  }

  @Test
  public void testJson2() {
    log.info("{}", Tuple.of("value1", "value2", 12345).deepToString());
  }

  @Test
  public void testJson3() {
    JsonObject result = new JsonObject();
    result.put("success", true);
    result.put("msg", "Success");
    result.put("data", JsonArray.of());
    log.info(result.encode());
  }

}