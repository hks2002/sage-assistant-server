/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-07-04 20:59:13                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-04 21:02:37                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

public enum DB_TYPE {
  MYSQL,
  PG, POSTGRESQL,
  MSSQL, SQLSERVER;

  public static DB_TYPE fromString(String dbString) {
    if (dbString == null || dbString.isBlank()) {
      return null;
    }
    String input = dbString.trim().toLowerCase();
    return switch (input) {
      case "mysql" -> MYSQL;
      case "pg", "postgresql" -> POSTGRESQL;
      case "mssql", "sqlserver" -> SQLSERVER;
      default -> null;
    };
  }
}