/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-20 11:15:15                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 20:03:36                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;

import com.da.sage.assistant.AppConfig;
import com.da.sage.assistant.serviceStatic.FS;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.ClientSSLOptions;
import io.vertx.jdbcclient.JDBCConnectOptions;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.pgclient.PgBuilder;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;
import lombok.extern.log4j.Log4j2;

/**
 * Important: if connection cannot be established, edit these files: -
 * /etc/crypto-policies/back-ends/java.config -
 * JAVA_HOME/lib/security/java.security
 */
@Log4j2
public class DB {
  private static Map<String, Pool> pools = new HashMap<>();
  public static Map<String, DB_TYPE> dbTypes = new HashMap<>();
  private static ClientSSLOptions sslOptions = new ClientSSLOptions().setTrustAll(true);

  public static void initDB() {
    // to skip if already initialized
    if (pools.size() > 0) {
      return;
    }

    JsonArray dataSource = AppConfig.config.getJsonArray("dataSource");

    for (int i = 0; i < dataSource.size(); i++) {
      JsonObject obj = dataSource.getJsonObject(i);
      String typeStr = obj.getString("type");
      String name = obj.getString("name");
      DB_TYPE type = DB_TYPE.fromString(typeStr);
      dbTypes.put(name, type);

      JsonObject dbConfig = obj.getJsonObject("config");
      JsonObject poolConfig = obj.getJsonObject("options");
      PoolOptions poolOptions = new PoolOptions(poolConfig);
      Integer idleSize = poolConfig.getInteger("idleSize", 1);

      switch (type) {
        case PG:
        case POSTGRESQL:
          PgConnectOptions pgOptions = new PgConnectOptions(dbConfig).setSslOptions(sslOptions);
          Pool pgClient = PgBuilder.pool()
              .with(poolOptions)
              .connectingTo(pgOptions)
              .using(FS.vertx).build();
          pools.put(name, pgClient);
          pools.put(name, Pool.pool(FS.vertx, pgOptions, poolOptions));
          ensureMinConnections(name, idleSize);
          break;
        case MYSQL:
          MySQLConnectOptions mysqlOptions = new MySQLConnectOptions(dbConfig).setSslOptions(sslOptions);
          Pool mysqlClient = MySQLBuilder.pool()
              .with(poolOptions)
              .connectingTo(mysqlOptions)
              .using(FS.vertx).build();
          pools.put(name, mysqlClient);
          ensureMinConnections(name, idleSize);
          break;
        case MSSQL:
        case SQLSERVER:
          JDBCConnectOptions mssqlOptions = new JDBCConnectOptions(dbConfig);
          Pool msClient = JDBCPool.pool(FS.vertx, mssqlOptions, poolOptions);

          pools.put(name, msClient);
          ensureMinConnections(name, idleSize);
          break;
        default:
          log.warn("Unsupported DB type: {}", type);
          break;
      }
    }
  }

  public static void ensureMinConnections(String db, Integer idleSize) {
    FS.vertx.setPeriodic(1000, id -> {
      Integer currentSize = pools.get(db).size();
      if (currentSize >= idleSize) {
        return;
      }
      for (int i = 0; i < idleSize - currentSize; i++) {
        log.debug("Set up connecting {}:{}", db, currentSize + i + 1);
        pools.get(db).query("SELECT 1").execute();
      }
    });
  }

  public static JsonArray getDataSource() {
    JsonArray jsonArray = new JsonArray();
    for (String key : pools.keySet()) {
      if (key.equals("sa")) {
        continue;
      }
      jsonArray.add(JsonObject.of("name", key));
    }
    return jsonArray;
  }

  private static Future<JsonArray> selectBySqlRaw(String db, SqlWithTuple sqlWithTuple) {
    String sql = sqlWithTuple.getSql();
    Tuple tuple = sqlWithTuple.getTuple();

    return pools.get(db).preparedQuery(sql).execute(tuple).compose(rowSet -> {
      JsonArray list = new JsonArray();
      for (Row row : rowSet) {
        list.add(DBHelper.rowToJson(row));
      }
      log.debug("query result: {}", list);
      return Future.succeededFuture(list);
    }).onFailure(e -> {
      log.error("query failed", e);
    });

  }

  public static Future<JsonArray> selectBySql(String db, String sqlTemplate, JsonObject params) {
    return selectBySqlRaw(db, DBHelper.getQuerySql(sqlTemplate, params));
  }

  public static Future<JsonArray> selectBySqlId(String db, String sqlId, JsonObject params) {
    MappedStatement mappedStatement = MybatisHelper.getMappedStatementById(sqlId);
    return selectBySqlRaw(db, DBHelper.getQuerySql(mappedStatement, params));
  }

  public static Future<JsonArray> selectBySqlId(String sqlId, JsonObject params) {
    return selectBySqlId("sa", sqlId, params);
  }

  public static Future<JsonArray> selectBySqlIdWithCache(String db, String sqlId, JsonObject params) {
    return DBQueryCache.get(db + "|" + sqlId + "|" + params.encode());
  }

  public static Future<JsonArray> selectBySqlIdWithCache(String mappedSqlId, JsonObject params) {
    return selectBySqlIdWithCache("sa", mappedSqlId, params);
  }

  private static void sqlAddReturnId(String db, String sql) {
    // return last insert id
    switch (dbTypes.get(db)) {
      case PG:
      case POSTGRESQL:
        sql += " RETURNING id";
        break;
      case MSSQL:
      case SQLSERVER:
        int idx = sql.toUpperCase().indexOf("VALUES");
        if (idx < 0) {
          throw new IllegalArgumentException("Invalid INSERT SQL for MSSQL");
        }
        sql = sql.substring(0, idx) + " OUTPUT INSERTED.id " + sql.substring(idx);
        break;
      default:
    }
  }

  private static Future<Long> insertBySqlRaw(String db, SqlWithTuple sqlWithTuple) {
    String sql = sqlWithTuple.getSql();
    Tuple tuple = sqlWithTuple.getTuple();

    sqlAddReturnId(db, sql);

    return pools.get(db).preparedQuery(sql).execute(tuple).compose(rowSet -> {
      switch (dbTypes.get(db)) {
        case MYSQL:
          Long lastInsertId = rowSet.property(MySQLClient.LAST_INSERTED_ID);
          return Future.succeededFuture(lastInsertId);
        case PG:
        case POSTGRESQL:
        case MSSQL:
        case SQLSERVER:
          Long insertId = rowSet.iterator().next().getLong("id");
          return Future.succeededFuture(insertId);
        default:
          return Future.failedFuture("Insert failed: no rows affected");
      }
    }).onFailure(e -> {
      log.error("insert failed", e);
    });
  }

  public static Future<Long> insertBySql(String db, String sqlTemplate, JsonObject params) {
    return insertBySqlRaw(db, DBHelper.getInsertSql(sqlTemplate, params));
  }

  public static Future<Long> insertBySqlId(String db, String sqlId, JsonObject params) {
    MappedStatement mappedStatement = MybatisHelper.getMappedStatementById(sqlId);

    return insertBySqlRaw(db, DBHelper.getInsertSql(mappedStatement, params))
        .onSuccess(affected -> {
          String namespace = DBQueryCache.getNamespaceFromKey(db + "|" + sqlId + "|" + params.encode());
          DBQueryCache.invalidateByNameSpace(namespace);
        });
  }

  public static Future<Long> insertBySqlId(String sqlId, JsonObject params) {
    return insertBySqlId("sa", sqlId, params);
  }

  private static Future<JsonArray> insertBySqlBatchRaw(String db, SqlWithTuple sqlWithTuple) {
    String sql = sqlWithTuple.getSql();
    List<Tuple> tuples = sqlWithTuple.getTuples();

    sqlAddReturnId(db, sql);

    return pools.get(db).preparedQuery(sql).executeBatch(tuples).compose(rowSet -> {
      JsonArray insertIds = new JsonArray();

      switch (dbTypes.get(db)) {
        case MYSQL:
          Long firstInsertId = rowSet.property(MySQLClient.LAST_INSERTED_ID);
          if (firstInsertId != null) {
            for (int i = 0; i < tuples.size(); i++) {
              insertIds.add(firstInsertId + i);
            }
          }
          break;
        case PG:
        case POSTGRESQL:
        case MSSQL:
        case SQLSERVER:
          for (Row row : rowSet) {
            insertIds.add(row.getLong("id"));
          }
          break;
        default:
          return Future.failedFuture("Insert failed: unsupported database type");
      }

      return Future.succeededFuture(insertIds);
    }).onFailure(e -> {
      log.error("insert failed", e);
    });

  }

  public static Future<JsonArray> insertBySqlBatch(String db, String sqlTemplate, JsonArray params) {
    return insertBySqlBatchRaw(db, DBHelper.getInsertBatchSql(sqlTemplate, params));
  }

  public static Future<JsonArray> insertBySqlIdBatch(String db, String sqlId, JsonArray params) {
    MappedStatement mappedStatement = MybatisHelper.getMappedStatementById(sqlId);

    return insertBySqlBatchRaw(db, DBHelper.getInsertBatchSql(mappedStatement, params))
        .onSuccess(affected -> {
          String namespace = DBQueryCache.getNamespaceFromKey(db + "|" + sqlId + "|" + params.encode());
          DBQueryCache.invalidateByNameSpace(namespace);
        });
  }

  public static Future<JsonArray> insertBySqlIdBatch(String sqlId, JsonArray params) {
    return insertBySqlIdBatch("sa", sqlId, params);
  }

  private static Future<Integer> updatedBySqlRaw(String db, SqlWithTuple sqlWithTuple) {
    String sql = sqlWithTuple.getSql();
    Tuple tuple = sqlWithTuple.getTuple();

    return pools.get(db).preparedQuery(sql).execute(tuple).compose(rowSet -> {
      Integer affected = rowSet.rowCount();
      if (affected > 0) {
        return Future.succeededFuture(affected);
      } else {
        return Future.failedFuture("Update failed: no rows affected");
      }
    }).onFailure(e -> {
      log.error("update failed", e);
    });
  }

  public static Future<Integer> updateBySql(String db, String sqlTemplate, JsonObject params) {
    return updatedBySqlRaw(db, DBHelper.getUpdateSql(sqlTemplate, params));
  }

  public static Future<Integer> updateBySqlId(String db, String sqlId, JsonObject params) {
    MappedStatement mappedStatement = MybatisHelper.getMappedStatementById(sqlId);

    return updatedBySqlRaw(db, DBHelper.getUpdateSql(mappedStatement, params))
        .onSuccess(affected -> {
          String namespace = DBQueryCache.getNamespaceFromKey(db + "|" + sqlId + "|" + params.encode());
          DBQueryCache.invalidateByNameSpace(namespace);
        });
  }

  public static Future<Integer> updateBySqlId(String sqlId, JsonObject params) {
    return updateBySqlId("sa", sqlId, params);
  }

  private static Future<Integer> deleteBySqlRaw(String db, SqlWithTuple sqlWithTuple) {
    String sql = sqlWithTuple.getSql();
    Tuple tuple = sqlWithTuple.getTuple();

    return pools.get(db).preparedQuery(sql).execute(tuple).compose(rowSet -> {
      Integer affected = rowSet.rowCount();
      if (affected > 0) {
        return Future.succeededFuture(affected);
      } else {
        return Future.failedFuture("Delete failed: no rows affected");
      }
    }).onFailure(e -> {
      log.error("delete failed", e);
    });
  }

  public static Future<Integer> deleteBySql(String db, String sqlTemplate, JsonObject params) {
    return deleteBySqlRaw(db, DBHelper.getDeleteSql(sqlTemplate, params));
  }

  public static Future<Integer> deleteBySqlId(String db, String sqlId, JsonObject params) {
    MappedStatement mappedStatement = MybatisHelper.getMappedStatementById(sqlId);

    return deleteBySqlRaw(db, DBHelper.getDeleteSql(mappedStatement, params))
        .onSuccess(affected -> {
          String namespace = DBQueryCache.getNamespaceFromKey(db + "|" + sqlId + "|" + params.encode());
          DBQueryCache.invalidateByNameSpace(namespace);
        });
  }

  public static Future<Integer> deleteBySqlId(String sqlId, JsonObject params) {
    return deleteBySqlId("sa", sqlId, params);
  }

  private static Future<Integer> deleteBySqlBatchRaw(String db, SqlWithTuple sqlWithTuple) {
    String sql = sqlWithTuple.getSql();
    List<Tuple> tuples = sqlWithTuple.getTuples();

    return pools.get(db).preparedQuery(sql).executeBatch(tuples).compose(rowSet -> {
      Integer affected = rowSet.rowCount();
      if (affected > 0) {
        return Future.succeededFuture(affected);
      } else {
        return Future.failedFuture("Delete failed: no rows affected");
      }
    }).onFailure(e -> {
      log.error("{}\n{}", e.getMessage(), e.getCause());
    });
  }

  public static Future<Integer> deleteBySqlBatch(String db, String sqlTemplate, JsonArray params) {
    return deleteBySqlBatchRaw(db, DBHelper.getDeleteBatchSql(sqlTemplate, params));
  }

  public static Future<Integer> deleteBySqlIdBatch(String db, String sqlId, JsonArray params) {
    MappedStatement mappedStatement = MybatisHelper.getMappedStatementById(sqlId);

    return deleteBySqlBatchRaw(db, DBHelper.getDeleteBatchSql(mappedStatement, params))
        .onSuccess(affected -> {
          String namespace = DBQueryCache.getNamespaceFromKey(db + "|" + sqlId + "|" + params.encode());
          DBQueryCache.invalidateByNameSpace(namespace);
        });
  }

  public static Future<Integer> deleteBySqlIdBatch(String sqlId, JsonArray params) {
    return deleteBySqlIdBatch("sa", sqlId, params);
  }
}