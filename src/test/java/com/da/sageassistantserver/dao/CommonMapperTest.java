/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-12 19:28:43                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-08 22:00:18                                                                      *
 * @FilePath              : src/test/java/com/da/sage/assistant/dao/CommonMapperTest.java                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;

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
