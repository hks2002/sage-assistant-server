/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-06-22 12:15:04                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2023-06-24 21:21:03                                                                       *
 * @FilePath              : src/main/java/com/da/sageassistantserver/SageAssistantServerApplication.java              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.filter.NameFilter;

@SpringBootApplication
public class SageAssistantServerApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Pascal case
        NameFilter pascalNameFilter = NameFilter.of(PropertyNamingStrategy.PascalCase);
        // Scan model and register
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:com/da/sageassistantserver/model/*.class");
        for (Resource res : resources) {
            String clsName = new SimpleMetadataReaderFactory().getMetadataReader(res).getClassMetadata().getClassName();
            JSON.register(Class.forName(clsName), pascalNameFilter);
        }
        // Date format
        JSON.configWriterDateFormat("yyyy-MM-dd");
        // BigDecimal as plain
        JSON.config(JSONWriter.Feature.WriteBigDecimalAsPlain);

        SpringApplication.run(SageAssistantServerApplication.class, args);
    }
}
