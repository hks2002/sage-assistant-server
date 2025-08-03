/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditDate          : 2025-08-07 00:15:33                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.BusinessPartnerName;
import com.da.sage.assistant.model.SupplierDOD;
import com.da.sage.assistant.model.SupplierDetails;
import com.da.sage.assistant.model.SupplierName;
import com.da.sage.assistant.model.SupplierOTD;
import com.da.sage.assistant.model.SupplierOTDTop;
import com.da.sage.assistant.model.SupplierOrder;
import com.da.sage.assistant.model.SupplierSummaryAmountByTarget;
import com.da.sage.assistant.model.SupplierSummaryAmountTop;

@Mapper
public interface SupplierMapper {
  List<SupplierName> findSupplierByCodeOrName(
      @Param("SupplierCodeOrName") String SupplierCodeOrName,
      @Param("Count") Integer Count);

  List<BusinessPartnerName> findBusinessPartnerByCode(
      @Param("BPCode") String BPCode);

  List<SupplierDetails> findSupplierDetailsByCode(
      @Param("SupplierCode") String SupplierCode);

  List<SupplierSummaryAmountByTarget> findSupplierSumAmount(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("SupplierCode") String SupplierCode,
      @Param("OrderType") String OrderType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<SupplierSummaryAmountTop> findSupplierSumAmountTop(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("OrderType") String OrderType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Limit") Integer Limit);

  List<SupplierOTD> findSupplierOTD(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("SupplierCode") String SupplierCode,
      @Param("OrderType") String OrderType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Interval") String Interval);

  List<SupplierOTDTop> findSupplierOTDTop(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("OrderType") String OrderType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("Sort") String Sort,
      @Param("Limit") Integer Limit);

  List<SupplierDOD> findSupplierDOD(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("SupplierCode") String SupplierCode,
      @Param("OrderType") String OrderType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo);

  Integer findSupplierOrdersCnt(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("SupplierCode") String SupplierCode,
      @Param("OrderType") String OrderType,
      @Param("DateType") String DateType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("OrderStatus") String OrderStatus);

  List<SupplierOrder> findSupplierOrders(
      @Param("Site") String Site,
      @Param("SupplierType") String SupplierType,
      @Param("SupplierCode") String SupplierCode,
      @Param("OrderType") String OrderType,
      @Param("DateType") String DateType,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("OrderStatus") String OrderStatus,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);
}
