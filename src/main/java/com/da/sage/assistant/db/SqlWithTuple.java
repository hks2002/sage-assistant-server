/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-07-23 18:34:41                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-23 19:02:44                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.ParameterMapping;

import io.vertx.sqlclient.Tuple;

public class SqlWithTuple {
  private String sql;
  private List<ParameterMapping> paramMappings;
  private List<Tuple> tuples;

  public SqlWithTuple(String sql) {
    this.sql = sql;
    this.tuples = new ArrayList<>();
  }

  public SqlWithTuple(String sql, Tuple tuple) {
    this.sql = sql;
    this.tuples = new ArrayList<>();
    if (tuple != null) {
      this.tuples.add(tuple);
    }
  }

  public SqlWithTuple(String sql, List<Tuple> tuples) {
    this.sql = sql;
    this.tuples = tuples != null ? tuples : new ArrayList<>();
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public List<Tuple> getTuples() {
    return tuples;
  }

  public void setTuples(List<Tuple> tuples) {
    this.tuples = tuples != null ? tuples : new ArrayList<>();
  }

  public void addTuple(Tuple tuple) {
    if (tuple != null) {
      this.tuples.add(tuple);
    }
  }

  public Tuple getTuple() {
    return tuples.isEmpty() ? null : tuples.get(0);
  }

  public List<ParameterMapping> getParamMappings() {
    return paramMappings;
  }

  public void setParamMappings(List<ParameterMapping> paramMappings) {
    this.paramMappings = paramMappings != null ? paramMappings : new ArrayList<>();
  }
}