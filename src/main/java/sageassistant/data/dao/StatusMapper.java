/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 19:31:50                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/dao/StatusMapper.java                                *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sageassistant.data.model.TobeClosedWO;
import sageassistant.data.model.TobeDealWithOrderLine;
import sageassistant.data.model.TobeDelivery;
import sageassistant.data.model.TobePurchaseBom;
import sageassistant.data.model.TobeReceive;
import sageassistant.data.model.TobeTrackingBOMLine;
import sageassistant.data.model.TobeTrackingPurchaseOrderLine;
import sageassistant.data.model.TobeTrackingQALine;
import sageassistant.data.model.TobeTrackingReceiptLine;
import sageassistant.data.model.TobeTrackingSalesOrderLine;

@Mapper
public interface StatusMapper {
    List<TobeDelivery> findTobeDeliveryBySite(@Param("Site") String Site);

    List<TobeReceive> findTobeReceiveBySite(@Param("Site") String Site);

    List<TobeDealWithOrderLine> findTobeDealWithOrderLineBySite(@Param("Site") String site);

    List<TobePurchaseBom> findTobePurchaseBomBySite(@Param("Site") String Site);

    List<TobeClosedWO> findTobeClosedWOBySite(@Param("Site") String Site);

    Integer findTobeTrackingSalesOrderLineCntBySite(
        @Param("Site") String Site,
        @Param("OrderType") String OrderType,
        @Param("CustomerCode") String CustomerCode,
        @Param("VendorCode") String VendorCode
    );

    List<TobeTrackingSalesOrderLine> findTobeTrackingSalesOrderLineBySite(
        @Param("Site") String Site,
        @Param("OrderType") String OrderType,
        @Param("CustomerCode") String CustomerCode,
        @Param("VendorCode") String VendorCode,
        @Param("OrderBy") String OrderBy,
        @Param("Descending") String Descending,
        @Param("Offset") Integer Offset,
        @Param("Limit") Integer Limit
    );

    List<TobeTrackingBOMLine> findTobeTrackingBOMLineBySite(
        @Param("Site") String Site,
        @Param("OrderType") String OrderType,
        @Param("CustomerCode") String CustomerCode,
        @Param("VendorCode") String VendorCode,
        @Param("OrderBy") String OrderBy,
        @Param("Descending") String Descending,
        @Param("Offset") Integer Offset,
        @Param("Limit") Integer Limit
    );

    List<TobeTrackingPurchaseOrderLine> findTobeTrackingPurchaseOrderLineBySite(
        @Param("Site") String Site,
        @Param("OrderType") String OrderType,
        @Param("CustomerCode") String CustomerCode,
        @Param("VendorCode") String VendorCode,
        @Param("OrderBy") String OrderBy,
        @Param("Descending") String Descending,
        @Param("Offset") Integer Offset,
        @Param("Limit") Integer Limit
    );

    List<TobeTrackingReceiptLine> findTobeTrackingReceiptLineBySite(
        @Param("Site") String Site,
        @Param("OrderType") String OrderType,
        @Param("CustomerCode") String CustomerCode,
        @Param("VendorCode") String VendorCode,
        @Param("OrderBy") String OrderBy,
        @Param("Descending") String Descending,
        @Param("Offset") Integer Offset,
        @Param("Limit") Integer Limit
    );

    List<TobeTrackingQALine> findTobeTrackingQALineBySite(
        @Param("Site") String Site,
        @Param("OrderType") String OrderType,
        @Param("CustomerCode") String CustomerCode,
        @Param("VendorCode") String VendorCode,
        @Param("OrderBy") String OrderBy,
        @Param("Descending") String Descending,
        @Param("Offset") Integer Offset,
        @Param("Limit") Integer Limit
    );
}
