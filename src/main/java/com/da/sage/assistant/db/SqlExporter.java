/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-09-13 17:45:42                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-15 17:16:09                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

public class SqlExporter {
  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.err.println("Usage: SqlExporter <mapper xml directory> <SQL output directory>");
      System.exit(1);
    }
    String inputDir = args[0];
    String outputDir = args[1];

    Configuration configuration = new Configuration();

    List<File> xmlFiles = new ArrayList<>();
    try (Stream<Path> paths = Files.walk(Paths.get(inputDir))) {
      paths.filter(Files::isRegularFile)
          .filter(p -> p.toString().endsWith(".xml"))
          .forEach(p -> xmlFiles.add(p.toFile()));
    }

    if (xmlFiles.isEmpty()) {
      System.err.println("No XML files found: " + inputDir);
      System.exit(1);
    }

    for (File xmlFile : xmlFiles) {
      try (FileInputStream fis = new FileInputStream(xmlFile)) {
        XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(
            fis, configuration, xmlFile.getAbsolutePath(),
            configuration.getSqlFragments());
        mapperBuilder.parse();
      } catch (Exception e) {
        System.err.println("Parse failed: " + xmlFile.getName() + " -> " + e.getMessage());
      }
    }

    Collection<String> statementIds = configuration.getMappedStatementNames();

    Files.createDirectories(Paths.get(outputDir));

    for (String stmtId : statementIds) {
      if (!stmtId.contains(".")) {
        continue;
      }
      MappedStatement ms = configuration.getMappedStatement(stmtId, true);
      SqlSource sqlSource = ms.getSqlSource();
      String sql;

      if (sqlSource instanceof DynamicSqlSource dynamicSqlSource) {
        Field rootSqlNodeField = DynamicSqlSource.class.getDeclaredField("rootSqlNode");
        rootSqlNodeField.setAccessible(true);
        SqlNode rootSqlNode = (SqlNode) rootSqlNodeField.get(dynamicSqlSource);
        StringBuilder sb = new StringBuilder();
        extractSql(rootSqlNode, sb);
        sql = sb.toString().trim();
      } else {
        // RawSqlSource，静态SQL
        sql = sqlSource.getBoundSql(new HashMap<>()).getSql();
      }

      System.out.println(stmtId);
      // System.out.println(sql);

      // 写入文件，把statementId中的非法字符替换
      String fileName = stmtId.replaceAll("[\\\\/:*?\"<>|]", "_") + ".sql";
      Files.write(Paths.get(outputDir, fileName), sql.getBytes());
    }
    System.out.println("Export completed, #{} and XML dynamic tags (<if>/<choose>/<foreach>...) preserved");
  }

  @SuppressWarnings("unchecked")
  private static void extractSql(SqlNode node, StringBuilder sb) throws Exception {
    if (node == null) {
      return;
    }
    String className = node.getClass().getName();

    if (className.endsWith("MixedSqlNode")) {
      List<SqlNode> contents = (List<SqlNode>) findField(node.getClass(), "contents").get(node);
      for (SqlNode child : contents) {
        extractSql(child, sb);
      }
    } else if (className.endsWith("TextSqlNode")) {
      sb.append((String) findField(node.getClass(), "text").get(node));
    } else if (className.endsWith("StaticTextSqlNode")) {
      sb.append((String) findField(node.getClass(), "text").get(node));
    } else if (className.endsWith("IfSqlNode")) {
      // <if test="...">...</if>
      String test = (String) findField(node.getClass(), "test").get(node);
      SqlNode contents = (SqlNode) findField(node.getClass(), "contents").get(node);
      sb.append("<if ").append(attr("test", test)).append(">");
      extractSql(contents, sb);
      sb.append("</if>");
    } else if (className.endsWith("ChooseSqlNode")) {
      // <choose> discards inter-tag whitespace, so reconstruct newlines + indent
      String chooseIndent = trailingIndent(sb);
      sb.append("<choose>");
      List<SqlNode> ifSqlNodes = (List<SqlNode>) findField(node.getClass(), "ifSqlNodes").get(node);
      String childIndent = null;
      for (SqlNode ifNode : ifSqlNodes) {
        String test = (String) findField(ifNode.getClass(), "test").get(ifNode);
        SqlNode contents = (SqlNode) findField(ifNode.getClass(), "contents").get(ifNode);
        StringBuilder temp = new StringBuilder();
        extractSql(contents, temp);
        if (childIndent == null) {
          childIndent = trailingIndent(temp);
          if (childIndent.isEmpty()) {
            childIndent = chooseIndent + "  ";
          }
        }
        sb.append("\n").append(childIndent).append("<when ").append(attr("test", test)).append(">");
        sb.append(temp);
        sb.append("</when>");
      }
      SqlNode defaultNode = (SqlNode) findField(node.getClass(), "defaultSqlNode").get(node);
      if (defaultNode != null) {
        StringBuilder temp = new StringBuilder();
        extractSql(defaultNode, temp);
        if (childIndent == null) {
          childIndent = trailingIndent(temp);
          if (childIndent.isEmpty()) {
            childIndent = chooseIndent + "  ";
          }
        }
        sb.append("\n").append(childIndent).append("<otherwise>");
        sb.append(temp);
        sb.append("</otherwise>");
      }
      sb.append("\n").append(chooseIndent).append("</choose>");
    } else if (className.endsWith("ForEachSqlNode")) {
      // <foreach collection="..." item="..." index="..." open="..." close="..."
      // separator="...">...</foreach>
      String collection = (String) findField(node.getClass(), "collectionExpression").get(node);
      String item = (String) findField(node.getClass(), "item").get(node);
      String index = (String) findField(node.getClass(), "index").get(node);
      String open = (String) findField(node.getClass(), "open").get(node);
      String close = (String) findField(node.getClass(), "close").get(node);
      String separator = (String) findField(node.getClass(), "separator").get(node);
      SqlNode contents = (SqlNode) findField(node.getClass(), "contents").get(node);
      sb.append("<foreach");
      sb.append(" ").append(attr("collection", collection));
      if (item != null) {
        sb.append(" ").append(attr("item", item));
      }
      if (index != null) {
        sb.append(" ").append(attr("index", index));
      }
      if (open != null) {
        sb.append(" ").append(attr("open", open));
      }
      if (close != null) {
        sb.append(" ").append(attr("close", close));
      }
      if (separator != null) {
        sb.append(" ").append(attr("separator", separator));
      }
      sb.append(">");
      extractSql(contents, sb);
      sb.append("</foreach>");
    } else if (className.endsWith("VarDeclSqlNode")) {
      // <bind name="..." value="..." />
      String name = (String) findField(node.getClass(), "name").get(node);
      String expression = (String) findField(node.getClass(), "expression").get(node);
      sb.append("<bind ").append(attr("name", name)).append(" ").append(attr("value", expression)).append(" />");
    } else if (className.endsWith("WhereSqlNode")) {
      // <where>...</where>
      SqlNode contents = (SqlNode) findField(node.getClass(), "contents").get(node);
      sb.append("<where>");
      extractSql(contents, sb);
      sb.append("</where>");
    } else if (className.endsWith("SetSqlNode")) {
      // <set>...</set>
      SqlNode contents = (SqlNode) findField(node.getClass(), "contents").get(node);
      sb.append("<set>");
      extractSql(contents, sb);
      sb.append("</set>");
    } else if (className.endsWith("TrimSqlNode")) {
      // <trim prefix="..." suffix="..." prefixOverrides="..."
      // suffixOverrides="...">...</trim>
      SqlNode contents = (SqlNode) findField(node.getClass(), "contents").get(node);
      String prefix = (String) findField(node.getClass(), "prefix").get(node);
      String suffix = (String) findField(node.getClass(), "suffix").get(node);
      List<String> prefixesToOverride = (List<String>) findField(node.getClass(), "prefixesToOverride").get(node);
      List<String> suffixesToOverride = (List<String>) findField(node.getClass(), "suffixesToOverride").get(node);
      sb.append("<trim");
      if (prefix != null) {
        sb.append(" ").append(attr("prefix", prefix));
      }
      if (suffix != null) {
        sb.append(" ").append(attr("suffix", suffix));
      }
      if (prefixesToOverride != null && !prefixesToOverride.isEmpty()) {
        sb.append(" ").append(attr("prefixOverrides", String.join("|", prefixesToOverride)));
      }
      if (suffixesToOverride != null && !suffixesToOverride.isEmpty()) {
        sb.append(" ").append(attr("suffixOverrides", String.join("|", suffixesToOverride)));
      }
      sb.append(">");
      extractSql(contents, sb);
      sb.append("</trim>");
    } else {
      // 兜底：尝试用 DynamicContext apply
      node.apply(new org.apache.ibatis.scripting.xmltags.DynamicContext(new Configuration(), new HashMap<>()));
    }
  }

  private static Field findField(Class<?> clazz, String name) throws NoSuchFieldException {
    Class<?> c = clazz;
    while (c != null) {
      try {
        Field f = c.getDeclaredField(name);
        f.setAccessible(true);
        return f;
      } catch (NoSuchFieldException e) {
        c = c.getSuperclass();
      }
    }
    throw new NoSuchFieldException(name);
  }

  private static String attr(String name, String value) {
    if (value == null) {
      return "";
    }
    if (value.contains("\"") && value.contains("'")) {
      return name + "=\"" + value.replace("\"", "&quot;") + "\"";
    }
    String q = value.contains("\"") ? "'" : "\"";
    return name + "=" + q + value + q;
  }

  private static String trailingIndent(StringBuilder sb) {
    for (int i = sb.length() - 1; i >= 0; i--) {
      char c = sb.charAt(i);
      if (c == '\n') {
        return sb.substring(i + 1);
      }
      if (!Character.isWhitespace(c)) {
        return "";
      }
    }
    return "";
  }
}