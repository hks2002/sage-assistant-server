/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 19:32:14                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/dao/SupplierMapper.java                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sageassistantserver.model.SupplierDelayHistory;
import com.da.sageassistantserver.model.SupplierDeliveryHistory;
import com.da.sageassistantserver.model.SupplierDetails;
import com.da.sageassistantserver.model.SupplierName;
import com.da.sageassistantserver.model.SupplierOpenItems;
import com.da.sageassistantserver.model.SupplierSummaryAmount;
import com.da.sageassistantserver.model.SupplierSummaryQty;

@Mapper
public interface SupplierMapper {
    List<SupplierName> findSupplierByCodeOrName(
        @Param("SupplierCodeOrName") String SupplierCodeOrName,
        @Param("Count") Integer Count
    );

    List<SupplierDetails> findSupplierDetailsByCode(@Param("SupplierCode") String SupplierCode);

    List<SupplierSummaryAmount> findSupplierOpenAmount(@Param("SupplierCode") String SupplierCode);

    List<SupplierSummaryQty> findSupplierOpenProjectQty(@Param("SupplierCode") String SupplierCode);

    List<SupplierSummaryQty> findSupplierOpenItemQty(@Param("SupplierCode") String SupplierCode);

    List<SupplierSummaryQty> findSupplierOpenProductQty(@Param("SupplierCode") String SupplierCode);

    List<SupplierOpenItems> findSupplierOpenItems(@Param("SupplierCode") String SupplierCode);

    List<SupplierSummaryAmount> findSupplierTotalAmount(
        @Param("SupplierCode") String SupplierCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<SupplierSummaryQty> findSupplierTotalProjectQty(
        @Param("SupplierCode") String SupplierCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<SupplierSummaryQty> findSupplierTotalItemQty(
        @Param("SupplierCode") String SupplierCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<SupplierSummaryQty> findSupplierTotalProductQty(
        @Param("SupplierCode") String SupplierCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<SupplierDeliveryHistory> findSupplierDeliveryHistory(
        @Param("SupplierCode") String SupplierCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<SupplierDelayHistory> findSupplierDelayHistory(
        @Param("SupplierCode") String SupplierCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    String findPurchaseDate(@Param("PurchaseNO") String PurchaseNO);
}
