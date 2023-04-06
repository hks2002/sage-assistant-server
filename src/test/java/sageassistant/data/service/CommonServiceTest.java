/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-11 16:26:53                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:44:41                                                                      *
 * @FilePath              : src/test/java/sageassistant/data/service/CommonServiceTest.java                          *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.service;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommonServiceTest {

    // Unit Test for Service，sample code
    @TestConfiguration
    static class prepare {

        @Bean
        public CommonService getService() {
            return new CommonService();
        }
    }

    @Autowired
    CommonService commonService;

    @Test
    void contextLoads() {
        commonService.getAllSites();
    }
}
