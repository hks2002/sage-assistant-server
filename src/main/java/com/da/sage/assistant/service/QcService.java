/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                      *
 * @LastEditDate          : 2025-07-31 14:17:17                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.QcMapper;
import com.da.sage.assistant.model.QcNCHistory;
import com.da.sage.assistant.model.QcNCHistoryTiny;
import com.da.sage.assistant.model.QcNCSummary;
import com.da.sage.assistant.model.QcQPY;
import com.da.sage.assistant.model.QcQPYTop;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class QcService {

  private final QcMapper qcMapper;

  public List<QcQPY> getQcQPY(
      String Site,
      String SupplierCode,
      String CustomerCode,
      String QcType,
      String DateFrom,
      String DateTo,
      String Interval) {
    return qcMapper.findQcQPY(
        Site,
        SupplierCode,
        CustomerCode,
        QcType,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<QcQPYTop> getQcQPYTop(
      String Site,
      String SupplierType,
      String OrderType,
      String DateFrom,
      String DateTo,
      Integer Limit,
      String Sort) {
    return qcMapper.findQcQPYTop(
        Site,
        SupplierType,
        OrderType,
        DateFrom,
        DateTo,
        Limit,
        Sort);
  }

  public List<QcNCSummary> getQcNCSummary(
      String Site,
      String SupplierCode,
      String NCCatCode,
      String DateFrom,
      String DateTo,
      String Interval) {
    return qcMapper.findQcNCSummary(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo,
        Interval);
  }

  public Integer getQcNCHistoryCnt(
      String Site,
      String SupplierCode,
      String NCCatCode,
      String DateFrom,
      String DateTo) {
    return qcMapper.findQcNCHistoryCnt(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo);
  }

  public List<QcNCHistory> getQcNCHistory(
      String Site,
      String SupplierCode,
      String NCCatCode,
      String DateFrom,
      String DateTo,
      Integer Offset,
      Integer Limit) {
    return qcMapper.findQcNCHistory(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo,
        Offset,
        Limit);
  }

  public List<QcNCHistoryTiny> getQcNCHistoryTiny(
      String Site,
      String SupplierCode,
      String NCCatCode,
      String DateFrom,
      String DateTo,
      Integer Offset,
      Integer Limit) {
    List<QcNCHistory> listRaw = qcMapper.findQcNCHistory(
        Site,
        SupplierCode,
        NCCatCode,
        DateFrom,
        DateTo,
        Offset,
        Limit);
    List<QcNCHistoryTiny> list = new ArrayList<>();

    for (QcNCHistory raw : listRaw) {
      if (raw.getNCCAT0() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(1);
        item.setDate(raw.getNCDAT0());
        item.setCat(raw.getNCCAT0());
        item.setType(raw.getNCTYP0());
        item.setCri(raw.getNCCRIT0());
        item.setDes(raw.getNCDES0());
        item.setCreatedBy(raw.getCreatedBy0());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT1() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(2);
        item.setDate(raw.getNCDAT1());
        item.setCat(raw.getNCCAT1());
        item.setType(raw.getNCTYP1());
        item.setCri(raw.getNCCRIT1());
        item.setDes(raw.getNCDES1());
        item.setCreatedBy(raw.getCreatedBy1());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT2() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(3);
        item.setDate(raw.getNCDAT2());
        item.setCat(raw.getNCCAT2());
        item.setType(raw.getNCTYP2());
        item.setCri(raw.getNCCRIT2());
        item.setDes(raw.getNCDES2());
        item.setCreatedBy(raw.getCreatedBy2());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT3() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(4);
        item.setDate(raw.getNCDAT3());
        item.setCat(raw.getNCCAT3());
        item.setType(raw.getNCTYP3());
        item.setCri(raw.getNCCRIT3());
        item.setDes(raw.getNCDES3());
        item.setCreatedBy(raw.getCreatedBy3());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT4() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(5);
        item.setDate(raw.getNCDAT4());
        item.setCat(raw.getNCCAT4());
        item.setType(raw.getNCTYP4());
        item.setCri(raw.getNCCRIT4());
        item.setDes(raw.getNCDES4());
        item.setCreatedBy(raw.getCreatedBy4());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT5() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(6);
        item.setDate(raw.getNCDAT5());
        item.setCat(raw.getNCCAT5());
        item.setType(raw.getNCTYP5());
        item.setCri(raw.getNCCRIT5());
        item.setDes(raw.getNCDES5());
        item.setCreatedBy(raw.getCreatedBy5());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT6() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(7);
        item.setDate(raw.getNCDAT6());
        item.setCat(raw.getNCCAT6());
        item.setType(raw.getNCTYP6());
        item.setCri(raw.getNCCRIT6());
        item.setDes(raw.getNCDES6());
        item.setCreatedBy(raw.getCreatedBy6());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT7() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(8);
        item.setDate(raw.getNCDAT7());
        item.setCat(raw.getNCCAT7());
        item.setType(raw.getNCTYP7());
        item.setCri(raw.getNCCRIT7());
        item.setDes(raw.getNCDES7());
        item.setCreatedBy(raw.getCreatedBy7());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT8() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(9);
        item.setDate(raw.getNCDAT8());
        item.setCat(raw.getNCCAT8());
        item.setType(raw.getNCTYP8());
        item.setCri(raw.getNCCRIT8());
        item.setDes(raw.getNCDES8());
        item.setCreatedBy(raw.getCreatedBy8());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT9() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(10);
        item.setDate(raw.getNCDAT9());
        item.setCat(raw.getNCCAT9());
        item.setType(raw.getNCTYP9());
        item.setCri(raw.getNCCRIT9());
        item.setDes(raw.getNCDES9());
        item.setCreatedBy(raw.getCreatedBy9());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT10() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(11);
        item.setDate(raw.getNCDAT10());
        item.setCat(raw.getNCCAT10());
        item.setType(raw.getNCTYP10());
        item.setCri(raw.getNCCRIT10());
        item.setDes(raw.getNCDES10());
        item.setCreatedBy(raw.getCreatedBy10());
        list.add(item);
      } else {
        continue;
      }
      if (raw.getNCCAT11() != null) {
        QcNCHistoryTiny item = new QcNCHistoryTiny();
        item.setSite(raw.getSite());
        item.setSupplierCode(raw.getSupplierCode());
        item.setNCNo(raw.getNCNo());
        item.setProjectCode(raw.getProjectNO());
        item.setReq(12);
        item.setDate(raw.getNCDAT11());
        item.setCat(raw.getNCCAT11());
        item.setType(raw.getNCTYP11());
        item.setCri(raw.getNCCRIT11());
        item.setDes(raw.getNCDES11());
        item.setCreatedBy(raw.getCreatedBy11());
        list.add(item);
      } else {
        continue;
      }
    }

    return list;
  }
}
