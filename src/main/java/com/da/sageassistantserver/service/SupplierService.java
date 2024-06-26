/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2022-03-26 17:57:00                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-06-24 15:03:23                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.service;

import com.da.sageassistantserver.dao.SupplierMapper;
import com.da.sageassistantserver.model.BusinessPartnerName;
import com.da.sageassistantserver.model.SupplierDelayHistory;
import com.da.sageassistantserver.model.SupplierDeliveryHistory;
import com.da.sageassistantserver.model.SupplierDetails;
import com.da.sageassistantserver.model.SupplierName;
import com.da.sageassistantserver.model.SupplierOpenItems;
import com.da.sageassistantserver.model.SupplierSummaryAmount;
import com.da.sageassistantserver.model.SupplierSummaryQty;
import com.da.sageassistantserver.utils.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SupplierService {

  @Autowired private SupplierMapper supplierMapper;

  @Autowired private CurrencyService currencyService;

  public List<SupplierName> getSupplierByCodeOrName(String cond, Integer Count) {
    List<SupplierName> listPage = supplierMapper.findSupplierByCodeOrName("%" + cond + "%", Count);

    return listPage;
  }

  public List<BusinessPartnerName> getBusinessPartnerByCode(String code) {
    List<BusinessPartnerName> listPage = supplierMapper.findBusinessPartnerByCode(code);
    return listPage;
  }

  public List<SupplierSummaryAmount> getSupplierTotalAmount(String SupplierCode, String DateFrom, String DateTo) {
    List<SupplierSummaryAmount> listPage = supplierMapper.findSupplierTotalAmount(SupplierCode, DateFrom, DateTo);

    for (SupplierSummaryAmount o : listPage) {
      String key = o.getCurrency() + "USD" + Utils.formatDate(new Date());
      log.debug("key:" + key);
      try {
        o.setRate(Float.parseFloat(currencyService.cache.get(key)));
        log.debug("Rate:" + o.getRate());
      } catch (NumberFormatException e) {
        log.error(e.getMessage());
      }
      o.setUSD(o.getAmount().multiply(new BigDecimal(o.getRate())));
    }

    return listPage;
  }

  public List<SupplierSummaryQty> getSupplierTotalProjectQty(@Param("SupplierCode") String SupplierCode,
                                                             String DateFrom, String DateTo) {
    return supplierMapper.findSupplierTotalProjectQty(SupplierCode, DateFrom, DateTo);
  }

  public List<SupplierSummaryQty> getSupplierTotalItemQty(@Param("SupplierCode") String SupplierCode, String DateFrom,
                                                          String DateTo) {
    return supplierMapper.findSupplierTotalItemQty(SupplierCode, DateFrom, DateTo);
  }

  public List<SupplierSummaryQty> getSupplierTotalProductQty(@Param("SupplierCode") String SupplierCode,
                                                             String DateFrom, String DateTo) {
    return supplierMapper.findSupplierTotalProductQty(SupplierCode, DateFrom, DateTo);
  }

  public List<SupplierSummaryQty> getSupplierTotalQty(@Param("SupplierCode") String SupplierCode, String DateFrom,
                                                      String DateTo) {
    List<SupplierSummaryQty> listProject = supplierMapper.findSupplierTotalProjectQty(SupplierCode, DateFrom, DateTo);
    List<SupplierSummaryQty> listProduct = supplierMapper.findSupplierTotalProductQty(SupplierCode, DateFrom, DateTo);
    List<SupplierSummaryQty> listItem = supplierMapper.findSupplierTotalItemQty(SupplierCode, DateFrom, DateTo);

    List<SupplierSummaryQty> listAll = new ArrayList<>();

    for (SupplierSummaryQty o : listProject) {
      listAll.add(o);
    }
    for (SupplierSummaryQty o : listProduct) {
      listAll.add(o);
    }
    for (SupplierSummaryQty o : listItem) {
      listAll.add(o);
    }
    return listAll;
  }

  public List<SupplierSummaryAmount> getSupplierOpenAmount(String SupplierCode) {
    List<SupplierSummaryAmount> listPage = supplierMapper.findSupplierOpenAmount(SupplierCode);

    for (SupplierSummaryAmount o : listPage) {
      String key = o.getCurrency() + "USD" + Utils.formatDate(new Date());
      log.debug("key:" + key);
      try {
        o.setRate(Float.parseFloat(currencyService.cache.get(key)));
        log.debug("Rate:" + o.getRate());
      } catch (NumberFormatException e) {
        log.error(e.getMessage());
      }
      o.setUSD(o.getAmount().multiply(new BigDecimal(o.getRate())));
    }

    return listPage;
  }

  public List<SupplierSummaryQty> getSupplierOpenProjectQty(@Param("SupplierCode") String SupplierCode) {
    return supplierMapper.findSupplierOpenProjectQty(SupplierCode);
  }

  public List<SupplierSummaryQty> getSupplierOpenItemQty(@Param("SupplierCode") String SupplierCode) {
    return supplierMapper.findSupplierOpenItemQty(SupplierCode);
  }

  public List<SupplierSummaryQty> getSupplierOpenProductQty(@Param("SupplierCode") String SupplierCode) {
    return supplierMapper.findSupplierOpenProductQty(SupplierCode);
  }

  public List<SupplierSummaryQty> getSupplierOpenQty(@Param("SupplierCode") String SupplierCode) {
    List<SupplierSummaryQty> listProject = supplierMapper.findSupplierOpenProjectQty(SupplierCode);
    List<SupplierSummaryQty> listProduct = supplierMapper.findSupplierOpenProductQty(SupplierCode);
    List<SupplierSummaryQty> listItem = supplierMapper.findSupplierOpenItemQty(SupplierCode);

    List<SupplierSummaryQty> listAll = new ArrayList<>();

    for (SupplierSummaryQty o : listProject) {
      listAll.add(o);
    }
    for (SupplierSummaryQty o : listProduct) {
      listAll.add(o);
    }
    for (SupplierSummaryQty o : listItem) {
      listAll.add(o);
    }
    return listAll;
  }

  public List<SupplierDeliveryHistory> getSupplierDeliveryHistory(@Param("SupplierCode") String SupplierCode,
                                                                  String DateFrom, String DateTo) {
    return supplierMapper.findSupplierDeliveryHistory(SupplierCode, DateFrom, DateTo);
  }

  public List<SupplierDelayHistory> getSupplierDelayHistory(@Param("SupplierCode") String SupplierCode, String DateFrom,
                                                            String DateTo) {
    return supplierMapper.findSupplierDelayHistory(SupplierCode, DateFrom, DateTo);
  }

  public List<SupplierOpenItems> getSupplierOpenItems(@Param("SupplierCode") String SupplierCode) {
    return supplierMapper.findSupplierOpenItems(SupplierCode);
  }

  public List<SupplierDetails> getSupplierDetails(@Param("SupplierCode") String SupplierCode) {
    return supplierMapper.findSupplierDetailsByCode(SupplierCode);
  }

  public String getPurchaseDate(@Param("PurchaseNO") String PurchaseNO) {
    return supplierMapper.findPurchaseDate(PurchaseNO);
  }
}
