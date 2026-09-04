/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-08-24 19:15:00                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-08-24 19:28:17                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.da.sage.assistant.db.DBHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestMybatisMapperLoader {

  private static Method discoverMapperXmlMethod;

  @BeforeAll
  public static void setup() throws Exception {
    discoverMapperXmlMethod = DBHelper.class.getDeclaredMethod("discoverMapperXml", String.class);
    discoverMapperXmlMethod.setAccessible(true);
  }

  @SuppressWarnings("unchecked")
  private static List<String> invokeDiscoverMapperXml(String dir) throws Exception {
    return (List<String>) discoverMapperXmlMethod.invoke(null, dir);
  }

  @Test
  public void testDiscoverMapperXml() throws Exception {
    List<String> mappers = invokeDiscoverMapperXml("mapper");

    log.info("Discovered mappers: {}", mappers);

    assertFalse(mappers.isEmpty(), "Should discover at least one mapper XML");
    assertTrue(mappers.stream().allMatch(m -> m.startsWith("mapper/") && m.endsWith(".xml")),
        "All results should start with 'mapper/' and end with '.xml'");
  }

  @Test
  public void testDiscoverMapperXmlContainsTestFiles() throws Exception {
    List<String> mappers = invokeDiscoverMapperXml("mapper");

    assertTrue(mappers.contains("mapper/TestMapper.xml"),
        "Should contain TestMapper.xml");
    assertTrue(mappers.contains("mapper/OrderMapper.xml"),
        "Should contain OrderMapper.xml");
  }

  @Test
  public void testDiscoverMapperXmlNonExistentDir() throws Exception {
    List<String> mappers = invokeDiscoverMapperXml("non_existent_dir");

    assertTrue(mappers.isEmpty(), "Should return empty list for non-existent directory");
  }

  @Test
  public void testLoadMapperAndGetMappedStatement() throws Exception {
    Method getMybatisConfigMethod = DBHelper.class.getDeclaredMethod("getMybatisConfig");
    getMybatisConfigMethod.setAccessible(true);
    Configuration config = (Configuration) getMybatisConfigMethod.invoke(null);

    assertNotNull(config, "Configuration should not be null");

    boolean hasTestStatement = config.getMappedStatementNames().contains("test.selectUser");
    boolean hasOrderStatement = config.getMappedStatementNames().contains("order.selectOrder");

    log.info("Mapped statements: {}", config.getMappedStatementNames());

    if (hasTestStatement) {
      var ms = config.getMappedStatement("test.selectUser");
      assertNotNull(ms, "MappedStatement 'test.selectUser' should not be null");
      log.info("test.selectUser SQL: {}", ms.getBoundSql(null).getSql());
    }

    if (hasOrderStatement) {
      var ms = config.getMappedStatement("order.selectOrder");
      assertNotNull(ms, "MappedStatement 'order.selectOrder' should not be null");
      log.info("order.selectOrder SQL: {}", ms.getBoundSql(null).getSql());
    }

    assertTrue(hasTestStatement || hasOrderStatement,
        "Should have at least one mapped statement from mapper XML files");
  }
}