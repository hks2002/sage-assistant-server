/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-07-04 21:18:12                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-01 11:53:29                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBHelper {

  public static DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  /**
   * for mybatis parameter using
   * 
   * @param obj
   * @return
   */
  private static Object toMap(Object value) {
    if (value instanceof JsonObject) {
      return toMap((JsonObject) value);
    }
    if (value instanceof JsonArray) {
      List<Object> list = new ArrayList<>();
      for (Object v : (JsonArray) value) {
        list.add(toMap(v));
      }
      return list;
    }
    return value; // String / Number / Boolean / null
  }

  /**
   * for mybatis parameter using
   * 
   * @param obj
   * @return
   */
  private static Map<String, Object> toMap(JsonObject obj) {
    Map<String, Object> params = new HashMap<>();
    if (obj == null) {
      return params;
    }

    for (String key : obj.fieldNames()) {
      Object value = obj.getValue(key);
      params.put(key, toMap(value));
    }
    return params;
  }

  private static Tuple toTuple(List<ParameterMapping> parameterMappings, JsonObject params) {
    Tuple tuple = Tuple.tuple();

    for (ParameterMapping pm : parameterMappings) {
      String prop = pm.getProperty();
      if (!params.containsKey(prop)) {
        log.error("Missing Param:{}", prop);
        throw new IllegalArgumentException("Missing Param: " + prop);
      }
      tuple.addValue(params.getValue(prop));
    }
    return tuple;
  }

  public static JsonObject rowToJson(Row row) {
    JsonObject json = new JsonObject();
    for (int i = 0; i < row.size(); i++) {
      String name = row.getColumnName(i);
      Object value = row.getValue(i);
      if (name.endsWith("Date") && value instanceof LocalDateTime) {
        json.put(name, ((LocalDateTime) value).format(DateTimeFormatter.ISO_LOCAL_DATE));
      } else {
        json.put(name, value);
      }
    }
    return json;
  }

  private static SqlWithTuple getSqlWithTupleRaw(BoundSql bondSql, JsonObject params) {
    String sql = bondSql.getSql(); // contains '?' for parameter placehodler
    List<ParameterMapping> paramMappings = bondSql.getParameterMappings();

    log.debug("Sql:\n{}\nparamMappings: {}", sql, paramMappings);

    Tuple tuple = toTuple(paramMappings, params);
    SqlWithTuple sqlWithTuple = new SqlWithTuple(sql, tuple);
    sqlWithTuple.setParamMappings(paramMappings);
    return sqlWithTuple;
  }

  private static SqlWithTuple getSqlWithTuple(String sqlTemplate, JsonObject params) {
    Map<String, Object> paramsMap = toMap(params);

    SqlSource sqlSource = MybatisHelper.buildDynamicSqlSource(sqlTemplate);
    BoundSql bondSql = sqlSource.getBoundSql(paramsMap);

    return getSqlWithTupleRaw(bondSql, params);
  }

  private static SqlWithTuple getSqlWithTuple(MappedStatement mappedStatement, JsonObject params) {
    Map<String, Object> paramsMap = toMap(params);

    BoundSql bondSql = mappedStatement.getBoundSql(paramsMap);
    return getSqlWithTupleRaw(bondSql, params);
  }

  private static void addDefaultQueryParams(JsonObject params) {
    if (params.containsKey("offset")) {
      params.put("offset", Integer.valueOf(params.getString("offset", "0")));
    } else {
      params.put("offset", 0);
    }
    if (params.containsKey("limit")) {
      params.put("limit", Integer.valueOf(params.getString("limit", "10")));
    } else {
      params.put("limit", 10);
    }
  }

  public static SqlWithTuple getQuerySql(String sqlTemplate, JsonObject params) {
    addDefaultQueryParams(params);
    return getSqlWithTuple(sqlTemplate, params);
  }

  public static SqlWithTuple getQuerySql(MappedStatement mappedStatement, JsonObject params) {
    addDefaultQueryParams(params);
    return getSqlWithTuple(mappedStatement, params);
  }

  private static void addDefaultInsertParams(JsonObject params) {
    String now = LocalDateTime.now().format(fmt);
    if (!params.containsKey("create_at") || params.getString("create_at").isEmpty()) {
      params.put("create_at", now);
    }
    if (!params.containsKey("create_by") || params.getString("create_by").isEmpty()) {
      params.put("create_by", 0);
    }
  }

  public static SqlWithTuple getInsertSql(String sqlTemplate, JsonObject params) {
    addDefaultInsertParams(params);
    return getSqlWithTuple(sqlTemplate, params);
  }

  public static SqlWithTuple getInsertSql(MappedStatement mappedStatement, JsonObject params) {
    addDefaultInsertParams(params);
    return getSqlWithTuple(mappedStatement, params);
  }

  private static SqlWithTuple buildInsertBatchTuples(SqlWithTuple sqlWithTuple, JsonArray params) {
    List<ParameterMapping> paramMappings = sqlWithTuple.getParamMappings();

    List<Tuple> tuples = new ArrayList<>();
    for (int i = 0; i < params.size(); i++) {
      JsonObject item = params.getJsonObject(i);
      addDefaultInsertParams(item);
      tuples.add(toTuple(paramMappings, item));
    }
    sqlWithTuple.setTuples(tuples);

    return sqlWithTuple;
  }

  public static SqlWithTuple getInsertBatchSql(String sqlTemplate, JsonArray params) {
    var first = params.getJsonObject(0);
    addDefaultInsertParams(first);
    SqlWithTuple sqlWithTuple = getSqlWithTuple(sqlTemplate, first);
    return buildInsertBatchTuples(sqlWithTuple, params);
  }

  public static SqlWithTuple getInsertBatchSql(MappedStatement mappedStatement, JsonArray params) {
    var first = params.getJsonObject(0);
    addDefaultInsertParams(first);
    SqlWithTuple sqlWithTuple = getSqlWithTuple(mappedStatement, first);
    return buildInsertBatchTuples(sqlWithTuple, params);
  }

  private static void addDefaultUpdateParams(JsonObject params) {
    String now = LocalDateTime.now().format(fmt);
    if (!params.containsKey("update_at") || params.getString("update_at").isEmpty()) {
      params.put("update_at", now);
    }
    if (!params.containsKey("update_by") || params.getString("update_by").isEmpty()) {
      params.put("update_by", 0);
    }
  }

  public static SqlWithTuple getUpdateSql(String sqlTemplate, JsonObject params) {
    addDefaultUpdateParams(params);

    return getSqlWithTuple(sqlTemplate, params);
  }

  public static SqlWithTuple getUpdateSql(MappedStatement mappedStatement, JsonObject params) {
    addDefaultUpdateParams(params);

    return getSqlWithTuple(mappedStatement, params);
  }

  public static SqlWithTuple getDeleteSql(String sqlTemplate, JsonObject params) {
    return getSqlWithTuple(sqlTemplate, params);
  }

  public static SqlWithTuple getDeleteSql(MappedStatement mappedStatement, JsonObject params) {
    return getSqlWithTuple(mappedStatement, params);
  }

  private static SqlWithTuple buildDeleteBatchTuples(SqlWithTuple sqlWithTuple, JsonArray params) {
    List<ParameterMapping> paramMappings = sqlWithTuple.getParamMappings();

    List<Tuple> tuples = new ArrayList<>();
    for (int i = 0; i < params.size(); i++) {
      tuples.add(toTuple(paramMappings, params.getJsonObject(i)));
    }
    sqlWithTuple.setTuples(tuples);
    return sqlWithTuple;
  }

  public static SqlWithTuple getDeleteBatchSql(String sqlTemplate, JsonArray params) {
    SqlWithTuple sqlWithTuple = getSqlWithTuple(sqlTemplate, params.getJsonObject(0));
    return buildDeleteBatchTuples(sqlWithTuple, params);
  }

  public static SqlWithTuple getDeleteBatchSql(MappedStatement mappedStatement, JsonArray params) {
    SqlWithTuple sqlWithTuple = getSqlWithTuple(mappedStatement, params.getJsonObject(0));
    return buildDeleteBatchTuples(sqlWithTuple, params);
  }
}