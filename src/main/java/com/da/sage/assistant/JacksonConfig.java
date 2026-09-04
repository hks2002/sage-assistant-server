/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-04 14:39:45                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-07-24 11:50:45                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import io.vertx.core.json.jackson.DatabindCodec;

public final class JacksonConfig {

  private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  public static void setup() {
    ObjectMapper mapper = DatabindCodec.mapper();

    mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(new LocalDateTimeSerializer(DATETIME_FORMATTER));
    javaTimeModule.addSerializer(new LocalDateSerializer(DATE_FORMATTER));
    javaTimeModule.addSerializer(new LocalTimeSerializer(TIME_FORMATTER));
    mapper.registerModule(javaTimeModule);

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    mapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    // mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}