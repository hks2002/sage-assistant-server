/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-11-09 18:52:05                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-06-20 21:39:39                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;

@Configuration
public class JSONConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig jsonConfig = new FastJsonConfig();

        jsonConfig.setDateFormat("yyyy-MM-dd");
        jsonConfig.setCharset(StandardCharsets.UTF_8);

        jsonConverter.setFastJsonConfig(jsonConfig);
        jsonConverter.setSupportedMediaTypes(
                Collections.singletonList(MediaType.APPLICATION_JSON));

        // fastJson support return List<Object>
        // Add to last, then it could use default string converter,
        // because fastJson converter will add quotes to string.
        converters.add(jsonConverter);
    }
}
