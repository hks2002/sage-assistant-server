/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 15:17:16                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 17:43:06                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import com.da.sage.assistant.db.DB;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MapperService {
  private static final String namespace = "com.da.sage.assistant.mapper";

  /**
   * 
   * @param target Mapper name with id name prefix, eg: UserMapper.user
   */
  public static Future<Long> insert(String target, JsonObject params) {
    return DB.insertBySqlId(namespace + "." + target + "_Insert", params);
  }

  /**
   * 
   * @param target Mapper name with id name prefix, eg: UserMapper.user
   */
  public static Future<Integer> update(String target, JsonObject params) {
    return DB.updateBySqlId(namespace + "." + target + "_Update", params);
  }

  /**
   * 
   * @param target Mapper name with id name prefix, eg: UserMapper.user
   */
  public static Future<Integer> delete(String target, JsonObject params) {
    return DB.deleteBySqlId(namespace + "." + target + "_Delete", params);
  }

  /**
   * @param db     database to query
   * @param target Mapper name with id name prefix, eg: UserMapper.user
   */
  public static Future<JsonObject> query(String target, JsonObject params) {
    Future<JsonArray> f1 = DB.selectBySqlIdWithCache(namespace + "." + target + "_Query", params);
    Future<JsonArray> f2 = DB.selectBySqlIdWithCache(namespace + "." + target + "_QueryCnt", params);

    return Future.all(f1, f2).compose(val -> {
      JsonArray data = val.resultAt(0);
      JsonArray cnt = val.resultAt(1);
      Integer total = cnt.getJsonObject(0).getInteger("total");
      JsonObject dataWithTotal = JsonObject.of("data", data, "total", total);
      return Future.succeededFuture(dataWithTotal);
    });
  }
}