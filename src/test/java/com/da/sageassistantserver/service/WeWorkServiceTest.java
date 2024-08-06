/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-03-16 17:14:44                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-08-06 22:49:11                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

public class WeWorkServiceTest {

  @Test
  void testSendMessage() throws ExecutionException {
    WeWorkService.sendMessage(
        "xxxxxx",
        "For Samples and Templates");
  }

}