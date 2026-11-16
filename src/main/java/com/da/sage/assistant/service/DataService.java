/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-08-31 18:08:21                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-14 11:20:48                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class DataService {

  public Future<JsonObject> query(JsonObject info) {
    return MapperService.query("DataMapper.data", info);
  }

  public Future<Long> queryCnt(JsonObject info) {
    return MapperService.queryCnt("DataMapper.data", info);
  }

  public Future<Long> insert(JsonObject info) {
    return MapperService.insert("DataMapper.data", info);
  }

  public Future<Long> update(JsonObject info) {
    return MapperService.update("DataMapper.data", info);
  }

  public Future<Long> delete(JsonObject info) {
    return MapperService.delete("DataMapper.data", info);
  }
}
