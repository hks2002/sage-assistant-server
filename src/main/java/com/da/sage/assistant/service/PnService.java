/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 11:25:31                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.PnMapper;
import com.da.sage.assistant.model.ComponentDeliveryDuration;
import com.da.sage.assistant.model.ComponentPurchaseHistory;
import com.da.sage.assistant.model.PnDetails;
import com.da.sage.assistant.model.PnIndustrialization;
import com.da.sage.assistant.model.PnOptionPn;
import com.da.sage.assistant.model.PnRootPn;
import com.da.sage.assistant.model.PnStatus;
import com.da.sage.assistant.model.ProductCostHistory;
import com.da.sage.assistant.model.ProductDeliveryDuration;
import com.da.sage.assistant.model.ProductQuoteHistory;
import com.da.sage.assistant.model.ProductSalesHistory;
import com.da.sage.assistant.model.StockInfo;
import com.da.sage.assistant.utils.DateUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class PnService {

  private final PnMapper pnMapper;
  private final CurrencyService currencyService;

  public String checkPN(@Param("PnRoot") String PnRoot) {
    return pnMapper.checkPN(PnRoot);
  }

  public List<PnOptionPn> findOptionPN(@Param("PnRoot") String PnRoot) {
    return pnMapper.findOptionPn(PnRoot);
  }

  public List<PnRootPn> findPnByStartWith(String cond, Integer Count) {
    List<PnRootPn> listPage = pnMapper.findPnByLike(cond + "%", Count);

    return listPage;
  }

  public List<PnRootPn> findPnByEndWith(String cond, Integer Count) {
    List<PnRootPn> listPage = pnMapper.findPnByLike("%" + cond, Count);

    return listPage;
  }

  public List<PnRootPn> findPnByContains(String cond, Integer Count) {
    List<PnRootPn> listPage = pnMapper.findPnByLike("%" + cond + "%", Count);

    return listPage;
  }

  public List<PnDetails> findAllPnByPnRoot(String PnRoot) {
    return pnMapper.findAllPnByPnRoot(PnRoot);
  }

  public List<PnStatus> findObsoletePnBySite(String Site) {
    return pnMapper.findObsoletePnBySite(Site);
  }

  public List<PnIndustrialization> findIndustrializationByPnRoot(String PnRoot) {
    return pnMapper.findIndustrializationByPnRoot(PnRoot);
  }

  public List<StockInfo> findProductStockInfoByPnRoot(String PnRoot) {
    return pnMapper.findProductStockInfoByPnRoot(PnRoot);
  }

  public List<StockInfo> findComponentStockInfoByPnRoot(String PnRoot) {
    return pnMapper.findComponentStockInfoByPnRoot(PnRoot);
  }

  public List<ProductDeliveryDuration> findProductDeliveryDurationByPnRoot(String PnRoot) {
    return pnMapper.findProductDeliveryDurationByPnRoot(PnRoot);
  }

  public List<ComponentDeliveryDuration> findComponentDeliveryDurationByPnRoot(String PnRoot) {
    return pnMapper.findComponentDeliveryDurationByPnRoot(PnRoot);
  }

  public List<ProductSalesHistory> findProductSalesHistoryByPnRoot(String PnRoot) {
    List<ProductSalesHistory> listPage = (ArrayList<ProductSalesHistory>) pnMapper.findProductSalesHistoryByPnRoot(
        PnRoot);

    for (ProductSalesHistory o : listPage) {
      String key = o.getCurrency() + "USD" + DateUtils.formatDate(o.getOrderDate());
      log.debug("key:" + key);
      try {
        o.setRate(Float.parseFloat(currencyService.getCurrencyRate(key)));
        log.debug("Rate:" + o.getRate());
      } catch (NumberFormatException e) {
        log.error(e.getMessage());
      }
      o.setUSD(o.getNetPrice() * o.getRate());
    }

    return listPage;
  }

  public List<ProductQuoteHistory> findProductQuoteHistoryByPnRoot(String PnRoot) {
    List<ProductQuoteHistory> listPage = pnMapper.findProductQuoteHistoryByPnRoot(PnRoot);

    for (ProductQuoteHistory o : listPage) {
      String key = o.getCurrency() + "USD" + DateUtils.formatDate(o.getQuoteDate());
      log.debug("key:" + key);
      try {
        o.setRate(Float.parseFloat(currencyService.getCurrencyRate(key)));
        log.debug("Rate:" + o.getRate());
      } catch (NumberFormatException e) {
        log.error(e.getMessage());
      }
      o.setUSD(o.getNetPrice() * o.getRate());
    }

    return listPage;
  }

  public List<ProductCostHistory> findProductCostHistoryByPnRoot(String PnRoot) {
    List<ProductCostHistory> listPage = pnMapper.findProductCostHistoryByPnRoot(PnRoot);

    for (ProductCostHistory o : listPage) {
      String key = o.getCurrency() + "USD" + DateUtils.formatDate(o.getOrderDate());
      log.debug("key:" + key);
      try {
        o.setRate(Float.parseFloat(currencyService.getCurrencyRate(key)));
        log.debug("Rate:" + o.getRate());
      } catch (NumberFormatException e) {
        log.error(e.getMessage());
      }
      o.setUSD(o.getNetPrice() * o.getRate());
    }
    // one project maybe purchase line with different currency

    return listPage;
  }

  public List<ComponentPurchaseHistory> findComponentPurchaseHistoryByPnRoot(String PnRoot) {
    List<ComponentPurchaseHistory> listPage = pnMapper.findComponentPurchaseHistoryByPnRoot(PnRoot);

    for (ComponentPurchaseHistory o : listPage) {
      String key = o.getCurrency() + "USD" + DateUtils.formatDate(o.getPurchaseDate());
      log.debug("key:" + key);
      try {
        o.setRate(Float.parseFloat(currencyService.getCurrencyRate(key)));
        log.debug("Rate:" + o.getRate());
      } catch (NumberFormatException e) {
        log.error(e.getMessage());
      }
      o.setUSD(o.getNetPrice() * o.getRate());
    }
    // one project maybe purchase line with different currency

    return listPage;
  }
}
