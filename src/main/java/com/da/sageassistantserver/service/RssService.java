/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2025-01-20 00:10:34                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.service;

import com.da.sageassistantserver.model.PnStatus;
import com.da.sageassistantserver.model.TobeDealWithOrderLine;
import com.da.sageassistantserver.model.TobeDelivery;
import com.da.sageassistantserver.model.TobePurchaseBom;
import com.da.sageassistantserver.model.TobeReceive;
import com.da.sageassistantserver.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RssService {

  @Autowired
  private PnService pnService;

  @Autowired
  private StatusService statusService;

  SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

  public String checkPnUpdate(String site) {
    List<PnStatus> list = pnService.findObsoletePnBySite(site);

    String content = "<table border=\"2\" style=\"border-collapse:collapse;\">";
    content += "<tr style=\"background-color:blue;\">";
    content += "<th>ProjectNO</th>";
    content += "<th>PN</th>";
    content += "<th>Desc</th>";
    content += "<th>PN Status</th>";
    content += "<th>WC</th>";
    content += "<th>CustomerCode</th>";
    content += "<th>CustomerName</th>";
    content += "</tr>";

    for (PnStatus o : list) {
      content += "<tr style=\"background-color:red;\">";
      content += "<td>" + o.getProjectNO() + "</td>";
      content += "<td>" + o.getPN() + "</td>";
      content +=
        "<td>" +
        o.getDesc1() +
        " " +
        o.getDesc2() +
        " " +
        o.getDesc3() +
        "</td>";
      content += "<td>" + o.getPNStatus() + "</td>";
      content += "<td>" + o.getWC() + "</td>";
      content += "<td>" + o.getCustomerCode() + "</td>";
      content += "<td>" + o.getCustomerName() + "</td>";
      content += "</tr>";
    }
    content += "</table>";

    if (list.size() > 0) {
      return content;
    } else {
      log.info("No obsolete PN found.");
      return "";
    }
  }

  public String checkTobeDelivery(String site, Integer range) {
    List<TobeDelivery> list = statusService.findTobeDeliveryBySite(site);

    String content = "<table border=\"2\" style=\"border-collapse:collapse;\">";

    content += "<tr style=\"background-color:blue;\">";
    content += "<th>ProjectNO</th>";
    content += "<th>PN</th>";
    content += "<th>Desc</th>";
    content += "<th>Qty</th>";
    content += "<th>OrderDate</th>";
    content += "<th>RequestDate</th>";
    content += "<th>OverDueDays</th>";
    content += "</tr>";

    Date now = new Date();
    Integer Nrange = range * -1;
    Integer halfNrange = Nrange / 2;
    Integer quarterNrange = Nrange / 4;

    for (Iterator<TobeDelivery> iter = list.iterator(); iter.hasNext();) {
      TobeDelivery o = iter.next();
      long diff = Utils.dateDiff(o.getRequestDate(), now);

      if (diff >= Nrange) {
        if (diff >= Nrange && diff < halfNrange) {
          content += "<tr>";
        } else if (diff >= halfNrange && diff < quarterNrange) {
          content += "<tr style=\"background-color:yellow;\">";
        } else if (diff >= quarterNrange) {
          content += "<tr style=\"background-color:red;\">";
        }

        content += "<td>" + o.getProjectNO() + "</td>";
        content += "<td>" + o.getPN() + "</td>";
        content += "<td>" + o.getDescription() + "</td>";
        content += "<td>" + o.getQty() + "</td>";
        content += "<td>" + fmt.format(o.getOrderDate()) + "</td>";
        content += "<td>" + fmt.format(o.getRequestDate()) + "</td>";
        content += "<td>" + diff + "</td>";
        content += "</tr>";
      } else {
        iter.remove();
      }
    }

    content += "</table>";

    if (list.size() > 0) {
      return content;
    } else {
      log.info("No TobeDelivery found.");
      return "";
    }
  }

  public String checkTobeReceive(String site, Integer range) {
    List<TobeReceive> list = statusService.findTobeReceiveBySite(site);

    String content = "<table border=\"2\" style=\"border-collapse:collapse;\">";

    content += "<tr style=\"background-color:blue;\">";
    content += "<th>PurchaseNO</th>";
    content += "<th>Line</th>";
    content += "<th>ProjectNO</th>";
    content += "<th>PN</th>";
    content += "<th>Desc</th>";
    content += "<th>Qty</th>";
    content += "<th>OrderDate</th>";
    content += "<th>AckDate</th>";
    content += "<th>ExpectDate</th>";
    content += "<th>Purchaser</th>";
    content += "<th>OverDueDays</th>";
    content += "</tr>";

    Date now = new Date();
    Integer Nrange = range * -1;
    Integer halfNrange = Nrange / 2;
    Integer quarterNrange = Nrange / 4;

    for (Iterator<TobeReceive> iter = list.iterator(); iter.hasNext();) {
      TobeReceive o = iter.next();
      long diff = Utils.dateDiff(o.getExpectDate(), now);

      if (diff >= Nrange) {
        if (diff >= Nrange && diff < halfNrange) {
          content += "<tr>";
        } else if (diff >= halfNrange && diff < quarterNrange) {
          content += "<tr style=\"background-color:yellow;\">";
        } else if (diff >= quarterNrange) {
          content += "<tr style=\"background-color:red;\">";
        }

        content += "<td>" + o.getPurchaseNO() + "</td>";
        content += "<td>" + o.getLine() + "</td>";
        content += "<td>" + o.getProjectNO() + "</td>";
        content += "<td>" + o.getPN() + "</td>";
        content += "<td>" + o.getDescription() + "</td>";
        content += "<td>" + o.getQty() + "</td>";
        content += "<td>" + fmt.format(o.getOrderDate()) + "</td>";
        content += "<td>" + fmt.format(o.getAckDate()) + "</td>";
        content += "<td>" + fmt.format(o.getExpectDate()) + "</td>";
        content += "<td>" + o.getCreateUser() + "</td>";
        content += "<td>" + diff + "</td>";
        content += "</tr>";
      } else {
        iter.remove();
      }
    }

    content += "</table>";

    if (list.size() > 0) {
      return content;
    } else {
      log.info("No TobeReceive found.");
      return "";
    }
  }

  public String checkTobePurchaseBom(String site) {
    List<TobePurchaseBom> list = statusService.findTobePurchaseBomBySite(site);

    String content = "<table border=\"2\" style=\"border-collapse:collapse;\">";

    content += "<tr style=\"background-color:blue;\">";
    content += "<th>ProjectNO</th>";
    content += "<th>For PN</th>";
    content += "<th>WorkOrderNO</th>";
    content += "<th>getBomSeq</th>";
    content += "<th>PN</th>";
    content += "<th>Desc</th>";
    content += "<th>Qty</th>";
    content += "<th>ShortQty</th>";
    content += "<th>AllQty</th>";
    content += "<th>CreateDate</th>";
    content += "<th>Days</th>";
    content += "</tr>";

    for (TobePurchaseBom o : list) {
      long diff = Utils.dateDiff(o.getCreateDate(), new Date());

      if (diff >= 14) {
        content += "<tr style=\"background-color:red;\">";
      } else if (diff >= 7) {
        content += "<tr style=\"background-color:yellow;\">";
      } else {
        content += "<tr>";
      }
      content += "<td>" + o.getProjectNO() + "</td>";
      content += "<td>" + o.getForPN() + "</td>";
      content += "<td>" + o.getWorkOrderNO() + "</td>";
      content += "<td>" + o.getBomSeq() + "</td>";
      content += "<td>" + o.getPN() + "</td>";
      content += "<td>" + o.getDescription() + "</td>";
      content += "<td>" + o.getQty() + "</td>";
      content += "<td>" + o.getShortQty() + "</td>";
      content += "<td>" + o.getAllQty() + "</td>";
      content += "<td>" + fmt.format(o.getCreateDate()) + "</td>";
      content += "<td>" + diff + "</td>";
      content += "</tr>";
    }
    content += "</table>";

    if (list.size() > 0) {
      return content;
    } else {
      log.info("No TobePurchaseBom found.");
      return "";
    }
  }

  public String checkTobeDealWithOrderLine(String site) {
    List<TobeDealWithOrderLine> list = statusService.findTobeDealWithOrderLineBySite(
      site
    );

    String content = "<table border=\"2\" style=\"border-collapse:collapse;\">";

    content += "<tr style=\"background-color:blue;\">";
    content += "<th>ProjectNO</th>";
    content += "<th>OrderType</th>";
    content += "<th>PN</th>";
    content += "<th>Desc</th>";
    content += "<th>Qty</th>";
    content += "<th>Unit</th>";
    content += "<th>CustomerCode</th>";
    content += "<th>CustomerName</th>";
    content += "<th>OrDerDate</th>";
    content += "<th>DemandDate</th>";
    content += "<th>Days</th>";
    content += "</tr>";

    for (TobeDealWithOrderLine o : list) {
      long diff = Utils.dateDiff(o.getOrderDate(), new Date());

      if (diff >= 14) {
        content += "<tr style=\"background-color:red;\">";
      } else if (diff >= 7) {
        content += "<tr style=\"background-color:yellow;\">";
      } else {
        content += "<tr>";
      }
      content += "<td>" + o.getProjectNO() + "</td>";
      content += "<td>" + o.getOrderType() + "</td>";
      content += "<td>" + o.getPN() + "</td>";
      content += "<td>" + o.getDescription() + "</td>";
      content += "<td>" + o.getQty() + "</td>";
      content += "<td>" + o.getUnit() + "</td>";
      content += "<td>" + o.getCustomerCode() + "</td>";
      content += "<td>" + o.getCustomerName() + "</td>";
      content += "<td>" + fmt.format(o.getOrderDate()) + "</td>";
      content += "<td>" + fmt.format(o.getPlanedDate()) + "</td>";
      content += "<td>" + diff + "</td>";
      content += "</tr>";
    }
    content += "</table>";

    if (list.size() > 0) {
      return content;
    } else {
      log.info("No TobeDealWithOrderLine found.");
      return "";
    }
  }
}
