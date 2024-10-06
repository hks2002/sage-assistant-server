/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2024-06-06 12:40:24                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2024-12-25 14:53:54                                                                      *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sage.assistant;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AsyncFutureTest {

  @Test
  public void testRun() throws InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      log.info("Run Second");
      return "Return in Future";
    });

    future.thenAccept(s -> log.info(s));

    log.info("Run first");

    CompletableFuture.supplyAsync(() -> {
      log.info("Run ....");
      return null;
    });
    Thread.sleep(3000);

    CompletableFuture.runAsync(() -> {
      log.info("Run third");
    });
  }
}
