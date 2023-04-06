/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-03-10 15:17:08                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2023-03-12 14:06:15                                                                       *
 * @FilePath              : src/test/java/sageassistant/dataSrv/SageAssistantDataServerApplicationTests.java          *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package sageassistant.data;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

/* using MybatisPlusTest instead of SpringBootTest, due to database autowired */
// @SpringBootTest

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SageAssistantDataServerApplicationTests {

    @Test
    void contextLoads() {}
}
