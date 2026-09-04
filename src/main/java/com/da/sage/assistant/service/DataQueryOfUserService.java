/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-03-09 17:28:45                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-30 17:43:54                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.db.DBQueryCache;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQueryOfUserService {

  public Future<JsonArray> getDataSource() {
    return Future.succeededFuture(DB.getDataSource());
  }

  public Future<JsonObject> test(JsonObject obj) {
    String db = obj.getString("db");
    String sql = obj.getString("sql");
    JsonObject params = obj.getJsonObject("params");

    return DB.selectBySql(db, sql, params).compose(ar -> {
      JsonObject res = new JsonObject();
      res.put("data", ar);
      res.put("total", ar.size());
      return Future.succeededFuture(res);
    });
  }

  public Future<JsonObject> query(String db, String dataCode, JsonObject params) {
    Future<JsonObject> f = MapperService.query("DataService.data",
        JsonObject.of("data_code", dataCode, "details", true));

    return f.compose(ar -> {
      JsonArray data = ar.getJsonArray("data");
      Integer total = ar.getInteger("total");

      if (total.equals(1)) {

        String sqlTemplate = data.getJsonObject(0).getString("data_definition");
        Integer cacheTime = data.getJsonObject(0).getInteger("cache_time");

        String key = db + "|" + dataCode + "|" + params.encode();
        var f1 = DBQueryCache.getIfPresent(key);
        if (f1 == null) {
          return DB.selectBySql(db, sqlTemplate, params).compose(ar2 -> {
            JsonObject res = new JsonObject();
            res.put("data", ar2);
            res.put("total", ar2.size());
            // Cache it
            DBQueryCache.put(db + "|" + dataCode + "|" + params.encode(), ar2, cacheTime);
            return Future.succeededFuture(res);
          });
        } else {
          return f1.compose(ar2 -> {
            JsonObject res = new JsonObject();
            res.put("data", ar2);
            res.put("total", ar2.size());
            return Future.succeededFuture(res);
          });
        }

      } else if (total > 1) {
        log.error("data_code [{}] duplicated", dataCode);
        return Future.failedFuture(dataCode + " duplicated");

      } else {
        log.error("data_code [{}] not found", dataCode);
        return Future.failedFuture(dataCode + " not found");

      }
    });
  }

  public Future<String> getCols(String db, String dataCode) {
    Future<JsonObject> f = MapperService.query("DataService.data",
        JsonObject.of("data_code", dataCode, "details", true));

    return f.compose(ar -> {
      JsonArray data = ar.getJsonArray("data");
      Integer total = ar.getInteger("total");

      if (total.equals(1)) {
        String data_columns = data.getJsonObject(0).getString("data_columns");
        return Future.succeededFuture(data_columns);

      } else if (total > 1) {
        log.error("data_code [{}] duplicated", dataCode);
        return Future.failedFuture(dataCode + " duplicated");

      } else {
        log.error("data_code [{}] not found", dataCode);
        return Future.failedFuture(dataCode + " not found");

      }
    });
  }

}