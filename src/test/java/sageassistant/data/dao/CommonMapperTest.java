/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-12 19:28:43                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:43:13                                                                      *
 * @FilePath              : src/test/java/sageassistant/data/dao/CommonMapperTest.java                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommonMapperTest {

    @Autowired
    CommonMapper commonMapper;

    @Test
    void testGetAllSites() {
        commonMapper.getAllSites();
    }
}
