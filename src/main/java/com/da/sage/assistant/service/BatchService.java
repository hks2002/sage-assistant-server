/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 15:25:32                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.BatchMapper;
import com.da.sage.assistant.model.BatchPurchase;
import com.da.sage.assistant.model.BatchQuote;
import com.da.sage.assistant.model.BatchSales;
import com.da.sage.assistant.utils.DateUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {

  private final BatchMapper batchMapper;
  private final CurrencyService currencyService;

  public String getSales(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot,
      @Param("Currency") String Currency,
      @Param("Target") String Target,
      @Param("LastN") String LastN) {
    List<BatchSales> list = batchMapper.getSales(Site, PnRoot, LastN);
    if (list.size() == 0) {
      return "";
    }

    BatchSales o = list.get(0);
    String rtn = "";
    switch (Target) {
      case "OrderNO":
        rtn = o.getOrderNO();
        break;
      case "OrderDate":
        rtn = DateUtils.formatDate(o.getOrderDate());
        break;
      case "CustomerCode":
        rtn = o.getCustomerCode();
        break;
      case "CustomerName":
        rtn = o.getCustomerName();
        break;
      case "NetPrice":
        String date = DateUtils.formatDate(o.getOrderDate());
        String targetCurrency = o.getCurrency() + Currency + date;
        rtn = String.format("%.2f",
            Float.parseFloat(currencyService.getCurrencyRate(targetCurrency)) * o.getNetPrice());
        break;
      case "QTY":
        rtn = o.getQTY().toString();
        break;
      default:
        break;
    }

    return rtn;
  }

  public String getQuote(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot,
      @Param("Currency") String Currency,
      @Param("Target") String Target,
      @Param("LastN") String LastN) {
    List<BatchQuote> list = batchMapper.getQuote(Site, PnRoot, LastN);
    if (list.size() == 0) {
      return "";
    }

    BatchQuote o = list.get(0);
    String rtn = "";
    switch (Target) {
      case "QuoteNO":
        rtn = o.getQuoteNO();
        break;
      case "QuoteDate":
        rtn = DateUtils.formatDate(o.getQuoteDate());
        break;
      case "CustomerCode":
        rtn = o.getCustomerCode();
        break;
      case "CustomerName":
        rtn = o.getCustomerName();
        break;
      case "NetPrice":
        String date = DateUtils.formatDate(o.getQuoteDate());
        String targetCurrency = o.getCurrency() + Currency + date;
        rtn = String.format("%.2f",
            Float.parseFloat(currencyService.getCurrencyRate(targetCurrency)) * o.getNetPrice());
        break;
      case "QTY":
        rtn = o.getQTY().toString();
        break;
      case "OrderFlag":
        rtn = o.getOrderFlag().toString();
        break;
      case "OrderNO":
        rtn = o.getOrderNO();
      default:
        break;
    }
    return rtn;
  }

  public String getPurchase(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot,
      @Param("Currency") String Currency,
      @Param("Target") String Target,
      @Param("LastN") String LastN) {
    List<BatchPurchase> list = batchMapper.getPurchase(Site, PnRoot, LastN);
    if (list.size() == 0) {
      return "";
    }

    BatchPurchase o = list.get(0);
    String rtn = "";
    switch (Target) {
      case "ProjectNO":
        rtn = o.getProjectNO();
        break;
      case "ProjectDate":
        rtn = DateUtils.formatDate(o.getProjectDate());
        break;
      case "LocalCostNoTax":
        String date = DateUtils.formatDate(o.getProjectDate());
        String targetCurrency = o.getCurrency() + Currency + date;
        rtn = String.format("%.2f",
            Float.parseFloat(currencyService.getCurrencyRate(targetCurrency)) * o.getLocalCostNoTax());
        break;
      case "LocalCostWithTax":
        String date2 = DateUtils.formatDate(o.getProjectDate());
        String targetCurrency2 = o.getCurrency() + Currency + date2;
        rtn = String.format("%.2f",
            Float.parseFloat(currencyService.getCurrencyRate(targetCurrency2)) * o.getLocalCostWithTax());
        break;
      default:
        break;
    }
    return rtn;

  }

}
