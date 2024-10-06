/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 19:16:32                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.dao.TrackingMapper;
import com.da.sage.assistant.model.TrackingBOMLine;
import com.da.sage.assistant.model.TrackingNCLine;
import com.da.sage.assistant.model.TrackingPurchaseOrderLine;
import com.da.sage.assistant.model.TrackingReceiptLine;
import com.da.sage.assistant.model.TrackingSalesOrderLine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {

  private final TrackingMapper trackingMapper;

  public Integer findTrackingSalesOrderLineCnt(
      String Site,
      String OrderType,
      String CustomerCode,
      String VendorCode,
      String ProjectNO) {

    return trackingMapper.findTrackingSalesOrderLineCntBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO);
  }

  public List<TrackingSalesOrderLine> findTrackingSalesOrderLine(
      String Site,
      String OrderType,
      String CustomerCode,
      String VendorCode,
      String ProjectNO,
      String OrderBy,
      String Descending,
      Integer Offset,
      Integer Limit) {
    List<TrackingSalesOrderLine> listPage = trackingMapper.findTrackingSalesOrderLineBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
    List<TrackingNCLine> ncList = trackingMapper.findTrackingNCLineBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);

    JSONArray lineArray = new JSONArray();
    JSONArray projectArray = new JSONArray();
    for (TrackingSalesOrderLine so : listPage) {
      JSONObject obj = new JSONObject();
      obj.put("line", so.getOrderNO() + '-' + so.getOrderLine());
      lineArray.add(obj);

      JSONObject obj2 = new JSONObject();
      obj2.put("project", so.getProjectNO());
      projectArray.add(obj2);

      JSONObject obj3 = new JSONObject();
      obj3.put("project", so.getTrackingNO());
      projectArray.add(obj3);
    }

    for (TrackingSalesOrderLine so : listPage) {
      // set NC
      List<TrackingNCLine> NC = new ArrayList<>();
      for (TrackingNCLine nc : ncList) {
        if (!nc.getSite().equals(so.getSite())) {
          continue;
        }
        if ((nc.getClaimProjectNO().equals(so.getTrackingNO()) ||
            nc.getClaimProjectNO().equals(so.getProjectNO()))) {
          NC.add(nc);
        }
      }
      so.setNC(NC);

      // set action summary
      so.setTQCCntPass(0);
      so.setTQCCntNC(0);
      so.setIQCCntPass(0);
      so.setIQCCntNC(0);
      so.setFQCCntPass(0);
      so.setFQCCntNC(0);
      so.setPCKCntPass(0);
      so.setPCKCntNC(0);

    }

    return listPage;
  }

  public List<TrackingBOMLine> findTrackingBOMLine(
      String Site,
      String OrderType,
      String CustomerCode,
      String VendorCode,
      String ProjectNO,
      String OrderBy,
      String Descending,
      Integer Offset,
      Integer Limit) {
    List<TrackingBOMLine> listPage = trackingMapper.findTrackingBOMLineBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);

    JSONArray array = new JSONArray();
    for (TrackingBOMLine bom : listPage) {
      JSONObject obj = new JSONObject();
      obj.put("line", bom.getWorkOrderNO() + '-' + bom.getBomLine());
      array.add(obj);
    }

    return listPage;
  }

  public List<TrackingPurchaseOrderLine> findTrackingPurchaseOrderLine(
      String Site,
      String OrderType,
      String CustomerCode,
      String VendorCode,
      String ProjectNO,
      String OrderBy,
      String Descending,
      Integer Offset,
      Integer Limit) {
    List<TrackingPurchaseOrderLine> listPage = trackingMapper.findTrackingPurchaseOrderLineBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);
    return listPage;
  }

  public List<TrackingReceiptLine> findTrackingReceiptLine(
      String Site,
      String OrderType,
      String CustomerCode,
      String VendorCode,
      String ProjectNO,
      String OrderBy,
      String Descending,
      Integer Offset,
      Integer Limit) {
    List<TrackingReceiptLine> listPage = trackingMapper.findTrackingReceiptLineBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);

    return listPage;
  }

  public List<TrackingNCLine> findTrackingNCLine(
      String Site,
      String OrderType,
      String CustomerCode,
      String VendorCode,
      String ProjectNO,
      String OrderBy,
      String Descending,
      Integer Offset,
      Integer Limit) {
    List<TrackingNCLine> listPage = trackingMapper.findTrackingNCLineBySite(
        Site,
        OrderType,
        CustomerCode,
        VendorCode,
        ProjectNO,
        OrderBy,
        Descending,
        Offset,
        Limit);

    return listPage;
  }
}
