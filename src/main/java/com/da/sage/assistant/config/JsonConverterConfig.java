/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2023-11-09 18:52:05                                                                      *
 * @LastEditDate          : 2025-07-23 10:05:51                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;

@Configuration
public class JsonConverterConfig implements WebMvcConfigurer {

  @Override
  @SuppressWarnings("null")
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setDateFormat("yyyy-MM-dd");

    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    // add application/json to support return json type from controller directly
    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
    converter.setFastJsonConfig(fastJsonConfig);
    converters.add(converter);
  }
}
