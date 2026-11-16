/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-02-09 18:49:46                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-10-10 22:53:19                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.db;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.da.sage.assistant.AppConfig;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBQueryCache {
  private static final int DB_MAX_CACHE_THREAD_SIZE = AppConfig.config.getJsonObject("dbCache", JsonObject.of())
      .getInteger("threadSize", 10);
  private static final int DB_MAX_CACHE_SIZE = AppConfig.config.getJsonObject("dbCache", JsonObject.of())
      .getInteger("cacheSize", 1_000);
  private static final long DB_CACHE_EXPIRE_AFTER_WRITE = AppConfig.config.getJsonObject("dbCache", JsonObject.of())
      .getInteger("expireAfterWrite", 15_000);

  private static final Map<String, Long> NAMESPACE_DEFAULT_TTL = new ConcurrentHashMap<>();
  private static final Map<String, Long> KEY_CUSTOM_TTL = new ConcurrentHashMap<>();
  private static final ExecutorService CACHE_LOAD_EXECUTOR = Executors.newFixedThreadPool(DB_MAX_CACHE_THREAD_SIZE);

  // namespace -> AsyncLoadingCache
  private static final Map<String, AsyncLoadingCache<String, JsonArray>> CACHES = new ConcurrentHashMap<>();

  private DBQueryCache() {
  }

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

  /**
   * 拼接key用于KEY_CUSTOM_TTL的复合key：namespace + cacheKey
   */
  private static String compositeTtlKey(String ns, String cacheKey) {
    return ns + "::" + cacheKey;
  }

  private static AsyncLoadingCache<String, JsonArray> getCache(String key) {
    String namespace = getNamespaceFromKey(key);
    return CACHES.computeIfAbsent(namespace, ns -> {
      JsonObject cacheConfig = MybatisHelper.getCacheConfig(ns);
      if (cacheConfig.isEmpty()) {
        log.warn("No cache config found for namespace [{}], skip cache", ns);
        return null;
      }
      int maxSize = cacheConfig.getInteger("size", DB_MAX_CACHE_SIZE);
      long defaultTtlMs = cacheConfig.getLong("flushInterval", DB_CACHE_EXPIRE_AFTER_WRITE);
      NAMESPACE_DEFAULT_TTL.put(ns, defaultTtlMs);
      log.debug("Create cache for namespace [{}] maxSize={}, defaultTtl={}ms", ns, maxSize, defaultTtlMs);

      Expiry<String, JsonArray> perKeyExpiry = new Expiry<>() {
        @Override
        public long expireAfterCreate(String cacheKey, JsonArray value, long currentTime) {
          String ttlKey = compositeTtlKey(ns, cacheKey);
          Long customTtl = KEY_CUSTOM_TTL.get(ttlKey);
          long ttl = customTtl != null ? customTtl : defaultTtlMs;
          return TimeUnit.MILLISECONDS.toNanos(ttl);
        }

        @Override
        public long expireAfterUpdate(String cacheKey, JsonArray value, long currentTime, long currentDuration) {
          // 更新缓存时，复用当前剩余TTL，也可以改成重新取TTL
          return currentDuration;
        }

        @Override
        public long expireAfterRead(String cacheKey, JsonArray value, long currentTime, long currentDuration) {
          // 不做访问续期，只按写入时间过期；如需访问续期改成返回TTL
          return currentDuration;
        }
      };

      return Caffeine.newBuilder()
          .maximumSize(maxSize)
          .expireAfter(perKeyExpiry)
          .executor(CACHE_LOAD_EXECUTOR)
          .buildAsync((cacheKey, exec) -> loadQueryData(cacheKey));
    });
  }

  private static CompletableFuture<JsonArray> loadQueryData(String cacheKey) {
    String[] keys = cacheKey.split("\\|");
    if (keys.length < 3) {
      log.error("Invalid cache key format: {}", cacheKey);
      return CompletableFuture.failedFuture(new IllegalArgumentException("Invalid cache key"));
    }
    String sqlId = keys[0];
    String target = keys[1];
    JsonObject paramJson;
    try {
      paramJson = new JsonObject(keys[2]);
    } catch (Exception e) {
      log.error("Parse param json failed, key:{}", cacheKey, e);
      return CompletableFuture.failedFuture(e);
    }
    return DB.selectBySqlId(sqlId, target, paramJson).toCompletionStage().toCompletableFuture();
  }

  public static Future<JsonArray> getIfPresent(String key) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache == null) {
      return Future.succeededFuture(null);
    }
    var dataFuture = cache.getIfPresent(key);
    if (dataFuture == null) {
      return Future.succeededFuture(null);
    }
    return Future.fromCompletionStage(dataFuture);
  }

  public static Future<JsonArray> get(String key) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache == null) {
      String[] keys = key.split("\\|");
      if (keys.length < 3) {
        return Future.failedFuture(new IllegalArgumentException("Invalid cache key: " + key));
      }
      String sqlId = keys[0];
      String target = keys[1];
      JsonObject paramJson = new JsonObject(keys[2]);
      return DB.selectBySqlId(sqlId, target, paramJson);
    }
    return Future.fromCompletionStage(cache.get(key));
  }

  public static void invalidateAll() {
    for (AsyncLoadingCache<String, JsonArray> cache : CACHES.values()) {
      if (cache != null) {
        cache.synchronous().invalidateAll();
      }
    }
    KEY_CUSTOM_TTL.clear();
    NAMESPACE_DEFAULT_TTL.clear();
    log.debug("Invalidated all cache entries across all namespaces");
  }

  public static void invalidateByKey(String key) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache != null) {
      String ns = getNamespaceFromKey(key);
      KEY_CUSTOM_TTL.remove(compositeTtlKey(ns, key));
      NAMESPACE_DEFAULT_TTL.remove(ns);
      cache.synchronous().invalidate(key);
    }
  }

  public static void invalidateByNameSpace(String namespace) {
    AsyncLoadingCache<String, JsonArray> cache = CACHES.get(namespace);
    if (cache != null) {
      cache.synchronous().invalidateAll();
      // 清理该namespace下所有自定义TTL
      KEY_CUSTOM_TTL.keySet().removeIf(k -> k.startsWith(namespace + "::"));
      NAMESPACE_DEFAULT_TTL.remove(namespace);
      log.debug("Invalidated all cache entries for namespace: {}", namespace);
    }
  }

  /**
   * 使用namespace默认TTL写入缓存
   */
  public static void put(String key, JsonArray value) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache != null) {
      // 清除该key旧的自定义TTL，使用namespace默认TTL
      String ns = getNamespaceFromKey(key);
      KEY_CUSTOM_TTL.remove(compositeTtlKey(ns, key));
      NAMESPACE_DEFAULT_TTL.remove(ns);
      cache.synchronous().put(key, value);
      log.debug("Added cache entry: {} (use namespace default ttl)", key);
    }
  }

  /**
   * ✅ 核心重载：put时传入自定义过期毫秒，覆盖namespace默认TTL
   */
  public static void put(String key, JsonArray value, long expireAfterMilliSeconds) {
    AsyncLoadingCache<String, JsonArray> cache = getCache(key);
    if (cache == null) {
      return;
    }
    String ns = getNamespaceFromKey(key);
    String ttlKey = compositeTtlKey(ns, key);
    KEY_CUSTOM_TTL.put(ttlKey, expireAfterMilliSeconds);
    NAMESPACE_DEFAULT_TTL.remove(ns);
    cache.synchronous().put(key, value);
    log.debug("Added cache entry: {} custom expire={}ms", key, expireAfterMilliSeconds);
  }

  /**
   * 应用关闭时释放线程池
   */
  public static void close() {
    CACHE_LOAD_EXECUTOR.shutdown();
  }
}
