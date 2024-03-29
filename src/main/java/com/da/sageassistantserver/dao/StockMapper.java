/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 19:32:00                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/dao/StockMapper.java                                 *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sageassistantserver.model.StockHistory;
import com.da.sageassistantserver.model.StockInfo;
import com.da.sageassistantserver.model.StockSummary;

@Mapper
public interface StockMapper {
    String checkPN(@Param("pnRoot") String pnRoot);

    String findStockOptionPN(@Param("pnRoot") String pnRoot);

    String findStockQty(@Param("Site") String Site, @Param("pnRoot") String pnRoot);

    List<StockInfo> findStockInfoByPnRoot(@Param("pnRoot") String pnRoot);

    List<StockSummary> findStockSummaryBySite(@Param("Site") String Site);

    List<StockHistory> findStockHistoryBySite(
        @Param("Site") String Site,
        @Param("PnOrName") String PnOrName,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );
}
