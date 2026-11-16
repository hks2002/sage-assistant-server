/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-08 19:11:51                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-02-15 22:11:12                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant;

import com.da.sage.assistant.handler.WebSocketHandler;
import com.da.sage.assistant.router.RootRouter;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebServerVerticle extends VerticleBase {
  @Override
  public Future<?> start() {
    JsonObject httpConfig = config().getJsonObject("http");

    return vertx.createHttpServer(new HttpServerOptions(httpConfig))
        .requestHandler(RootRouter.create(vertx))
        .webSocketHandler(new WebSocketHandler())
        .listen().onSuccess(http -> {
          log.info("HTTP server started on port {}", http.actualPort());
        });
  }

  @Override
  public Future<?> stop() {
    return Future.succeededFuture();
  }
}