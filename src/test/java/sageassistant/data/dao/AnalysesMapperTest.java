/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-12 19:34:33                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:41:32                                                                      *
 * @FilePath              : src/test/java/sageassistant/data/dao/AnalysesMapperTest.java                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnalysesMapperTest {

    @Autowired
    AnalysesMapper analysesMapper;

    @Test
    void testAnalysesQuoteSalesCost() {
        analysesMapper.analysesQuoteSalesCost("ZHU", "C0002", "956A1001G01", "2023-01-01", "2023-01-02", 1);
    }

    @Test
    void testAnalysesPurchase() {
        analysesMapper.analysesPurchase("ZHU", "956A1001G01", "RMB", "1");
    }

    @Test
    void testAnalysesQuote() {
        analysesMapper.analysesQuote("ZHU", "956A1001G01", "RMB", "1");
    }

    @Test
    void testAnalysesSales() {
        analysesMapper.analysesSales("ZHU", "956A1001G01", "RMB", "1");
    }
}
