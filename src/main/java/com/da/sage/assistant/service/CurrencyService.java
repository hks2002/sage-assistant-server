/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 09:54:18                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.CurrencyMapper;
import com.da.sage.assistant.model.CurrencyHistory;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyService {

  private static HashMap<String, String> defaultRate = new HashMap<String, String>();

  private final CurrencyMapper currencyMapper;

  /**
   * google guava cache
   */
  private LoadingCache<String, String> cache = Caffeine
      .newBuilder()
      .build(
          new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
              return getFromSage(key);
            }
          });

  @PostConstruct
  public void initDefaultCurrency() {
    defaultRate.put("EURUSD", "1.18");
    defaultRate.put("GBPUSD", "1.31");
    defaultRate.put("SGDUSD", "0.73");
    defaultRate.put("RMBUSD", "0.145");
    defaultRate.put("HKDUSD", "0.13");
    defaultRate.put("MXNUSD", "0.064");
    defaultRate.put("AEDUSD", "0.27");
    defaultRate.put("QARUSD", "0.275");
    defaultRate.put("CADUSD", "0.7639");
    defaultRate.put("AUDUSD", "0.7285");
    defaultRate.put("JPYUSD", "0.00962");
  }

  private String getFromSage(String key) {
    if (key.length() != 16) {
      return "0";
    }

    String Sour = key.substring(0, 3);
    if (Sour.equals("CNY")) {
      Sour = "RMB";
    }
    String Dest = key.substring(3, 6);
    String Date = key.substring(6, 16);

    if (Sour.equals(Dest)) {
      return "1";
    }

    List<CurrencyHistory> list = currencyMapper.findCurrencyRate(Sour, Dest, Date);

    String rateStr = "0";
    if (list.size() > 0) {
      rateStr = list.get(0).getRate().toString();
    } else if (defaultRate.containsKey(Sour + Dest)) {
      rateStr = defaultRate.get(Sour + Dest);
    } else {
      rateStr = "0";
    }

    return rateStr;
  }

  /*
   * key like RMBUSD2010-10-10, if not find, return "0"
   */
  public String getCurrencyRate(String key) {
    String val = cache.get(key);
    return val == null ? "0" : val;
  }

}
