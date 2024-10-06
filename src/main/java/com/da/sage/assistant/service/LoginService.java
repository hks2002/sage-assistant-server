/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-11-23 20:45:00                                                                       *
 * @LastEditDate          : 2025-07-21 23:20:58                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.config.ADProperties;
import com.da.sage.assistant.utils.ResponseJson;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoginService {

  private final ADProperties adProperties;
  private static ADProperties adStaticProperties;

  @PostConstruct
  public void init() {
    adStaticProperties = adProperties;
  }

  /**
   * Key is auth
   * <p/>
   * Value is user info Object {full_name: true, email: id,.....}
   * <p/>
   * If failed, return null, will throw InvalidCacheLoadException
   *
   */
  private static LoadingCache<String, JSONObject> authCache = Caffeine
      .newBuilder()
      .expireAfterAccess(15, TimeUnit.MINUTES)
      .build(
          new CacheLoader<String, JSONObject>() {
            @Override
            public JSONObject load(String auth) {
              ADServices adServices = new ADServices();
              String url = adStaticProperties.getUrl();
              String domain = adStaticProperties.getDomain();
              String searchBase = adStaticProperties.getSearchBase();

              return adServices.adAuthorization(url, domain, searchBase, auth);
            }
          });

  /**
   * Do login, using authCache to get return login result,
   * Do really login will be called with high frequency, so cache the auth,
   * 15 minutes expire
   *
   * @param auth
   * @return
   */
  public static JSONObject doLogin(String auth) {
    try {
      JSONObject user = authCache.get(auth);
      if (user == null) {
        return ResponseJson.unauthorized("Login failed");
      } else {
        return ResponseJson.success(user);
      }

    } catch (Exception e) {
      return ResponseJson.unauthorized("Login failed");
    }

  }

  public static JSONObject doLogout(String auth) {
    JSONObject user = authCache.getIfPresent(auth);

    if (user == null) {
      return ResponseJson.success("Logout success");
    } else {
      authCache.invalidate(auth);
      return ResponseJson.success("Logout success");
    }
  }

}
