/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-09 18:49:46                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 11:54:16                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.da.sage.assistant.AppConfig;
import com.da.sage.assistant.serviceStatic.FS;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBQueryCache {
  private static final int DB_MAX_CACHE_SIZE = Optional
      .ofNullable(AppConfig.config)
      .map(c -> c.getJsonObject("dbCache"))
      .map(j -> j.getInteger("cacheSize"))
      .orElse(1_000);
  private static final long DB_CACHE_EXPIRE_AFTER_WRITE = Optional
      .ofNullable(AppConfig.config)
      .map(c -> c.getJsonObject("dbCache"))
      .map(j -> j.getInteger("expireAfterWrite"))
      .orElse(15);
  private static final Map<String, AsyncLoadingCache<String, JsonArray>> CACHES = new ConcurrentHashMap<>();

  public static String getNamespace(String target) {
    int lastDot = target.lastIndexOf('.');
    if (lastDot > 0) {
      return target.substring(0, lastDot);
    }
    return "default";
  }

  public static String getNamespaceFromKey(String key) {
    String[] parts = key.split("\\|", 3);
    if (parts.length >= 2) {
      return getNamespace(parts[1]);
    }
    return "default";
  }

  private static AsyncLoadingCache<String, JsonArray> getCache(String key) {
    String namespace = getNamespaceFromKey(key);
    JsonObject cacheConfig = MybatisHelper.getCacheConfig(namespace);
    if (cacheConfig.isEmpty()) {
      return null;
    }
    return CACHES.computeIfAbsent(namespace, ns -> {
      int maxSize = cacheConfig.getInteger("size", DB_MAX_CACHE_SIZE);
      long refreshInterval = cacheConfig.getLong("flushInterval", DB_CACHE_EXPIRE_AFTER_WRITE);

      log.debug("Create cache for namespace [{}] maxSize={} refreshInterval={}ms", ns, maxSize, refreshInterval);
      return Caffeine.newBuilder()
          .maximumSize(maxSize)
          .expireAfterWrite(refreshInterval, TimeUnit.MILLISECONDS)
          .buildAsync((k, exec) -> {
            var keys = k.split("\\|");
            return DB.selectBySqlId(keys[0], keys[1], new JsonObject(keys[2])).toCompletionStage()
                .toCompletableFuture();
          });
    });
  }

  public static Future<JsonArray> getIfPresent(String key) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache == null) {
      return null;
    }
    var data = cache.getIfPresent(key);
    if (data == null) {
      return null;
    }
    return Future.fromCompletionStage(data);
  }

  public static Future<JsonArray> get(String key) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache == null) {
      var keys = key.split("\\|");
      return DB.selectBySqlId(keys[0], keys[1], new JsonObject(keys[2]));
    }
    return Future.fromCompletionStage(cache.get(key));
  }

  public static void invalidateAll() {
    for (AsyncLoadingCache<String, JsonArray> cache : CACHES.values()) {
      cache.synchronous().invalidateAll();
    }
    log.debug("Invalidated all cache entries across all namespaces");
  }

  public static void invalidateByKey(String key) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache != null) {
      cache.synchronous().invalidate(key);
    }
  }

  public static void invalidateByNameSpace(String namespace) {
    AsyncLoadingCache<String, JsonArray> cache = CACHES.get(namespace);
    if (cache != null) {
      cache.synchronous().invalidateAll();
      log.debug("Invalidated all cache entries for namespace: {}", namespace);
    }
  }

  public static void put(String key, JsonArray value) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache != null) {
      cache.synchronous().put(key, value);
      log.debug("Added cache entry: {}", key);
    }
  }

  public static void scheduleInvalidation(String key, int minutes) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache != null) {
      FS.vertx.setTimer(minutes * 60 * 1000L, id -> {
        cache.synchronous().invalidate(key);
        log.debug("Cache entry expired and invalidated: {}", key);
      });
    }
  }

  public static void put(String key, JsonArray value, int expireAfterMinutes) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache != null) {
      cache.synchronous().put(key, value);
      scheduleInvalidation(key, expireAfterMinutes);
      log.debug("Added cache entry: {} with expire time: {} minutes", key, expireAfterMinutes);
    }
  }

}