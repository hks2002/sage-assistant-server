/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                       *
 * @LastEditDate          : 2025-07-27 10:27:29                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.BatchPurchase;
import com.da.sage.assistant.model.BatchQuote;
import com.da.sage.assistant.model.BatchSales;

@Mapper
public interface BatchMapper {
  List<BatchPurchase> getPurchase(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot,
      @Param("LastN") String LastN);

  List<BatchQuote> getQuote(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot,
      @Param("LastN") String LastN);

  List<BatchSales> getSales(
      @Param("Site") String Site,
      @Param("PnRoot") String PnRoot,
      @Param("LastN") String LastN);
}
