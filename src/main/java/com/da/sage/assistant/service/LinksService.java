/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditDate          : 2025-07-27 09:37:05                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.da.sage.assistant.dao.LinksMapper;
import com.da.sage.assistant.model.LinksDeliveryLine;
import com.da.sage.assistant.model.LinksInvoiceLine;
import com.da.sage.assistant.model.LinksPurchaseLine;
import com.da.sage.assistant.model.LinksReceiptLine;
import com.da.sage.assistant.model.LinksSalesLine;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinksService {

  private final LinksMapper linksMapper;

  public Integer findLinksLineCnt(
      String Site,
      String ProjectNO,
      String OrderType,
      String DateFrom,
      String DateTo,
      String CustomerCode,
      String VendorCode) {
    if (OrderType.equals("SO"))
      return linksMapper.findLinksLineCntBySO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode);
    else if (OrderType.equals("PO"))
      return linksMapper.findLinksLineCntByPO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          VendorCode);
    else
      return 0;
  }

  public List<LinksSalesLine> findLinksSalesLine(
      String Site,
      String ProjectNO,
      String OrderType,
      String DateFrom,
      String DateTo,
      String CustomerCode,
      String VendorCode,
      Integer Offset,
      Integer Limit) {
    if (OrderType.equals("SO"))
      return linksMapper.findLinksSalesLineBySO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    else if (OrderType.equals("PO"))
      return linksMapper.findLinksSalesLineByPO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    else
      return Collections.emptyList();
  }

  public List<LinksPurchaseLine> findLinksPurchaseLine(
      String Site,
      String ProjectNO,
      String OrderType,
      String DateFrom,
      String DateTo,
      String CustomerCode,
      String VendorCode,
      Integer Offset,
      Integer Limit) {
    if (OrderType.equals("SO"))
      return linksMapper.findLinksPurchaseLineBySO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    else if (OrderType.equals("PO"))
      return linksMapper.findLinksPurchaseLineByPO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    else
      return Collections.emptyList();
  }

  public List<LinksReceiptLine> findLinksReceiptLine(
      String Site,
      String ProjectNO,
      String OrderType,
      String DateFrom,
      String DateTo,
      String CustomerCode,
      String VendorCode,
      Integer Offset,
      Integer Limit) {
    if (OrderType.equals("SO")) {
      return linksMapper.findLinksReceiptLineBySO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    } else if (OrderType.equals("PO")) {
      return linksMapper.findLinksReceiptLineByPO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    } else
      return Collections.emptyList();
  }

  public List<LinksDeliveryLine> findLinksDeliveryLine(
      String Site,
      String ProjectNO,
      String OrderType,
      String DateFrom,
      String DateTo,
      String CustomerCode,
      String VendorCode,
      Integer Offset,
      Integer Limit) {
    if (OrderType.equals("SO")) {
      return linksMapper.findLinksDeliveryLineBySO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    } else if (OrderType.equals("PO")) {
      return linksMapper.findLinksDeliveryLineByPO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    } else
      return Collections.emptyList();
  }

  public List<LinksInvoiceLine> findLinksInvoiceLine(
      String Site,
      String ProjectNO,
      String OrderType,
      String DateFrom,
      String DateTo,
      String CustomerCode,
      String VendorCode,
      Integer Offset,
      Integer Limit) {
    if (OrderType.equals("SO")) {
      return linksMapper.findLinksInvoiceLineBySO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    } else if (OrderType.equals("PO")) {
      return linksMapper.findLinksInvoiceLineByPO(
          Site,
          ProjectNO,
          DateFrom,
          DateTo,
          CustomerCode,
          VendorCode,
          Offset,
          Limit);
    } else
      return Collections.emptyList();
  }
}
