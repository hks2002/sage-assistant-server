/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-03-16 17:14:44                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-17 23:40:05                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WeWorkServiceTest {
    @TestConfiguration
    static class prepare {

        @Bean
        public NoticeService getService() {
            return new NoticeService();
        }
    }

    @Autowired
    NoticeService noticeService;

    @Test
    void testSendMessage() throws ExecutionException {
        noticeService.sendPNNotActive();
    }

}
