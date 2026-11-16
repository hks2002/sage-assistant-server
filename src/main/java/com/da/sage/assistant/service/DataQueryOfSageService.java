/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-03-09 17:28:45                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-11 17:08:11                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import java.util.List;
import java.util.TreeMap;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.serviceStatic.FS;
import com.da.sage.assistant.utils.DataUtils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataQueryOfSageService {
  private static final String namespace = "com.da.sage.assistant.mapper.sage";

  public Future<String> batchQuery(JsonObject params, String sqlId, String dateField) {
    String currencyTo = params.getString("CurrencyTo", "USD");
    JsonObject sortedParams = new JsonObject(new TreeMap<>(params.getMap()));

    return DB.selectBySqlIdWithCache("sage", namespace + "." + sqlId, sortedParams)
        .compose(data -> {
          return CurrencyService.batchValue(data, currencyTo, dateField);
        });
  }

  public Future<JsonArray> query(JsonObject params, String sqlId) {
    JsonObject sortedParams = new JsonObject(new TreeMap<>(params.getMap()));
    return DB.selectBySqlIdWithCache("sage", namespace + "." + sqlId, sortedParams)
        .map(JsonArray::copy)
        .compose((data) -> {
          if (params.containsKey("CurrencyTo")) {
            String currencyTo = params.getString("CurrencyTo");
            String currencyDate = params.getString("CurrencyDate");
            if (currencyDate == null || currencyDate.isEmpty()) {
              return Future.failedFuture("CurrencyDate is empty");
            }

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

  public Future<JsonArray> querySum(JsonObject params, String sqlId) {
    // ignore the CurrencyTo,CurrencyDate,SumGroupByFields,SumTargetFields
    JsonObject newParams = new JsonObject(params.encode());
    newParams.remove("CurrencyTo");
    newParams.remove("CurrencyDate");
    newParams.remove("SumGroupByFields");
    newParams.remove("SumTargetFields");

    return DB.selectBySqlIdWithCache("sage", namespace + "." + sqlId, newParams)
        .map(JsonArray::copy) // Important！Deep copy the array to avoid modifying the original data，
                              // sum operation will modify the original data
        .compose((data) -> {
          if (params.containsKey("CurrencyTo")) {
            String currencyTo = params.getString("CurrencyTo");
            String currencyDate = params.getString("CurrencyDate");
            if (currencyDate == null || currencyDate.isEmpty()) {
              return Future.failedFuture("CurrencyDate is empty");
            }

            return CurrencyService.convertCurrency(data, currencyTo, currencyDate);
          } else {
            return Future.succeededFuture(data);
          }
        })
        .compose((data) -> {
          if (params.containsKey("SumGroupByFields") && params.containsKey("SumTargetFields")) {
            List<String> sumGroupBy = List.of(params.getString("SumGroupByFields").split(","));
            List<String> sumFields = List.of(params.getString("SumTargetFields").split(","));

            if (params.containsKey("CurrencyTo")) {
              String currencyTo = params.getString("CurrencyTo");
              return FS.vertx.executeBlocking(
                  () -> DataUtils.groupSum(data, sumGroupBy, sumFields, JsonObject.of("Currency", currencyTo)));
            } else {
              return FS.vertx.executeBlocking(() -> DataUtils.groupSum(data, sumGroupBy, sumFields, JsonObject.of()));
            }

          } else {
            return Future.succeededFuture(data);
          }
        });
  }

  public Future<JsonArray> queryTopN(JsonObject params, String sqlId) {
    // ignore the SortByField,TopN, Ascending
    JsonObject newParams = new JsonObject(params.encode());
    newParams.remove("SortByField");
    newParams.remove("TopN");
    newParams.remove("Ascending");

    return querySum(newParams, sqlId)
        .compose((data) -> {
          if (params.containsKey("SortByField")) {
            String sortByField = params.getString("SortByField");
            int topN = Integer.parseInt(params.getString("TopN", "10"));
            boolean ascending = "Y".equals(params.getString("Ascending", "N"));

            return FS.vertx.executeBlocking(() -> DataUtils.topN(data, sortByField, topN, ascending));
          } else {
            return Future.succeededFuture(data);
          }
        });
  }
}