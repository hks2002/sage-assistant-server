/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                       *
 * @LastEditDate          : 2025-07-27 09:24:51                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.LinksDeliveryLine;
import com.da.sage.assistant.model.LinksInvoiceLine;
import com.da.sage.assistant.model.LinksPurchaseLine;
import com.da.sage.assistant.model.LinksReceiptLine;
import com.da.sage.assistant.model.LinksSalesLine;

@Mapper
public interface LinksMapper {
  Integer findLinksLineCntBySO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode);

  Integer findLinksLineCntByPO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("VendorCode") String VendorCode);

  List<LinksSalesLine> findLinksSalesLineBySO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksPurchaseLine> findLinksPurchaseLineBySO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksDeliveryLine> findLinksDeliveryLineBySO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksInvoiceLine> findLinksInvoiceLineBySO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksReceiptLine> findLinksReceiptLineBySO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksSalesLine> findLinksSalesLineByPO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksPurchaseLine> findLinksPurchaseLineByPO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksDeliveryLine> findLinksDeliveryLineByPO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksInvoiceLine> findLinksInvoiceLineByPO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);

  List<LinksReceiptLine> findLinksReceiptLineByPO(
      @Param("Site") String Site,
      @Param("ProjectNO") String ProjectNO,
      @Param("DateFrom") String DateFrom,
      @Param("DateTo") String DateTo,
      @Param("CustomerCode") String CustomerCode,
      @Param("VendorCode") String VendorCode,
      @Param("Offset") Integer Offset,
      @Param("Limit") Integer Limit);
}
