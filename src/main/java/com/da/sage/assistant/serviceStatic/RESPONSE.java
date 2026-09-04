/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-09 23:29:08                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 17:02:45                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.serviceStatic;

import java.util.Arrays;
import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.MIMEHeader;
import io.vertx.ext.web.ParsedHeaderValues;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.ParsableMIMEValue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RESPONSE {

  public static String getAccept(RoutingContext ctx) {
    ParsedHeaderValues headerValues = ctx.parsedHeaders();
    final List<MIMEHeader> accepts = headerValues.accept();
    String accept = "text/plain";

    if (accepts != null) {
      MIMEHeader header = headerValues
          .findBestUserAcceptedIn(accepts, Arrays.asList(
              new ParsableMIMEValue("text/html").forceParse(),
              new ParsableMIMEValue("text/plain").forceParse(),
              new ParsableMIMEValue("application/json").forceParse()));
      if (header != null) {
        accept = header.component() + "/" + header.subComponent();
      }
    }
    return accept;
  }

  public static void internalError(RoutingContext ctx) {
    internalError(ctx, "Server error");
  }

  public static void internalError(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);
    log.error(msg);

    switch (accept) {
      case "text/plain":
        response.setStatusCode(500);
        response.end(msg);
        break;
      case "text/html":
        response.setStatusCode(301);
        response.putHeader(HttpHeaders.LOCATION, "/sa-web/#/Exception/500");
        response.end();
        break;
      case "application/json":
        response.setStatusCode(500);
        JsonObject result = new JsonObject();
        result.put("success", false);
        result.put("msg", msg);
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void unauthorized(RoutingContext ctx) {
    unauthorized(ctx, "Unauthorized");
  }

  public static void unauthorized(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    switch (accept) {
      case "text/plain":
        response.setStatusCode(401);
        response.putHeader("WWW-Authenticate", "Basic realm=\"Ad Authorization\"");
        response.end(msg);
        break;
      case "text/html":
        response.setStatusCode(301);
        response.putHeader(HttpHeaders.LOCATION, "/sa-web/#/login");
        response.end();
        break;
      case "application/json":
        response.setStatusCode(401);
        JsonObject result = new JsonObject();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", (new JsonArray()));
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void forbidden(RoutingContext ctx) {
    forbidden(ctx, "Forbidden");
  }

  public static void forbidden(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    switch (accept) {
      case "text/plain":
        response.setStatusCode(403);
        response.end(msg);
        break;
      case "text/html":
        response.setStatusCode(301);
        response.putHeader(HttpHeaders.LOCATION, "/sa-web/#/Exception/403");
        response.end();
        break;
      case "application/json":
        response.setStatusCode(403);
        JsonObject result = new JsonObject();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", (new JsonArray()));
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void badRequest(RoutingContext ctx) {
    badRequest(ctx, "Bad Request");
  }

  public static void badRequest(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    log.warn(msg);

    switch (accept) {
      case "text/plain":
        response.setStatusCode(400);
        response.end(msg);
        break;
      case "text/html":
        response.setStatusCode(301);
        response.putHeader(HttpHeaders.LOCATION, "/sa-web/#/Exception/400");
        response.end();
        break;
      case "application/json":
        response.setStatusCode(400);
        JsonObject result = new JsonObject();
        result.put("success", false);
        result.put("msg", msg);
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void failed(RoutingContext ctx) {
    failed(ctx, "Failed");
  }

  public static void failed(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    log.warn(msg);

    switch (accept) {
      case "text/plain":
        response.setStatusCode(200);
        response.end(msg);
        break;
      case "text/html":
        response.setStatusCode(200);
        response.end(msg);
        break;
      case "application/json":
        response.setStatusCode(200);
        JsonObject result = new JsonObject();
        result.put("success", false);
        result.put("msg", msg);
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void notFound(RoutingContext ctx) {
    notFound(ctx, "Not Found");
  }

  public static void notFound(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    log.warn(msg);

    switch (accept) {
      case "text/plain":
        response.setStatusCode(404);
        response.end(msg);
        break;
      case "text/html":
        response.setStatusCode(301);
        response.putHeader(HttpHeaders.LOCATION, "/sa-web/#/Exception/404");
        response.end();
        break;
      case "application/json":
        response.setStatusCode(404);
        JsonObject result = new JsonObject();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", (new JsonArray()));
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void success(RoutingContext ctx) {
    success(ctx, "Success");
  }

  public static void success(RoutingContext ctx, String msg) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    switch (accept) {
      case "application/json":
        JsonObject result = new JsonObject();
        result.put("success", true);
        result.put("msg", "Success");
        response.end(result.encode());
        break;
      default:
        response.end(msg);
    }
  }

  public static void success(RoutingContext ctx, JsonObject data) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    switch (accept) {
      case "application/json":
        JsonObject result = new JsonObject();
        result.put("success", true);
        result.put("msg", "Success");
        // result.put("data", encryptJsonObject(data));
        result.put("data", data);
        response.end(result.encode());
        break;
      default:
        response.end(data.encode());
    }
  }

  public static void success(RoutingContext ctx, JsonArray data) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);

    switch (accept) {
      case "application/json":
        JsonObject result = new JsonObject();
        result.put("success", true);
        result.put("msg", "Success");
        // result.put("data", encryptJsonArray(data));
        result.put("data", (data));
        response.end(result.encode());
        break;
      default:
        response.end(data.encode());
    }
  }

  public static void success(RoutingContext ctx, JsonArray data, Integer total) {
    HttpServerResponse response = ctx.response();
    String accept = getAccept(ctx);

    response.putHeader(HttpHeaders.CONTENT_TYPE, accept);
    JsonObject result = new JsonObject();
    result.put("success", true);
    result.put("msg", "Success");
    result.put("data", (data));
    result.put("total", total);

    switch (accept) {
      case "application/json":
        response.end(result.encode());
        break;
      default:
        response.end(result.encode());
    }
  }

  public static void responseArray(RoutingContext ctx, Future<JsonArray> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, v);
    });
  }

  public static void responseObject(RoutingContext ctx, Future<JsonObject> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, v);
    });
  }

  public static void responseObjectDataTotal(RoutingContext ctx, Future<JsonObject> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      JsonArray d = v.getJsonArray("data");
      Integer total = v.getInteger("total");
      success(ctx, d, total);
    });
  }

  public static void responseString(RoutingContext ctx, Future<String> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, v);
    });
  }

  public static void responseAssign(RoutingContext ctx, Future<Long> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, "Assigned");
    });
  }

  public static void responseUnAssign(RoutingContext ctx, Future<Integer> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, "UnAssigned");
    });
  }

  public static void responseCreate(RoutingContext ctx, Future<Long> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, JsonObject.of("id", v));
    });
  }

  public static void responseUpdate(RoutingContext ctx, Future<Integer> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, "Modified");
    });
  }

  public static void responseDelete(RoutingContext ctx, Future<Integer> data) {
    data.onFailure(err -> {
      failed(ctx, err.getMessage());
    }).onSuccess((v) -> {
      success(ctx, "Deleted");
    });
  }
}