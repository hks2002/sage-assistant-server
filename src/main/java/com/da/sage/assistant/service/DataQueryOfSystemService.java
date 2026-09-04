/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-03-09 17:28:45                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 21:58:30                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import com.da.sage.assistant.db.DB;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQueryOfSystemService {
  private static final String namespace = "com.da.sage.assistant.mapper";

  public Future<String> batchQuery(JsonObject params, String sqlId, String dateField) {
    String currencyTo = params.getString("CurrencyTo", "USD");
    return DB.selectBySqlIdWithCache("sage", sqlId, params)
        .compose(data -> {
          return CurrencyService.batchValue(data, currencyTo, dateField);
        });
  }

  public Future<JsonArray> query(JsonObject params, String sqlId) {
    return DB.selectBySqlIdWithCache("sage", namespace + "." + sqlId, params)
        .compose((data) -> {
          return Future.succeededFuture(data);
        });
  }

  public Future<JsonArray> currencyQuery(JsonObject params, String sqlId) {
    return DB.selectBySqlIdWithCache("sage", namespace + "." + sqlId, params)
        .compose((v) -> {
          JsonArray data = v;
          if (params.containsKey("CurrencyTo")
              && params.containsKey("CurrencyDate")) {
            String currencyTo = params.getString("CurrencyTo");
            String currencyDate = params.getString("CurrencyDate");

            return CurrencyService.convertCurrency(data, currencyTo, currencyDate);
          } else if (params.containsKey("UsingUSD")
              && params.containsKey("USDDate")
              && params.containsKey("USDTarget")) {
            String usdDate = params.getString("USDDate");
            String usdTarget = params.getString("USDTarget");

            return CurrencyService.addUSD(data, usdDate, usdTarget);

          } else {
            return Future.succeededFuture(data);
          }
        });
  }
}