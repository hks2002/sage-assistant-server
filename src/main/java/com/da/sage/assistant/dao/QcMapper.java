/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditDate          : 2025-07-31 14:17:45                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.QcNCHistory;
import com.da.sage.assistant.model.QcNCSummary;
import com.da.sage.assistant.model.QcQPY;
import com.da.sage.assistant.model.QcQPYTop;

@Mapper
public interface QcMapper {

  List<QcQPY> findQcQPY(
      @Param("Site") String Site,
      @Param("SupplierCode") String SupplierCode,
      @Param("CustomerCode") String CustomerCode,
      @Param("QcType") String QcType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<QcQPYTop> findQcQPYTop(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("OrderType") String OrderType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit,
      @Param("Sort") String Sort);

  List<QcNCSummary> findQcNCSummary(
      @Param("Site") String Site,
      @Param("SupplierCode") String SupplierCode,
      @Param("NCCatCode") String NCCatCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  Integer findQcNCHistoryCnt(
      @Param("Site") String Site,
      @Param("SupplierCode") String SupplierCode,
      @Param("NCCatCode") String NCCatCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo);

  List<QcNCHistory> findQcNCHistory(
      @Param("Site") String Site,
      @Param("SupplierCode") String SupplierCode,
      @Param("NCCatCode") String NCCatCode,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);
}
