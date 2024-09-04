/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : Invalid Date                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-09-04 19:58:45                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.da.sageassistantserver.dao.MsteamsWorkflowMapper;
import com.da.sageassistantserver.dao.NoticeMapper;
import com.da.sageassistantserver.dao.WeworkRobotMapper;
import com.da.sageassistantserver.model.DeadPurchaseLine;
import com.da.sageassistantserver.model.LongTimeNC;
import com.da.sageassistantserver.model.LongTimeNoQC;
import com.da.sageassistantserver.model.MsteamsWorkflow;
import com.da.sageassistantserver.model.PnStatus;
import com.da.sageassistantserver.model.ProjectProfit;
import com.da.sageassistantserver.model.SuspectDuplicatedPO;
import com.da.sageassistantserver.model.SuspectDuplicatedRA;
import com.da.sageassistantserver.model.TobeClosedWO;
import com.da.sageassistantserver.model.TobeDealWithOrderLine;
import com.da.sageassistantserver.model.TobeDelivery;
import com.da.sageassistantserver.model.TobeInvoice;
import com.da.sageassistantserver.model.TobePurchaseBom;
import com.da.sageassistantserver.model.TobeReceive;
import com.da.sageassistantserver.model.WeworkRobot;
import com.da.sageassistantserver.utils.Utils;

@Service
public class NoticeService {
  List<String> Sites = List.of("HKG", "ZHU", "YSH", "SGP");

  @Autowired
  RobotLastRunService robotLastRunService;

  @Autowired
  PnService pnService;

  @Autowired
  NoticeMapper noticeMapper;

  @Autowired
  SuspectDuplicateDataService suspectDuplicateDataService;

  @Autowired
  WeworkRobotMapper weworkRobotMapper;

  @Autowired
  MsteamsWorkflowMapper msteamsWorkflowMapper;

  public void sendMessage(String site, String code, String msg) {
    weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
        .eq(WeworkRobot::getNotice_code, code)
        .like(WeworkRobot::getSites, site)
        .eq(WeworkRobot::getEnable, 1))
        .forEach(r -> {
          WeWorkService.sendMessage(r.getRobot_uuid(), msg);
        });

    msteamsWorkflowMapper.selectList((new LambdaQueryWrapper<MsteamsWorkflow>())
        .eq(MsteamsWorkflow::getNotice_code, code)
        .like(MsteamsWorkflow::getSites, site)
        .eq(MsteamsWorkflow::getEnable, 1))
        .forEach(f -> {
          MSteamsService.sendMessage(f.getFlow_url(), msg);
        });
  }

  public void sendMessage(String site, String code, String title, StringBuilder msg) {
    sendMessage(site, code, title + "[" + site + "]" + "\n\n" + msg.toString());
  }

  @Scheduled(cron = "0 0 9 * * MON-FRI")
  public void sendPNNotActive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("INACTIVE_PN");

    Sites.forEach(site -> {
      List<PnStatus> list = pnService.findObsoletePnBySite(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        PnStatus pn = list.get(i);
        msg.append("项目号:\t")
            .append(pn.getProjectNO())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(pn.getPN())
            .append(" ");
        msg.append(pn.getDesc1())
            .append(" ")
            .append(pn.getDesc2())
            .append(" ")
            .append(pn.getDesc3())
            .append("\n");
        msg.append("状态:\t")
            .append(pn.getPNStatus())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "INACTIVE_PN", "⚠︎PN状态不可用", msg);
      }
    });

  }

  @Scheduled(cron = "0 0/10 9-11,13-17 * * MON-FRI")
  public void sendNewSalesOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    String lastRun = robotLastRunService.getLastRun("NEW_SALES_ORDER");

    Sites.forEach(site -> {
      List<TobeDelivery> list = noticeMapper.findNewSOSince(site, lastRun);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        TobeDelivery order = list.get(i);
        msg.append("项目号:\t")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("订单号:\t")
            .append(order.getOrderNO())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(order.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量:\t")
            .append(order.getQty())
            .append("\n");
        msg.append("金额:\t")
            .append(order.getNetPrice().setScale(2, RoundingMode.HALF_UP))
            .append(" ")
            .append(order.getCurrency())
            .append("\n");
        msg.append("客户:\t")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("交货日期:\t")
            .append(Utils.formatDate(order.getRequestDate()))
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "NEW_SALES_ORDER", "🤑💰新订单来了", msg);
      }
    });

  }

  @Scheduled(cron = "0 0 10 * * MON-FRI")
  public void sendSalesOrderDealWithDelay() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("SALES_ORDER_WITHOUT_DEAL");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findTobeDealWithOrderLines(site, -7);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        TobeDealWithOrderLine order = list.get(i);
        msg.append("项目号:\t")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("类型:\t")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg.append("订单日期:\t")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(order.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量:\t")
            .append(order.getQty())
            .append("\n");
        msg.append("客户:\t")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        msg.append("\n更多待处理新订单,请查看https://192.168.10.12/#/Todo 中的NEW-ORDER");
        sendMessage(site, "SALES_ORDER_WITHOUT_DEAL", "😡新订单超7天未处理", msg);
      }
    });

    // for sales
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findTobeDealWithOrderLines(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        TobeDealWithOrderLine order = list.get(i);
        msg.append("项目号:\t")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("类型:\t")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg.append("订单日期:\t")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(order.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量:\t")
            .append(order.getQty())
            .append("\n");
        msg.append("客户:\t")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        msg.append("\n更多待处理新订单,请查看https://192.168.10.12/#/Todo 中的NEW-ORDER");
        sendMessage(site, "SALES_ORDER_WITHOUT_DEAL", "😡新订单超14天未处理", msg);
      }
    });

  }

  @Scheduled(cron = "0 10 10 * * MON-FRI")
  public void sendBomDealWithDelay() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("BOM_NO_DEAL");

    Sites.forEach(site -> {
      List<TobePurchaseBom> list = noticeMapper.findTobePurchaseBom(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        TobePurchaseBom line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("类型:\t")
            .append(line.getOrderType())
            .append("\n");
        msg.append("工包:\t")
            .append(line.getWorkOrderNO())
            .append(" ")
            .append(Utils.formatDate(line.getCreateDate()))
            .append("\n");
        msg.append("销售成品ＰＮ:\t")
            .append(line.getForPN())
            .append("\n");
        msg.append("需采购ＰＮ:\t")
            .append("[" + line.getBomSeq() + "] ")
            .append(line.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(line.getDescription())
            .append("\n");
        msg.append("需求数量:\t")
            .append(line.getQty())
            .append("\n");
        msg.append("锁定数量:\t")
            .append(line.getAllQty())
            .append("\n");
        msg.append("短缺数量:\t")
            .append(line.getShortQty())
            .append("\n");
        msg.append("客户:\t")
            .append(line.getCustomerCode())
            .append(" ")
            .append(line.getCustomerName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        msg.append("\n更多待采购状态,请查看https://192.168.10.12/#/Todo 中的SHORT-BOM");
        sendMessage(site, "BOM_NO_DEAL", "😡Bom项超14天未采购", msg);
      }
    });
  }

  @Scheduled(cron = "0 0 14 * * MON-FRI")
  public void sendNoAckPO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("PURCHASE_ORDER_NO_ACK_DATE");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findNoACkPO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        TobeReceive line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号:\t")
            .append(line.getPurchaseNO())
            .append("[" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购ＰＮ:\t")
            .append(line.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(line.getDescription())
            .append("\n");
        msg.append("期望交付日期:\t")
            .append(Utils.formatDate(line.getExpectDate()))
            .append("\n");
        msg.append("供应商:\t")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "PURCHASE_ORDER_NO_ACK_DATE", "😡采购单没有供应商交付日期", msg);
      }
    });

  }

  @Scheduled(cron = "0 0 11 * * MON,WED,FRI")
  public void sendLongTimeNoReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_RECEIVE");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findLongTimeNoReceive(site, -90);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        TobeReceive line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号:\t")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购ＰＮ:\t")
            .append(line.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(line.getDescription())
            .append("\n");
        msg.append("期望交付日期:\t")
            .append(Utils.formatDate(line.getExpectDate()))
            .append("\n");
        msg.append("供应商:\t")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        msg.append("\n更多收货状态,请查看https://192.168.10.12/#/Todo 中的RECEIVE");
        sendMessage(site, "LONG_TIME_NO_RECEIVE", "😬采购交货严重超期(大于90天)", msg);
      }
    });

  }

  @Scheduled(cron = "0 10 14 * * MON-FRI")
  public void sendLongTimeNC() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NC");

    // for purchaser
    Sites.forEach(site -> {
      List<LongTimeNC> list = noticeMapper.findLongTimeNC(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        LongTimeNC line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号:\t")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购ＰＮ:\t")
            .append(line.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(line.getDescription())
            .append("\n");
        msg.append("首次检验日期:\t")
            .append(Utils.formatDate(line.getFirstNCDate()))
            .append("\n");
        msg.append("供应商:\t")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "LONG_TIME_NC", "😬NC处理14天仍未出货", msg);
      }
    });

  }

  @Scheduled(cron = "0 5 11 * * MON-FRI")
  public void sendLongTimeNoQC() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_QC");

    // for purchaser
    Sites.forEach(site -> {
      List<LongTimeNoQC> list = noticeMapper.findLongTimeNoQC(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        LongTimeNoQC line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号:\t")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购ＰＮ:\t")
            .append(line.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(line.getDescription())
            .append("\n");
        msg.append("收货日期:\t")
            .append(Utils.formatDate(line.getReceiptDate()))
            .append("\n");
        msg.append("供应商:\t")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "LONG_TIME_NO_QC", "😬收货14天仍未检验", msg);
      }
    });

  }

  @Scheduled(cron = "0 10 11 * * MON-FRI")
  public void sendOrphanPO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("ORPHAN_PURCHASE_ORDER");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findOrphanPO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        TobeReceive line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号:\t")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购ＰＮ:\t")
            .append(line.getPN())
            .append("\n");
        msg.append("ＰＮ描述:\t")
            .append(line.getDescription())
            .append("\n");
        msg.append("采购单价:\t")
            .append(line.getNetPrice().setScale(2, RoundingMode.HALF_UP))
            .append(" ")
            .append(line.getCurrency())
            .append("\n");
        msg.append("期望交付日期:\t")
            .append(Utils.formatDate(line.getExpectDate()))
            .append("\n");
        msg.append("供应商:\t")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("采购者:\t")
            .append(line.getCreateUser())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "ORPHAN_PURCHASE_ORDER", "🧯🧯采购单项目不存在🧯🧯", msg);
      }
    });

  }

  @Scheduled(cron = "0 10 14 * * MON-FRI")
  public void sendLongTimeNoInvoice() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_INVOICE");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeInvoice> list = noticeMapper.findLongTimeNoInvoice(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        TobeInvoice line = list.get(i);
        msg.append("项目号:\t")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号:\t")
            .append(line.getPurchaseNO())
            .append(" [" + line.getPurchaseLine() + "] ")
            .append("\n");
        msg.append("采购ＰＮ:\t")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("采购者:\t")
            .append(line.getPurchaser())
            .append("\n");
        msg.append("采购日期:\t")
            .append(Utils.formatDate(line.getPurchaseDate()))
            .append("\n");
        msg.append("供应商:\t")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("收货单号:\t")
            .append(line.getReceiveNO())
            .append(" [")
            .append(line.getReceiveLine())
            .append("]\n");
        msg.append("收货人:\t")
            .append(line.getReceiptor())
            .append("\n");
        msg.append("收货日期:\t")
            .append(Utils.formatDate(line.getReceiveDate()))
            .append("\n");
        msg.append("价格:\t")
            .append(line.getPrice().setScale(2, RoundingMode.HALF_UP))
            .append(" ").append(line.getCurrency())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "LONG_TIME_NO_INVOICE", "😬发票严重超期90天(仅2024年后)", msg);
      }
    });

  }

  @Scheduled(cron = "0 0/10 9-11,13-17 * * MON-FRI")
  public void sendDuplicatePurchaseOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    robotLastRunService.getLastRun("DUPLICATE_PURCHASE_ORDER");

    Sites.forEach(site -> {
      List<SuspectDuplicatedPO> list = noticeMapper.findDuplicatedPOBySite(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) {

        SuspectDuplicatedPO order = list.get(i);
        msg.append("项目号:\t")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(order.getPN())
            .append("\n");
        msg.append("第" + order.getSeq() + "次采购单:\t")
            .append(order.getPurchaseNO())
            .append("-")
            .append(order.getPurchaseLine())
            .append(" ")
            .append(Utils.formatDate(order.getPurchaseDate()))
            .append(" ")
            .append(order.getPurchaser())
            .append("\n");
        msg.append("第" + order.getSeq() + "次采购数量:\t")
            .append(order.getPurchaseQty())
            .append("\n");
        msg.append("第" + order.getSeq() + "次金额:\t")
            .append(order.getCost().setScale(2, RoundingMode.HALF_UP))
            .append(" ")
            .append(order.getCurrency())
            .append("\n");
        msg.append("项目总采购数量:\t")
            .append(order.getTotalPurchaseQty())
            .append("\n");
        msg.append("关联销售/备库数量:\t")
            .append(order.getTotalSalesQty())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        msg.append("\n在PurchaseLine或者ReceiveLine的Text中添加'AGAIN'可抑制通知");
        msg.append("\n更多重复采购,请查看https://192.168.10.12/#/SuspectDuplicateRecords");
        sendMessage(site, "DUPLICATE_PURCHASE_ORDER", "😵疑似重复采购", msg);
      }
    });

  }

  @Scheduled(cron = "0 15 10 * * MON-FRI")
  public void sendDuplicateReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    robotLastRunService.getLastRun("DUPLICATE_RECEIVE");

    Sites.forEach(site -> {
      List<SuspectDuplicatedRA> list = noticeMapper.findDuplicatedRABySite(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        SuspectDuplicatedRA ra = list.get(i);
        msg.append("项目号:\t")
            .append(ra.getProjectNO())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(ra.getPN())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货单:\t")
            .append(ra.getReceiptNO())
            .append("-")
            .append(ra.getReceiptLine())
            .append(" ")
            .append(Utils.formatDate(ra.getReceiptDate()))
            .append(" ")
            .append(ra.getReceiptor())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货数量:\t")
            .append(ra.getReceiptQty())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次金额:\t")
            .append(ra.getReceiptAmount().setScale(2, RoundingMode.HALF_UP))
            .append(" ")
            .append(ra.getCurrency())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购单:\t")
            .append(ra.getPurchaseNO())
            .append("-")
            .append(ra.getPurchaseLine())
            .append(" ")
            .append(Utils.formatDate(ra.getPurchaseDate()))
            .append(" ")
            .append(ra.getPurchaser())
            .append("\n");
        msg.append("项目总收货数量:\t")
            .append(ra.getTotalReceiptQty())
            .append("\n");
        msg.append("项目总采购数量:\t")
            .append(ra.getTotalPurchaseQty())
            .append("\n");
        msg.append("项目关联销售单数量:\t")
            .append(ra.getTotalSalesQty())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        msg.append("\n在PurchaseLine或者ReceiveLine的Text中添加'AGAIN'可抑制通知");
        msg.append("\n更多重复收货,请查看https://192.168.10.12/#/SuspectDuplicateRecords");
        sendMessage(site, "DUPLICATE_RECEIVE", "😵疑似重复收货", msg);
      }
    });

  }

  @Scheduled(cron = "0 15 11,16 * * MON-FRI")
  public void sendDuplicateWO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    robotLastRunService.getLastRun("DUPLICATE_WORK_ORDER");

    Sites.forEach(site -> {
      List<String> list = noticeMapper.duplicatedWO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        String PJT = list.get(i);
        msg.append(PJT)
            .append("\n\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "DUPLICATE_WORK_ORDER", "😵疑似重复工包", msg);
      }
    });

  }

  @Scheduled(cron = "0 0/10 9-11,13-17 * * MON-FRI")
  public void sendMixProjectBetweenZHUAndYSH() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("MIX_PROJECT_BETWEEN_ZHU_YSH");

    final StringBuilder msg = new StringBuilder();

    List<String> list = noticeMapper.mixPOProjectBetweenZHUAndYSH();

    for (int i = 0; i < list.size(); i++) {
      String PJT = list.get(i);
      msg.append(PJT)
          .append("\n\n");
    }

    if (msg.length() > 0) {
      sendMessage("ZHU", "MIX_PROJECT_BETWEEN_ZHU_YSH", "🤯珠海和上海采购单混用项目号", msg);
    }

    List<String> list2 = noticeMapper.mixWOProjectBetweenZHUAndYSH();
    final StringBuilder msg2 = new StringBuilder();

    for (int i = 0; i < list2.size(); i++) {
      String PJT = list2.get(i);
      msg2.append(PJT)
          .append("\n\n");
    }

    if (msg2.length() > 0) {
      sendMessage("ZHU", "MIX_PROJECT_BETWEEN_ZHU_YSH", "🤯珠海和上海工包混用项目号", msg2);
    }
  }

  @Scheduled(cron = "0 0/10 9-11,13-17 * * MON-FRI")
  public void sendNewReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    String lastRun = robotLastRunService.getLastRun("NEW_RECEIVE");

    Sites.forEach(site -> {
      List<SuspectDuplicatedRA> list = noticeMapper.findNewRASince(site, lastRun);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        SuspectDuplicatedRA ra = list.get(i);
        msg.append("项目号:\t")
            .append(ra.getProjectNO())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(ra.getPN())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购单:\t")
            .append(ra.getPurchaseNO())
            .append(" [")
            .append(ra.getPurchaseLine())
            .append("] ")
            .append(Utils.formatDate(ra.getPurchaseDate()))
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货单:\t")
            .append(ra.getReceiptNO())
            .append(" [")
            .append(ra.getReceiptLine())
            .append("] ")
            .append(Utils.formatDate(ra.getReceiptDate()))
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货数量:\t")
            .append(ra.getReceiptQty())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次金额:\t")
            .append(ra.getReceiptAmount().setScale(2, RoundingMode.HALF_UP))
            .append(" ")
            .append(ra.getCurrency())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购人:\t")
            .append(ra.getPurchaser())
            .append("\n");
        msg.append("项目总收货数量:\t")
            .append(ra.getTotalReceiptQty())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "NEW_RECEIVE", "🤯新收货通知", msg);
      }
    });

  }

  @Scheduled(cron = "0 30 9 * * MON-FRI")
  public void sendLongTimeNoDelivery() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_DELIVERY");

    Sites.forEach(site -> {
      List<TobeDelivery> list = noticeMapper.findLongTimeNoDelivery(site, -30);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        TobeDelivery order = list.get(i);
        msg.append("项目号:\t")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("订单类型:\t")
            .append(order.getOrderType())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量:\t")
            .append(order.getQty())
            .append("\n");
        msg.append("金额:\t")
            .append(order.getNetPrice().setScale(2, RoundingMode.HALF_UP))
            .append(" ")
            .append(order.getCurrency())
            .append("\n");
        msg.append("客户:\t")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("要求交货日期:\t")
            .append(Utils.formatDate(order.getRequestDate()))
            .append("\n");
        msg.append("计划交货日期:\t")
            .append(Utils.formatDate(order.getPlanedDate()))
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "LONG_TIME_NO_DELIVERY", "🧯🧯订单交付严重超期🧯🧯", msg);
      }
    });

  }

  @Scheduled(cron = "0 20 10 * * MON-FRI")
  public void sendNoBomServiceOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("SERVICE_ORDER_NO_WO");

    // for customer support service
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findNOBomServiceOrder(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        TobeDealWithOrderLine order = list.get(i);
        msg.append("项目号:\t")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("类型:\t")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg.append("订单日期:\t")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量:\t")
            .append(order.getQty())
            .append("\n");
        msg.append("客户:\t")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "SERVICE_ORDER_NO_WO", "😟售后订单建议创建工包", msg);
      }
    });

  }

  @Scheduled(cron = "0 20 11 * * MON-FRI")
  public void sendTobeClosedWO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("TO_BE_CLOSE_WO");

    Sites.forEach(site -> {
      List<TobeClosedWO> list = noticeMapper.findTobeClosedWO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        TobeClosedWO ra = list.get(i);
        msg.append("项目号:\t")
            .append(ra.getProjectNO())
            .append("\n");
        msg.append("工包:\t")
            .append(ra.getWorkOrderNO())
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "TO_BE_CLOSE_WO", "🧹WO关闭提醒, 订单项目已关闭, 工包未关闭", msg);
      }
    });

  }

  @Scheduled(cron = "0 20 11,16 * * MON-FRI")
  public void sendDeadPurchaseLine() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("DEAD_PURCHASE_LINE");

    Sites.forEach(site -> {
      List<DeadPurchaseLine> list = noticeMapper.findDeadPurchaseLine(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {

        DeadPurchaseLine o = list.get(i);
        msg.append("项目号:\t")
            .append(o.getProjectNO())
            .append("\n");
        msg.append("订单:\t")
            .append(o.getOrderNO())
            .append("\n");
        msg.append("订单日期:\t")
            .append(Utils.formatDate(o.getOrderDate()))
            .append("\n");
        msg.append("采购单:\t")
            .append(o.getPurchaseNO())
            .append("\n");
        msg.append("采购行:\t")
            .append(o.getPurchaseLine())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(o.getPN())
            .append("\n");
        msg.append("采购人:\t")
            .append(o.getPurchaser())
            .append("\n");
        msg.append("采购日期:\t")
            .append(Utils.formatDate(o.getPurchaseDate()))
            .append("\n");
        msg.append("\n");
      }

      if (msg.length() > 0) {
        sendMessage(site, "DEAD_PURCHASE_LINE", "🧹采购单关闭提醒, 订单项目已关闭, 采购单未收货", msg);
      }
    });

  }

  @Scheduled(cron = "0 8 9-11,13-17 * * MON-FRI")
  public void sendPreAnalyzeProjectProfit() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    String lastRun = robotLastRunService.getLastRun("PRE_ANALYZE_PROJECT_PROFIT");

    Sites.forEach(site -> {
      if (site.equals("HKG")) {
        return;
      }

      List<ProjectProfit> list = noticeMapper.findPreAnalysesProjectProfit(site, lastRun);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        ProjectProfit o = list.get(i);
        msg.append("项目号:\t")
            .append(o.getProjectNO())
            .append("\n");
        msg.append("订单:\t")
            .append(o.getOrderNO())
            .append("\n");
        msg.append("ＰＮ:\t")
            .append(o.getPN())
            .append(" ")
            .append(o.getDescription())
            .append("\n");
        msg.append("数量:\t")
            .append(o.getQty())
            .append("\n");
        msg.append("销售原价:\t")
            .append(o.getProjectSalesCurrencyPrice().setScale(2, RoundingMode.HALF_UP) + o.getSalesCurrency())
            .append("\n");
        msg.append("销售价格:\t")
            .append(o.getProjectSalesLocalPrice().setScale(2, RoundingMode.HALF_UP) + o.getLocalCurrency())
            .append("\n");
        msg.append("采购成本:\t")
            .append(o.getProjectLocalCost().setScale(2, RoundingMode.HALF_UP))
            .append("\n");
        msg.append("盈余:\t")
            .append(o.getProfit().setScale(2, RoundingMode.HALF_UP))
            .append("\n");
        msg.append("\n");

      }
      if (msg.length() > 0) {
        sendMessage(site, "PRE_ANALYZE_PROJECT_PROFIT", "🥶预分析项目盈亏", msg);
      }
    });

  }

}
