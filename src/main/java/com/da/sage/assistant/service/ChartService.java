/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-08-31 17:52:26                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-08-31 17:53:01                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class ChartService {
  public Future<JsonObject> query(JsonObject info) {
    return MapperService.query("ChartMapper.chart", info);
  }

  public Future<Long> insert(JsonObject info) {
    return MapperService.insert("ChartMapper.chart", info);
  }

  public Future<Integer> update(JsonObject info) {
    return MapperService.update("ChartMapper.chart", info);
  }

  public Future<Integer> delete(JsonObject info) {
    return MapperService.delete("ChartMapper.chart", info);
  }
}
