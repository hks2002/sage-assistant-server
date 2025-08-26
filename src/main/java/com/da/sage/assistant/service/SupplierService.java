/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                      *
 * @LastEditDate          : 2025-08-22 14:35:55                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/


package com.da.sage.assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.SupplierMapper;
import com.da.sage.assistant.model.BusinessPartnerName;
import com.da.sage.assistant.model.SupplierDOD;
import com.da.sage.assistant.model.SupplierDetails;
import com.da.sage.assistant.model.SupplierName;
import com.da.sage.assistant.model.SupplierOTD;
import com.da.sage.assistant.model.SupplierOTDTop;
import com.da.sage.assistant.model.SupplierOrder;
import com.da.sage.assistant.model.SupplierSummaryAmountByTarget;
import com.da.sage.assistant.model.SupplierSummaryAmountTop;
import com.da.sage.assistant.model.SupplierSummaryCountByTarget;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SupplierService {

  private final SupplierMapper supplierMapper;

  public List<SupplierName> getSupplierByCodeOrName(
      String cond,
      Integer Count) {
    List<SupplierName> listPage = supplierMapper.findSupplierByCodeOrName(
        "%" + cond + "%",
        Count);

    return listPage;
  }

  public String getBusinessPartnerByCode(String code) {
    List<BusinessPartnerName> listPage = supplierMapper.findBusinessPartnerByCode(
        code);
    if (listPage.size() == 0) {
      return "";
    } else {
      return listPage.get(0).getBPName();
    }
  }

  public List<SupplierDetails> getSupplierDetails(String SupplierCode) {
    return supplierMapper.findSupplierDetailsByCode(SupplierCode);
  }

  public List<SupplierSummaryAmountByTarget> getSupplierSummaryAmount(
      String Site,
      String SupplierType,
      String SupplierCode,
      String OrderType,
      String DateFrom,
      String DateTo,
      String Interval) {
    return supplierMapper.findSupplierSumAmount(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<SupplierSummaryCountByTarget> getSupplierSummaryCount(
      String Site,
      String SupplierType,
      String SupplierCode,
      String OrderType,
      String DateFrom,
      String DateTo,
      String Interval) {
    return supplierMapper.findSupplierSumCount(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<SupplierSummaryAmountTop> getSupplierSummaryAmountTop(
      String Site,
      String SupplierType,
      String OrderType,
      String DateFrom,
      String DateTo,
      Integer Limit) {
    return supplierMapper.findSupplierSumAmountTop(
        Site,
        SupplierType,
        OrderType,
        DateFrom,
        DateTo,
        Limit);
  }

  public List<SupplierOTD> getSupplierOTD(
      String Site,
      String SupplierType,
      String SupplierCode,
      String OrderType,
      String DateFrom,
      String DateTo,
      String Interval) {
    return supplierMapper.findSupplierOTD(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo,
        Interval);
  }

  public List<SupplierOTDTop> getSupplierOTDTop(
      String Site,
      String SupplierType,
      String OrderType,
      String DateFrom,
      String DateTo,
      String Sort,
      Integer Limit) {
    return supplierMapper.findSupplierOTDTop(
        Site,
        SupplierType,
        OrderType,
        DateFrom,
        DateTo,
        Sort,
        Limit);
  }

  public List<SupplierDOD> getSupplierDOD(
      String Site,
      String SupplierType,
      String SupplierCode,
      String OrderType,
      String DateFrom,
      String DateTo) {
    return supplierMapper.findSupplierDOD(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateFrom,
        DateTo);
  }

  public Integer getSupplierOrdersCnt(
      String Site,
      String SupplierType,
      String SupplierCode,
      String OrderType,
      String DateType,
      String DateFrom,
      String DateTo,
      String OrderStatus) {
    return supplierMapper.findSupplierOrdersCnt(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateType,
        DateFrom,
        DateTo,
        OrderStatus);
  }

  public List<SupplierOrder> getSupplierOrders(
      String Site,
      String SupplierType,
      String SupplierCode,
      String OrderType,
      String DateType,
      String DateFrom,
      String DateTo,
      String OrderStatus,
      Integer Offset,
      Integer Limit) {
    return supplierMapper.findSupplierOrders(
        Site,
        SupplierType,
        SupplierCode,
        OrderType,
        DateType,
        DateFrom,
        DateTo,
        OrderStatus,
        Offset,
        Limit);
  }
}
