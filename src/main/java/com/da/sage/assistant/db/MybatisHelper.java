/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-08-28 18:16:51                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-08-31 19:40:01                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MybatisHelper {
  private static final Map<String, JsonObject> cacheConfigs = new ConcurrentHashMap<>();
  private static volatile Configuration mybatisConfig;
  private static volatile LanguageDriver xmlDriver = new XMLLanguageDriver();

  private static final Pattern NAMESPACE_PATTERN = Pattern.compile("<mapper[^>]*namespace=\"([^\"]+)\"");
  private static final Pattern CACHE_TAG_PATTERN = Pattern.compile("<cache[^>]*/?>");
  private static final Pattern ATTR_SIZE_PATTERN = Pattern.compile("size=\"(\\d+)\"");
  private static final Pattern ATTR_FLUSH_INTERVAL_PATTERN = Pattern.compile("flushInterval=\"(\\d+)\"");

  private static List<String> discoverMapperXml(String dir) {
    List<String> result = new ArrayList<>();
    try {
      URL dirUrl = Resources.getResourceURL(dir);
      if (dirUrl == null) {
        log.warn("Mapper directory not found on classpath: {}", dir);
        return result;
      }

      if ("file".equals(dirUrl.getProtocol())) {
        try (Stream<Path> paths = Files.list(Path.of(dirUrl.toURI()))) {
          paths.filter(p -> p.toString().endsWith(".xml"))
              .forEach(p -> result.add(dir + "/" + p.getFileName()));
        }
      } else if ("jar".equals(dirUrl.getProtocol())) {
        String jarPath = dirUrl.getPath().substring(5, dirUrl.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(jarPath)) {
          jar.stream()
              .filter(e -> e.getName().startsWith(dir + "/") && e.getName().endsWith(".xml") && !e.isDirectory())
              .forEach(e -> result.add(e.getName()));
        }
      } else {
        log.warn("Unsupported protocol for mapper directory: {}", dirUrl);
      }
    } catch (IOException | URISyntaxException e) {
      log.error("Failed to discover XML mapper files in: " + dir, e);
    }

    return result;
  }

  public static Configuration initMybatisConfig() {
    mybatisConfig = new Configuration();

    List<String> mappers = discoverMapperXml("mapper");
    for (String mapperPath : mappers) {
      log.debug("Loading mapper: {}", mapperPath);

      byte[] xmlBytes;
      try (InputStream is = Resources.getResourceAsStream(mapperPath)) {
        xmlBytes = is.readAllBytes();
      } catch (IOException e) {
        log.error("Failed to read mapper: {}\n{}", mapperPath, e);
        throw new RuntimeException("Failed to read mapper");
      }
      log.info("Loaded mapper: {}", mapperPath);

      String xmlContent = new String(xmlBytes, StandardCharsets.UTF_8);

      Matcher nsMatcher = NAMESPACE_PATTERN.matcher(xmlContent);
      if (!nsMatcher.find()) {
        throw new RuntimeException("No namespace found in mapper: " + mapperPath);
      }
      String namespace = nsMatcher.group(1);

      Matcher cacheMatcher = CACHE_TAG_PATTERN.matcher(xmlContent);
      if (cacheMatcher.find()) {
        String cacheTag = cacheMatcher.group();
        JsonObject config = new JsonObject();

        Matcher sizeMatcher = ATTR_SIZE_PATTERN.matcher(cacheTag);
        if (sizeMatcher.find()) {
          config.put("size", Integer.parseInt(sizeMatcher.group(1)));
        }

        Matcher flushMatcher = ATTR_FLUSH_INTERVAL_PATTERN.matcher(cacheTag);
        if (flushMatcher.find()) {
          config.put("flushInterval", Long.parseLong(flushMatcher.group(1)));
        }

        cacheConfigs.put(namespace, config);
        log.debug("Cache : {}", config.encode());
      } else {
        cacheConfigs.put(namespace, null);
      }

      try {
        XMLMapperBuilder xmlMapperbuilder = new XMLMapperBuilder(
            new ByteArrayInputStream(xmlBytes), mybatisConfig, mapperPath, mybatisConfig.getSqlFragments());
        xmlMapperbuilder.parse();
      } catch (Exception e) {
        log.error("Failed to load mapper: {}\n{}", mapperPath, e);
        throw new RuntimeException("Failed to load mapper");
      }
    }

    return mybatisConfig;
  }

  public static JsonObject getCacheConfig(String namespace) {
    return cacheConfigs.getOrDefault(namespace, new JsonObject());
  }

  public static MappedStatement getMappedStatementById(String statementId) {
    return mybatisConfig.getMappedStatement(statementId);
  }

  public static SqlSource buildDynamicSqlSource(String sqlTemplate) {
    // @formatter:off
    return xmlDriver.createSqlSource(mybatisConfig, "<script>" + sqlTemplate + "</script>", null);
    // @formatter:on
  }

}