/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-22 13:48:09                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.StockMapper;
import com.da.sage.assistant.model.StockHistory;
import com.da.sage.assistant.model.StockSummary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

  private final StockMapper stockMapper;

  public String getStockQty(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot) {
    if (stockMapper.findStockQty(Site, PnRoot) == null) {
      return "0";
    } else {
      return stockMapper.findStockQty(Site, PnRoot);
    }
  }

  public List<StockSummary> getStockSummary(@Param("Site") String Site) {
    return stockMapper.findStockSummaryBySite(Site);
  }

  public List<StockHistory> getStockHistory(
      @Param("Site") String Site,
      @Param("PnOrName") String PnOrName,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo) {
    return stockMapper.findStockHistoryBySite(Site, PnOrName, DateFrom, DateTo);
  }
}
