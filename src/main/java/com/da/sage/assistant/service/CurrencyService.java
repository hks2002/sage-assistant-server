/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-07-04 11:20:50                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-09-03 17:22:10                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.da.sage.assistant.db.DB;
import com.da.sage.assistant.serviceStatic.FS;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CurrencyService {

  // @formatter:off
  public static final Set<String> CURRENCY_RELATED_TARGET = Set.of(
      "NetPrice", 
      "ProvideAmount", "ProvideAmountWithTax", 
      "ProvideCost", "ProvideCostWithTax",
      "SalesAmount", "SalesAmountWithTax", 
      "Cost", "CostWithTax",
      "Profit","ProfitWithTax"
  );
  // @formatter:on

  /**
   * google guava cache
   */
  private static AsyncLoadingCache<String, Float> cache = Caffeine.newBuilder()
      .buildAsync((key, exec) -> {
        return getRate(key).toCompletionStage().toCompletableFuture();
      });

  /**
   * Get currency rate
   * 
   * @param key RMBUSD2010-10-10, if not find, return 0
   * @return
   */
  public static Future<Float> getRate(String key) {
    if (key.length() != 16) {
      log.warn("key: {} is not valid", key);
      return Future.succeededFuture(0.0f);
    }

    String sour = key.substring(0, 3);
    if (sour.equals("CNY")) {
      sour = "RMB";
    }
    String dest = key.substring(3, 6);
    String date = key.substring(6, 16);

    if (sour.equals(dest)) {
      return Future.succeededFuture(1.0f);
    }

    return getRate(sour, dest, date);
  }

  /**
   * Get currency rate, try extractly date rate first, then use USD convert, then
   * avg rate, if not find, return 0
   * 
   * @param sour
   * @param dest
   * @param date
   * @return
   */
  public static Future<Float> getRate(String sour, String dest, String date) {
    return getDateRate(sour, dest, date)
        .recover(e1 -> getAvgRate(sour, dest)
            .recover((e2) -> getDateRateByUSD(sour, dest, date)
                .recover(e3 -> Future.succeededFuture(0.0f))));
  }

  private static Future<Float> getDateRate(String sour, String dest, String date) {
    JsonObject params = JsonObject.of("Sour", sour, "Dest", dest, "Date", date);
    log.debug("getDateRate: {}", params);

    var f1 = DB.selectBySqlIdWithCache(
        "sage",
        "com.da.sage.assistant.mapper.CurrencyMapper.CURRENCY_RATE_HISTORY_ONE_DAY",
        params);

    return f1.compose(val -> {
      if (val.size() == 0) {
        return Future.failedFuture("Currency rate not found: " + sour + dest + date);
      } else {
        return Future.succeededFuture(val.getJsonObject(0).getFloat("Rate"));
      }
    });
  }

  private static Future<Float> getAvgRate(String sour, String dest) {
    JsonObject params = JsonObject.of("Date", "2020-01-01", "Bottom", 0.1, "Top", 0.9);
    log.debug("getAvgRate: {}", params);
    var f1 = DB.selectBySqlIdWithCache(
        "sage",
        "com.da.sage.assistant.mapper.CurrencyMapper.CURRENCY_RATE_HISTORY_AVG_NORMAL",
        params);

    return f1.compose(val -> {
      Float rate = 0.0f;

      for (int i = 0; i < val.size(); i++) {
        JsonObject item = val.getJsonObject(i);
        String Sour = item.getString("Sour");
        String Dest = item.getString("Dest");

        if (Sour.equals(sour) && Dest.equals(dest)) {
          rate = item.getFloat("AvgNormalRate");
          break;
        }
      }

      if (rate.equals(0.0f)) {
        return Future.failedFuture("Currency rate not found");
      } else {
        return Future.succeededFuture(rate);
      }
    });

  }

  private static Future<Float> getDateRateByUSD(String sour, String dest, String date) {
    log.debug("getDateRateByUSD: {}", sour + dest + date);
    CompletableFuture<Float> f1 = cache.getIfPresent(sour + "USD" + date);
    CompletableFuture<Float> f2 = cache.getIfPresent("USD" + dest + date);
    if (f1 == null || f2 == null) {
      return Future.failedFuture("Currency rate not found: " + sour + dest + date);
    }

    return Future.all(
        Future.fromCompletionStage(f1),
        Future.fromCompletionStage(f2)).map(cf -> {
          Float rate1 = (Float) cf.resultAt(0);
          Float rate2 = (Float) cf.resultAt(1);
          return rate1 * rate2;
        });
  }

  public static Future<Void> InitRate() {
    return DB.selectBySqlIdWithCache(
        "sage",
        "com.da.sage.assistant.mapper.CurrencyMapper.CURRENCY_RATE_HISTORY", new JsonObject())
        .compose(val -> {
          return FS.vertx.executeBlocking(() -> {

            for (int i = 0; i < val.size(); i++) {
              JsonObject item = val.getJsonObject(i);
              String Sour = item.getString("Sour");
              String Dest = item.getString("Dest");
              String StartDate = item.getString("StartDate");
              String EndDate = item.getString("EndDate");
              Float rate = item.getFloat("Rate");
              LocalDate StartLocalDate = LocalDate.parse(StartDate);
              LocalDate EndLocalDate = LocalDate.parse(EndDate);

              // adding all dates in the range to cache
              while (StartLocalDate.isBefore(EndLocalDate)) {
                cache.put(Sour + Dest + StartLocalDate.format(DateTimeFormatter.ISO_DATE),
                    CompletableFuture.completedFuture(rate));
                StartLocalDate = StartLocalDate.plusDays(1);
              }
            }

            log.info("Add {} Currency Rates", cache.synchronous().asMap().size());

            return null;
          });
        });
  }

  /**
   * Get currency rate from cache
   * 
   * @param key RMBUSD2010-10-10, if not find, return 0
   * @return
   */
  public static Future<Float> getCacheRate(String key) {
    return Future.fromCompletionStage(cache.get(key));
  }

  /**
   * Converts currency amounts in the data to the target currency, updating all
   * currency-related fields.
   *
   * @param data       the data array to convert, each record must contain a
   *                   Currency field and the specified date field
   * @param currencyTo the target currency
   * @param DateName   the date field name used to construct the rate lookup
   *                   key
   * @return a Future containing the converted data, or a failed Future with the
   *         error message on failure
   */
  public static Future<JsonArray> convertCurrency(JsonArray data, String currencyTo, String DateName) {
    try {
      List<Future<Float>> rates = new ArrayList<>();

      // Step 1: iterate through all records and fetch the exchange rate for each
      for (int i = 0; i < data.size(); i++) {
        JsonObject record = data.getJsonObject(i);
        String currencyInRecord = record.getString("Currency");

        if (!currencyInRecord.equals(currencyTo)) {
          String key = currencyInRecord + currencyTo + record.getString(DateName);
          rates.add(getCacheRate(key));
        } else {

          rates.add(Future.succeededFuture(1.0f));
        }
      }

      // Step 2: wait for all rates, then convert amounts in each record
      return Future.all(rates).map(r -> {
        for (int i = 0; i < data.size(); i++) {
          JsonObject record = data.getJsonObject(i);
          Float rate = r.resultAt(i);
          record.put("Rate", rate);

          for (String field : CURRENCY_RELATED_TARGET) {
            if (record.containsKey(field)) {
              float value = record.getFloat(field);
              float result = value * rate;
              record.put(field, String.format("%.2f", result));
            }
          }
        }
        return data;
      });

    } catch (Exception e) {
      log.error("{}", e);
      return Future.failedFuture(e.getMessage());
    }
  }

  /**
   * Converts currency amounts in the data to the USD，for the target Field.
   *
   * @param data      the data array to convert, each record must contain a
   *                  Currency field and the specified date field
   * @param DateName  the date field name used to construct the rate lookup
   * @param usdTarget the target field to convert to USD
   *                  key
   * @return a Future containing the converted data, or a failed Future with the
   *         error message on failure
   */
  public static Future<JsonArray> addUSD(JsonArray data, String DateName, String usdTarget) {
    try {
      List<Future<Float>> rates = new ArrayList<>();

      for (int i = 0; i < data.size(); i++) {
        JsonObject record = data.getJsonObject(i);
        String currencyInRecord = record.getString("Currency");

        if (!currencyInRecord.equals("USD")) {
          String key = currencyInRecord + "USD" + record.getString(DateName);
          rates.add(getCacheRate(key));
        } else {
          rates.add(Future.succeededFuture(1.0f));
        }
      }

      return Future.all(rates).map(r -> {
        for (int i = 0; i < data.size(); i++) {
          JsonObject record = data.getJsonObject(i);
          Float rate = r.resultAt(i);
          record.put("Rate", rate);

          if (record.containsKey(usdTarget)) {
            float value = record.getFloat(usdTarget);
            float result = value * rate;
            record.put("USD", result);
          }
        }
        return data;
      });

    } catch (Exception e) {
      log.error("{}", e);
      return Future.failedFuture(e.getMessage());
    }
  }

  public static Future<String> batchValue(JsonArray data, String currencyTo, String DateName) {
    if (data.isEmpty()) {
      return Future.succeededFuture("");
    }

    try {
      List<Future<Float>> rates = new ArrayList<>();

      for (int i = 0; i < data.size(); i++) {
        JsonObject record = data.getJsonObject(i);
        String currencyInRecord = record.getString("Currency");

        if (!currencyInRecord.equals(currencyTo)) {
          String key = currencyInRecord + currencyTo + record.getString(DateName);
          rates.add(getCacheRate(key));
        } else {
          rates.add(Future.succeededFuture(1.0f));
        }
      }

      return Future.all(rates)
          .map(v -> {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < data.size(); i++) {
              if (i > 0) {
                sb.append("\n");
              }

              JsonObject record = data.getJsonObject(i);
              boolean firstField = true;

              for (String field : record.fieldNames()) {
                if (!firstField) {
                  sb.append("\t");
                }

                firstField = false;

                String value = record.getString(field);

                if (CURRENCY_RELATED_TARGET.contains(field)) {
                  String currencyInRecord = record.getString("Currency");
                  if (!currencyInRecord.equals(currencyTo)) {
                    float rate = rates.get(i).result();
                    float result = Float.valueOf(value) * rate;
                    sb.append(String.format("%.2f", result));
                  } else {
                    sb.append(value);
                  }
                } else {
                  boolean isCurrencyTarget = field.equals("Currency") && !currencyTo.isEmpty();
                  if (isCurrencyTarget) {
                    sb.append(currencyTo);
                  } else {
                    if (field.toUpperCase().endsWith("DATE")) {
                      try {
                        LocalDate date = LocalDate.parse(value);
                        sb.append(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
                      } catch (Exception e) {
                        sb.append(value);
                      }
                    } else {
                      sb.append(value);
                    }
                  }
                }

              }
            }

            return sb.toString();
          }).recover(err -> {
            log.error("{}", err.getMessage());
            return Future.succeededFuture("");
          });
    } catch (Exception e) {
      log.error("{}", e);
      return Future.succeededFuture("");
    }
  }
}