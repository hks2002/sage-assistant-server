/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                       *
 * @LastEditDate          : 2025-07-25 14:23:07                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

@Mapper
public interface PnMapper {
  String checkPN(@Param("PnRoot") String PnRoot);

  List<PnOptionPn> findOptionPn(@Param("PnRoot") String PnRoot);

  List<PnRootPn> findPnByLike(@Param("PnRoot") String PnRoot, @Param("Count") Integer Count);

  List<PnDetails> findAllPnByPnRoot(@Param("PnRoot") String PnRoot);

  List<PnIndustrialization> findIndustrializationByPnRoot(@Param("PnRoot") String PnRoot);

  List<PnStatus> findObsoletePnBySite(@Param("Site") String Site);

  List<StockInfo> findProductStockInfoByPnRoot(@Param("PnRoot") String PnRoot);

  List<ProductSalesHistory> findProductSalesHistoryByPnRoot(@Param("PnRoot") String PnRoot);

  List<ProductQuoteHistory> findProductQuoteHistoryByPnRoot(@Param("PnRoot") String PnRoot);

  List<ProductCostHistory> findProductCostHistoryByPnRoot(@Param("PnRoot") String PnRoot);

  List<ProductDeliveryDuration> findProductDeliveryDurationByPnRoot(@Param("PnRoot") String PnRoot);

  List<StockInfo> findComponentStockInfoByPnRoot(@Param("PnRoot") String PnRoot);

  List<ComponentDeliveryDuration> findComponentDeliveryDurationByPnRoot(@Param("PnRoot") String PnRoot);

  List<ComponentPurchaseHistory> findComponentPurchaseHistoryByPnRoot(@Param("PnRoot") String PnRoot);
}
