/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-03-16 17:14:44                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-08-06 23:12:47                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MSteamsServiceTest {

  @Test
  void testSendMessage() throws ExecutionException {

    MSteamsService.sendMessage(
        "https://prod-237.westeurope.logic.azure.com:443/workflows/e45882a158a54f3a9da05ca175151260/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=HGnjU85BzZev6ABzRoTMsfNPPiRZuddtXtRUGsQ01mk",
        "For Samples and xxxxpppppp");
  }

}