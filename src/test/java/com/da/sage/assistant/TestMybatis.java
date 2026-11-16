/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 19:32:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-03-12 09:32:16                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/

package com.da.sage.assistant;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestMybatis {

  @Test
  public void testMybatis() {
    Configuration configuration = new Configuration();
    String sql = """
        SELECT TOP
        <if test=\"Site == 'ZHU'\">1</if>
        <if test=\"Site == 'YSH'\">2</if>
        <choose>
          <when test=\"Site\">1</when>
          <otherwise>0</otherwise>
        </choose>
        SOHNUM_0 FROM EXPLOIT.SORDER WHERE SORDER.SALFCY_0 = '#{Site}'
        """;
    // make sure the sqlTemplate is wrapped with <script> tags,
    String sqlTemplate = "<script>\n" + sql + "\n</script>";

    // XMLLanguageDriver handling MyBatis XML tags like <if>, <choose>, etc.
    LanguageDriver languageDriver = new XMLLanguageDriver();
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlTemplate, null);

    Map<String, Object> params = new HashMap<>();
    params.put("Site", "ZHU");
    BoundSql b = sqlSource.getBoundSql(params);
    log.info("sql:{}", b.getSql());
    log.info("params:{}", b.getParameterMappings());
  }

}
