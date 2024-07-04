/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-11-09 18:52:05                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-04 12:58:00                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.http.MediaType;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;

public class JSONConverter extends FastJsonHttpMessageConverter {

    public JSONConverter() {
        super();
        FastJsonConfig jsonConfig = new FastJsonConfig();
        jsonConfig.setDateFormat("yyyy-MM-dd");
        jsonConfig.setCharset(StandardCharsets.UTF_8);

        setFastJsonConfig(jsonConfig);
        setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
    }
}
