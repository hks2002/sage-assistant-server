/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-09 23:29:08                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-11-06 01:07:19                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataUtils {
  /**
   * Safe read BigDecimal value from JsonObject field, null -> BigDecimal.ZERO
   * 
   * @param jo
   *                JsonObject instance
   * @param field
   *                field name
   * @return BigDecimal value
   */
  private static BigDecimal getBigDecimalValue(JsonObject jo, String field) {
    Object rawVal = jo.getValue(field);
    if (rawVal == null) {
      return BigDecimal.ZERO;
    }
    return new BigDecimal(rawVal.toString());
  }

  /**
   * Group sum operation on JsonArray of JsonObject
   * 
   * @param data
   *                        JsonArray of JsonObject
   * @param groupByFields
   *                        group dimension fields
   * @param sumFields
   *                        sum fields to accumulate
   * @return aggregated JsonArray of JsonObject
   */
  public static JsonArray groupSum(JsonArray data, List<String> groupByFields, List<String> sumFields,
      JsonObject additional) {
    // key: string combination of groupByFields, value: aggregated JsonObject with
    // sumFields
    Map<String, JsonObject> aggMap = new LinkedHashMap<>();

    for (Object obj : data) {
      if (!(obj instanceof JsonObject)) {
        continue;
      }
      JsonObject row = (JsonObject) obj;

      // ========== generate group key ==========
      StringBuilder keySb = new StringBuilder();
      for (String groupField : groupByFields) {
        String val = row.getString(groupField);
        keySb.append("|").append(val == null ? "" : val);
      }
      String groupKey = keySb.toString();

      // ========== check if group exists already ==========
      JsonObject aggRow = aggMap.get(groupKey);
      if (aggRow == null) {
        // ========== create new group record ==========
        aggRow = new JsonObject();
        // ========== fill group dimension fields ==========
        for (String gField : groupByFields) {
          aggRow.put(gField, row.getString(gField));
        }
        // ========== initialize sum fields to BigDecimal.ZERO ==========
        for (String sField : sumFields) {
          aggRow.put(sField, BigDecimal.ZERO);
        }
        aggMap.put(groupKey, aggRow);
      }

      // ========== accumulate sum fields ==========
      for (String sumField : sumFields) {
        BigDecimal currentVal = getBigDecimalValue(row, sumField);

        // 取出聚合对象里已累加的值
        BigDecimal existed = getBigDecimalValue(aggRow, sumField);
        BigDecimal newSum = existed.add(currentVal);
        aggRow.put(sumField, newSum);
      }

      // ========== merge additional fields ==========
      if (additional != null) {
        aggRow.mergeIn(additional);
      }
    }

    // ========== return aggregated map ==========
    return new JsonArray(new ArrayList<>(aggMap.values()));
  }

  public static JsonArray topN(JsonArray data, String sortField, int topN, boolean asc) {
    List<JsonObject> rowList = new ArrayList<>();

    // ========== filter out invalid JsonObjects ==========
    for (Object obj : data) {
      if (!(obj instanceof JsonObject)) {
        continue;
      }
      JsonObject row = (JsonObject) obj;
      rowList.add(row);
    }

    // ========== define custom comparator ==========
    Comparator<JsonObject> comparator = (o1, o2) -> {
      BigDecimal v1 = getBigDecimalValue(o1, sortField);
      BigDecimal v2 = getBigDecimalValue(o2, sortField);
      return v1.compareTo(v2);
    };

    if (!asc) {
      comparator = comparator.reversed();
    }

    // ========== sort JsonObjects ==========
    rowList.sort(comparator);

    // ========== extract topN ==========
    int endIndex = Math.min(topN, rowList.size());
    List<JsonObject> resultList = rowList.subList(0, endIndex);

    return new JsonArray(resultList);
  }

}