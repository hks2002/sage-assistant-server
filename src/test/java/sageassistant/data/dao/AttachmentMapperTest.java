/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-12 21:39:28                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:43:01                                                                      *
 * @FilePath              : src/test/java/sageassistant/data/dao/AttachmentMapperTest.java                           *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AttachmentMapperTest {

    @Autowired
    AttachmentMapper attachmentMapper;

    @Test
    void testGetAttachment() {
        attachmentMapper.getAttachment("956A1001G01");
    }
}
