/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-03-16 17:14:44                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-13 20:46:55                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeWorkServiceTest {

    @Test
    void testGetAccessToken() throws ExecutionException {
        String token = WeWorkService.getAccessToken("wwda7e45e3e2ae2d9e",
                "WPLWPHxLlK95YV8Dd_JgWXn1dBMkyBXoRg89_tGPPPg");
        log.info(token);
    }

    @Test
    void testSendMessage() throws ExecutionException {
        // Copy the chart robot id from the WeWork Group
        String robotId = "xxxxx";
        WeWorkService.sendMessage(robotId, "text", "Hello World");
    }

}
