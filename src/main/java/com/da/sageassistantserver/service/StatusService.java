/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-04-07 15:41:30                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.dao.NoticeMapper;
import com.da.sageassistantserver.dao.StatusMapper;
import com.da.sageassistantserver.model.DeadPurchaseLine;
import com.da.sageassistantserver.model.LineNote;
import com.da.sageassistantserver.model.LongTimeNC;
import com.da.sageassistantserver.model.LongTimeNoQC;
import com.da.sageassistantserver.model.PnStatus;
import com.da.sageassistantserver.model.TobeClosedWO;
import com.da.sageassistantserver.model.TobeDealWithOrderLine;
import com.da.sageassistantserver.model.TobeDelivery;
import com.da.sageassistantserver.model.TobeInvoice;
import com.da.sageassistantserver.model.TobePurchaseBom;
import com.da.sageassistantserver.model.TobeReceive;
import com.da.sageassistantserver.model.TobeTrackingBOMLine;
import com.da.sageassistantserver.model.TobeTrackingNCLine;
import com.da.sageassistantserver.model.TobeTrackingPurchaseOrderLine;
import com.da.sageassistantserver.model.TobeTrackingReceiptLine;
import com.da.sageassistantserver.model.TobeTrackingSalesOrderLine;
import com.da.sageassistantserver.model.WorkActionCnt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusService {

  @Autowired
  StatusMapper statusMapper;

  @Autowired
  PnService pnService;

  @Autowired
  NoticeMapper noticeMapper;

  @Autowired
  LineNoteService lineNoteService;

  @Autowired
  WorkActionService workActionService;

  @Autowired
  SuspectDuplicateDataService suspectDuplicateDataService;

  private List<String> toSiteList(String Site) {
    List<String> Sites = new ArrayList<>();
    if ("CHINA".equals(Site)) {
      Sites.add("HKG");
      Sites.add("ZHU");
      Sites.add("YSH");
    } else {
      Sites.add(Site);
    }
    return Sites;
  }

  public List<TobeDelivery> findTobeDeliveryBySite(String Site) {
    return statusMapper.findTobeDeliveryBySite(Site);
  }

  public List<TobeReceive> findTobeReceiveBySite(String Site) {
    return statusMapper.findTobeReceiveBySite(Site);
  }

  public List<TobeDealWithOrderLine> findTobeDealWithOrderLineBySite(
                                                                     String Site) {
    return statusMapper.findTobeDealWithOrderLineBySite(Site);
  }

  public List<TobePurchaseBom> findTobePurchaseBomBySite(String Site) {
    return statusMapper.findTobePurchaseBomBySite(Site);
  }

  public List<PnStatus> findNotActivePNBySite(String Site) {
    return pnService.findObsoletePnBySite(Site);
  }

  public List<TobeReceive> findNoAckDatePOBySite(String Site) {
    List<TobeReceive> listPage = new ArrayList<>();
    if ("CHINA".equals(Site)) {
      listPage = noticeMapper.findNoAckDatePO("HKG");
      listPage.addAll(noticeMapper.findNoAckDatePO("ZHU"));
      listPage.addAll(noticeMapper.findNoAckDatePO("YSH"));
      listPage.sort(Comparator.comparing(TobeReceive::getExpectDate));
    } else {
      listPage = noticeMapper.findNoAckDatePO(Site);
    }

    return listPage;
  }

  public List<LongTimeNC> findLongTimeNCBySite(String Site) {
    List<LongTimeNC> listPage = new ArrayList<>();
    if ("CHINA".equals(Site)) {
      listPage = noticeMapper.findLongTimeNC("HKG", -9999, -14);
      listPage.addAll(noticeMapper.findLongTimeNC("ZHU", -9999, -14));
      listPage.addAll(noticeMapper.findLongTimeNC("YSH", -9999, -14));
      listPage.sort(Comparator.comparing(LongTimeNC::getFirstNCDate));
    } else {
      listPage = noticeMapper.findLongTimeNC(Site, -9999, -14);
    }

    return listPage;
  }

  public List<LongTimeNoQC> findLongTimeNoQCBySite(String Site) {
    List<LongTimeNoQC> listPage = new ArrayList<>();
    if ("CHINA".equals(Site)) {
      listPage = noticeMapper.findLongTimeNoQC("HKG", -9999, -14);
      listPage.addAll(noticeMapper.findLongTimeNoQC("ZHU", -9999, -14));
      listPage.addAll(noticeMapper.findLongTimeNoQC("YSH", -9999, -14));
      listPage.sort(Comparator.comparing(LongTimeNoQC::getReceiptDate));
    } else {
      listPage = noticeMapper.findLongTimeNoQC(Site, -9999, -14);
    }

    return listPage;
  }

  public List<TobeClosedWO> findTobeClosedWOBySite(String Site) {
    return statusMapper.findTobeClosedWOBySite(Site);
  }

  public List<TobeReceive> findWrongProjectPOBySite(String Site) {
    List<TobeReceive> listPage = new ArrayList<>();
    if ("CHINA".equals(Site)) {
      listPage = noticeMapper.findWrongProjectPO("HKG");
      listPage.addAll(noticeMapper.findWrongProjectPO("ZHU"));
      listPage.addAll(noticeMapper.findWrongProjectPO("YSH"));
      listPage.sort(Comparator.comparing(TobeReceive::getOrderDate));
    } else {
      listPage = noticeMapper.findWrongProjectPO(Site);
    }

    return listPage;
  }

  public List<TobeInvoice> findNoInvoicePOBySite(String Site) {
    return statusMapper.findNoInvoicePOBySite(Site);
  }

  public List<DeadPurchaseLine> findDeadPurchaseLineBySite(String Site) {
    return statusMapper.findDeadPurchaseLineBySite(Site);
  }

  public Integer findTobeTrackingSalesOrderLineCnt(
                                                   String Site,
                                                   String OrderType,
                                                   String CustomerCode,
                                                   String VendorCode,
                                                   String ProjectNO) {

    return statusMapper.findTobeTrackingSalesOrderLineCntBySite(
                                                                Site,
                                                                OrderType,
                                                                CustomerCode,
                                                                VendorCode,
                                                                ProjectNO);
  }

  public List<TobeTrackingSalesOrderLine> findTobeTrackingSalesOrderLine(
                                                                         String Site,
                                                                         String OrderType,
                                                                         String CustomerCode,
                                                                         String VendorCode,
                                                                         String ProjectNO,
                                                                         String OrderBy,
                                                                         String Descending,
                                                                         Integer Offset,
                                                                         Integer Limit) {
    List<TobeTrackingSalesOrderLine> listPage = statusMapper.findTobeTrackingSalesOrderLineBySite(
                                                                                                  Site,
                                                                                                  OrderType,
                                                                                                  CustomerCode,
                                                                                                  VendorCode,
                                                                                                  ProjectNO,
                                                                                                  OrderBy,
                                                                                                  Descending,
                                                                                                  Offset,
                                                                                                  Limit);
    List<TobeTrackingNCLine>         ncList   = statusMapper.findTobeTrackingNCLineBySite(
                                                                                          Site,
                                                                                          OrderType,
                                                                                          CustomerCode,
                                                                                          VendorCode,
                                                                                          ProjectNO,
                                                                                          OrderBy,
                                                                                          Descending,
                                                                                          Offset,
                                                                                          Limit);

    JSONArray lineArray    = new JSONArray();
    JSONArray projectArray = new JSONArray();
    for (TobeTrackingSalesOrderLine so : listPage) {
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

    List<LineNote>      lineNotes      = lineNoteService.getLineNoteByLineJsonList(
                                                                                   lineArray.toJSONString());
    List<WorkActionCnt> workActionCnts = workActionService.getWorkActionCntByProjectJsonList(
                                                                                             projectArray.toJSONString(),
                                                                                             Site);

    for (TobeTrackingSalesOrderLine so : listPage) {
      // set NC
      List<TobeTrackingNCLine> NC = new ArrayList<>();
      for (TobeTrackingNCLine nc : ncList) {
        if (!nc.getSite().equals(so.getSite())) {
          continue;
        }
        if ((nc.getClaimProjectNO().equals(so.getTrackingNO()) ||
             nc.getClaimProjectNO().equals(so.getProjectNO()))) {
          NC.add(nc);
        }
      }
      so.setNC(NC);

      // set note
      so.setAssignToLineNote(new LineNote());
      so.setStatusLineNote(new LineNote());
      for (LineNote ln : lineNotes) {
        if ((so.getOrderNO() + '-' + so.getOrderLine()).equals(ln.getLine()) &&
            "ASSIGN".equals(ln.getNote_type())) {
          // only set lasted note
          so.setAssignToLineNote(ln);
          continue;
        }
        if ((so.getOrderNO() + '-' + so.getOrderLine()).equals(ln.getLine()) &&
            "STATUS".equals(ln.getNote_type())) {
          // only set lasted note
          so.setStatusLineNote(ln);
          continue;
        }
      }

      // set action summary
      so.setTQCCntPass(0);
      so.setTQCCntNC(0);
      so.setIQCCntPass(0);
      so.setIQCCntNC(0);
      so.setFQCCntPass(0);
      so.setFQCCntNC(0);
      so.setPCKCntPass(0);
      so.setPCKCntNC(0);
      for (WorkActionCnt cnt : workActionCnts) {
        // here ignore site, to show friendly to Internal order status
        if (so.getTrackingNO().equals(cnt.getProject()) ||
            so.getProjectNO().equals(cnt.getProject())) {
          if (cnt.getAct().equals("TQC") && cnt.getResult().equals("PASS")) {
            so.setTQCCntPass(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("TQC") && cnt.getResult().equals("NC")) {
            so.setTQCCntNC(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("IQC") && cnt.getResult().equals("PASS")) {
            so.setTQCCntPass(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("IQC") && cnt.getResult().equals("NC")) {
            so.setTQCCntNC(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("FQC") && cnt.getResult().equals("PASS")) {
            so.setTQCCntPass(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("FQC") && cnt.getResult().equals("NC")) {
            so.setTQCCntNC(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("PCK") && cnt.getResult().equals("PASS")) {
            so.setTQCCntPass(cnt.getQty());
            continue;
          }
          if (cnt.getAct().equals("PCK") && cnt.getResult().equals("NC")) {
            so.setTQCCntNC(cnt.getQty());
            continue;
          }
        }
      }
    }

    return listPage;
  }

  public List<TobeTrackingBOMLine> findTobeTrackingBOMLine(
                                                           String Site,
                                                           String OrderType,
                                                           String CustomerCode,
                                                           String VendorCode,
                                                           String ProjectNO,
                                                           String OrderBy,
                                                           String Descending,
                                                           Integer Offset,
                                                           Integer Limit) {
    List<TobeTrackingBOMLine> listPage = statusMapper.findTobeTrackingBOMLineBySite(
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
    for (TobeTrackingBOMLine bom : listPage) {
      JSONObject obj = new JSONObject();
      obj.put("line", bom.getWorkOrderNO() + '-' + bom.getBomLine());
      array.add(obj);
    }

    List<LineNote> lineNotes = lineNoteService.getLineNoteByLineJsonList(
                                                                         array.toJSONString());

    for (TobeTrackingBOMLine bom : listPage) {
      bom.setLineNote(new LineNote());
      for (LineNote ln : lineNotes) {
        // only last note
        if ((bom.getWorkOrderNO() + '-'
             + bom.getBomLine()).equals(ln.getLine())) {
          bom.setLineNote(ln);
        }
      }
    }
    return listPage;
  }

  public List<TobeTrackingPurchaseOrderLine> findTobeTrackingPurchaseOrderLine(
                                                                               String Site,
                                                                               String OrderType,
                                                                               String CustomerCode,
                                                                               String VendorCode,
                                                                               String ProjectNO,
                                                                               String OrderBy,
                                                                               String Descending,
                                                                               Integer Offset,
                                                                               Integer Limit) {
    List<TobeTrackingPurchaseOrderLine> listPage = statusMapper.findTobeTrackingPurchaseOrderLineBySite(
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
    for (TobeTrackingPurchaseOrderLine po : listPage) {
      JSONObject obj = new JSONObject();
      obj.put("line", po.getPurchaseNO() + '-' + po.getPurchaseLine());
      array.add(obj);
    }

    List<LineNote> lineNotes = lineNoteService.getLineNoteByLineJsonList(
                                                                         array.toJSONString());

    for (TobeTrackingPurchaseOrderLine po : listPage) {
      po.setLineNote(new LineNote());
      for (LineNote ln : lineNotes) {
        // only last note
        if ((po.getPurchaseNO() + '-'
             + po.getPurchaseLine()).equals(ln.getLine())) {
          po.setLineNote(ln);
        }
      }
    }
    return listPage;
  }

  public List<TobeTrackingReceiptLine> findTobeTrackingReceiptLine(
                                                                   String Site,
                                                                   String OrderType,
                                                                   String CustomerCode,
                                                                   String VendorCode,
                                                                   String ProjectNO,
                                                                   String OrderBy,
                                                                   String Descending,
                                                                   Integer Offset,
                                                                   Integer Limit) {
    List<TobeTrackingReceiptLine> listPage = statusMapper.findTobeTrackingReceiptLineBySite(
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

  public List<TobeTrackingNCLine> findTobeTrackingNCLine(
                                                         String Site,
                                                         String OrderType,
                                                         String CustomerCode,
                                                         String VendorCode,
                                                         String ProjectNO,
                                                         String OrderBy,
                                                         String Descending,
                                                         Integer Offset,
                                                         Integer Limit) {
    List<TobeTrackingNCLine> listPage = statusMapper.findTobeTrackingNCLineBySite(
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
