/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-06-22 16:50:30                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2023-06-22 16:51:35                                                                       *
 * @FilePath              : src/test/java/com/da/sageassistantserver/SageAssistantServerApplicationTests.java         *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

/* using MybatisPlusTest instead of SpringBootTest, due to database autowired */
// @SpringBootTest

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SageAssistantServerApplicationTests {

    @Test
    void contextLoads() {}
}
