/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 19:32:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-23 11:25:02                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.da.sage.assistant.utils.DataUtils;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestData {

  @Test
  public void testGroupSum() {
    JsonArray arr = new JsonArray();
    arr.add(JsonObject.of("Site", "ZHU", "salesAmount", 100.0, "amount", 100.0));
    arr.add(JsonObject.of("Site", "ZHU", "salesAmount", 200.0, "amount", 200.0));
    arr.add(JsonObject.of("Site", "YSH", "salesAmount", 300.0));
    arr.add(JsonObject.of("Site", "YSH", "salesAmount", 400.0));
    arr.add(JsonObject.of("Site", "YSH", "salesAmount", 200.0));

    JsonArray rtn = DataUtils.groupSum(arr, List.of("Site"), List.of("salesAmount", "amount"), new JsonObject());

    String jsonString = rtn.encodePrettily();
    log.info("StreamUtils.groupSum: \n{}", jsonString);

  }

}