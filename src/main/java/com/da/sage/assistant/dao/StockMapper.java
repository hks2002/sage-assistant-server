/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                       *
 * @LastEditDate          : 2025-07-22 13:40:02                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.StockHistory;
import com.da.sage.assistant.model.StockSummary;

@Mapper
public interface StockMapper {
  String findStockQty(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot);

  List<StockSummary> findStockSummaryBySite(
      @Param("Site") String Site);

  List<StockHistory> findStockHistoryBySite(
      @Param("Site") String Site,
      @Param("PnOrName") String PnOrName,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo);
}
