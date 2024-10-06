/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditDate          : 2025-07-27 18:41:51                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.TrackingBOMLine;
import com.da.sage.assistant.model.TrackingNCLine;
import com.da.sage.assistant.model.TrackingPurchaseOrderLine;
import com.da.sage.assistant.model.TrackingReceiptLine;
import com.da.sage.assistant.model.TrackingSalesOrderLine;

@Mapper
public interface TrackingMapper {
  Integer findTrackingSalesOrderLineCntBySite(
      @Param("Site") String Sites,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("ProjectNO") String ProjectNO);

  List<TrackingSalesOrderLine> findTrackingSalesOrderLineBySite(
      @Param("Site") String Sites,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("ProjectNO") String ProjectNO,
      @Param("OrderBy") String OrderBy,
      @Param("Descending") String Descending,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<TrackingBOMLine> findTrackingBOMLineBySite(
      @Param("Site") String Sites,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("ProjectNO") String ProjectNO,
      @Param("OrderBy") String OrderBy,
      @Param("Descending") String Descending,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<TrackingPurchaseOrderLine> findTrackingPurchaseOrderLineBySite(
      @Param("Site") String Sites,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("ProjectNO") String ProjectNO,
      @Param("OrderBy") String OrderBy,
      @Param("Descending") String Descending,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<TrackingReceiptLine> findTrackingReceiptLineBySite(
      @Param("Site") String Sites,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("ProjectNO") String ProjectNO,
      @Param("OrderBy") String OrderBy,
      @Param("Descending") String Descending,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<TrackingNCLine> findTrackingNCLineBySite(
      @Param("Site") String Sites,
      @Param("OrderType") String OrderType,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("ProjectNO") String ProjectNO,
      @Param("OrderBy") String OrderBy,
      @Param("Descending") String Descending,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);
}
