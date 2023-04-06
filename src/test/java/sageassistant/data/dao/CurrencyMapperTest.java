/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-12 21:42:56                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:43:20                                                                      *
 * @FilePath              : src/test/java/sageassistant/data/dao/CurrencyMapperTest.java                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CurrencyMapperTest {

    @Autowired
    private CurrencyMapper currencyMapper;

    @Test
    void testFindCurrencyRate() {
        currencyMapper.findCurrencyRate("USD", "CNY", "2020-01-01");
    }
}
