/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-01 21:16:36                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-06-02 16:41:45                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        registry.addInterceptor(new LoginAuthInterceptor())
                .excludePathPatterns("/Data/Login", "/Data/Logout")
                .excludePathPatterns("/Data/AnalysesQuoteSalesCost",
                        "/Data/AnalysesQuoteSalesCostByTarget",
                        "/Data/AnalysesPurchase",
                        "/Data/AnalysesQuote",
                        "/Data/AnalysesSales");
    }
}
