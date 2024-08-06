/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2024-06-02 21:34:24                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-08-07 01:43:15                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

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

  public void sendMessage(String msg, String site, String code) {
    weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
        .eq(WeworkRobot::getRobot_code, code).like(WeworkRobot::getSites, site))
        .forEach(r -> {
          Utils.splitStringByByteSize(msg, 2048).forEach(ss -> {
            WeWorkService.sendMessage(r.getRobot_uuid(), ss);
          });
        });

    msteamsWorkflowMapper.selectList((new LambdaQueryWrapper<MsteamsWorkflow>())
        .eq(MsteamsWorkflow::getWorkflow_code, code).like(MsteamsWorkflow::getSites, site))
        .forEach(f -> {
          Utils.splitStringByByteSize(msg, 8192).forEach(ss -> {
            MSteamsService.sendMessage(f.getWorkflow_url(), ss);
          });
        });
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
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        PnStatus pn = list.get(i);
        msg.append("项目号: ")
            .append(pn.getProjectNO())
            .append("\n");
        msg.append("PN: ")
            .append(pn.getPN())
            .append(" [")
            .append(pn.getPNStatus())
            .append("]\n");
        msg.append(pn.getDesc1())
            .append(" ")
            .append(pn.getDesc2())
            .append(" ")
            .append(pn.getDesc3())
            .append("\n");
      }

      if (msg.length() > 0) {
        String s = "⚠︎PN状态不可用\n" + msg.toString();
        sendMessage(s, site, "SALES");
      }
    });

  }

  @Scheduled(cron = "0 0/5 9-11,13-17 * * MON-FRI")
  public void sendNewSalesOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    String lastRun = robotLastRunService.getLastRun("NEW_SALES_ORDER");

    Sites.forEach(site -> {
      List<TobeDelivery> list = noticeMapper.findNewSOSince(site, lastRun);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeDelivery order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("订单号: ")
            .append(order.getOrderNO())
            .append("\n");
        msg.append("PN: ")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量: ")
            .append(order.getQty())
            .append("\n");
        msg.append("金额: ")
            .append(order.getNetPrice().setScale(2))
            .append(" ")
            .append(order.getCurrency())
            .append("\n");
        msg.append("客户: ")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("交货日期: ")
            .append(Utils.formatDate(order.getRequestDate()))
            .append("\n");

        if (msg.length() > 0) {
          String s = "🤑💰新订单来了\n" + msg.toString();
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 10 10,14 * * MON-FRI")
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
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeDealWithOrderLine order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("类型: ")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg.append("订单日期: ")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg.append("PN: ")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量: ")
            .append(order.getQty())
            .append("\n");
        msg.append("客户: ")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😡新订单超7天未处理\n" + msg.toString();
          s += "\n更多待处理新订单,请查看https://192.168.10.12/#/Todo 中的NEW-ORDER";
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

    // for sales
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findTobeDealWithOrderLines(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeDealWithOrderLine order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("类型: ")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg.append("订单日期: ")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg.append("PN: ")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量: ")
            .append(order.getQty())
            .append("\n");
        msg.append("客户: ")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😟新订单超14天未处理\n" + msg.toString();
          s += "\n更多待处理新订单,请查看https://192.168.10.12/#/Todo 中的NEW-ORDER";
          sendMessage(s, site, "SALES");
        }
      }
    });

  }

  @Scheduled(cron = "0 15 10,14 * * MON-FRI")
  public void sendBomDealWithDelay() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("BOM_NO_DEAL");

    Sites.forEach(site -> {
      List<TobePurchaseBom> list = noticeMapper.findTobePurchaseBom(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobePurchaseBom line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("类型: ")
            .append(line.getOrderType())
            .append("\n");
        msg.append("工包: ")
            .append(line.getWorkOrderNO())
            .append(" ")
            .append(Utils.formatDate(line.getCreateDate()))
            .append("\n");
        msg.append("销售成品PN: ")
            .append(line.getForPN())
            .append("\n");
        msg.append("需采购PN: ")
            .append("[" + line.getBomSeq() + "] ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("需求数量: ")
            .append(line.getQty())
            .append("\n");
        msg.append("短缺数量: ")
            .append(line.getShortQty())
            .append("\n");
        msg.append("客户: ")
            .append(line.getCustomerCode())
            .append(" ")
            .append(line.getCustomerName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😡Bom项超14天未采购\n" + msg.toString();
          s += "\n更多待采购状态,请查看https://192.168.10.12/#/Todo 中的SHORT-BOM";
          sendMessage(s, site, "PURCHASE");
        }
      }
    });
  }

  @Scheduled(cron = "0 0 11 * * MON-FRI")
  public void sendNoAckPO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("PURCHASE_ORDER_NO_ACK_DATE");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findNoACkPO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeReceive line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号: ")
            .append(line.getPurchaseNO())
            .append("[" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购PN: ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("期望交付日期: ")
            .append(Utils.formatDate(line.getExpectDate()))
            .append("\n");
        msg.append("供应商: ")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😡采购单没有供应商交付日期\n" + msg.toString();
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 0 10 * * MON")
  public void sendLongTimeNoReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_RECEIVE");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findLongTimeNoReceive(site, -90);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeReceive line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号: ")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购PN: ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("期望交付日期: ")
            .append(Utils.formatDate(line.getExpectDate()))
            .append("\n");
        msg.append("供应商: ")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😬采购交货严重超期(大于90天)\n" + msg.toString();
          s += "\n更多收货状态,请查看https://192.168.10.12/#/Todo 中的RECEIVE";
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 0 15 * * MON-FRI")
  public void sendLongTimeNC() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NC");

    // for purchaser
    Sites.forEach(site -> {
      List<LongTimeNC> list = noticeMapper.findLongTimeNC(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        LongTimeNC line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号: ")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购PN: ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("首次检验日期: ")
            .append(Utils.formatDate(line.getFirstNCDate()))
            .append("\n");
        msg.append("供应商: ")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😬NC处理14天仍未出货\n" + msg.toString();
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 30 9 * * MON-FRI")
  public void sendLongTimeNoQC() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_QC");

    // for purchaser
    Sites.forEach(site -> {
      List<LongTimeNoQC> list = noticeMapper.findLongTimeNoQC(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        LongTimeNoQC line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号: ")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购PN: ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("收货日期: ")
            .append(Utils.formatDate(line.getReceiptDate()))
            .append("\n");
        msg.append("供应商: ")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😬收货14天仍未检验\n" + msg.toString();
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 0 9-11,13-17 * * MON-FRI")
  public void sendOrphanPO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("ORPHAN_PURCHASE_ORDER");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findOrphanPO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeReceive line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号: ")
            .append(line.getPurchaseNO())
            .append(" [" + line.getLine() + "] ")
            .append("\n");
        msg.append("采购PN: ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("采购单价: ")
            .append(line.getNetPrice().setScale(2))
            .append("\n")
            .append(line.getCurrency())
            .append("\n");
        msg.append("期望交付日期: ")
            .append(Utils.formatDate(line.getExpectDate()))
            .append("\n");
        msg.append("供应商: ")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("采购者: ")
            .append(line.getCreateUser())
            .append("\n");

        if (msg.length() > 0) {
          String s = "🧯🧯采购单项目不存在🧯🧯\n" + msg.toString();
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 20 10,14 * * MON-FRI")
  public void sendLongTimeNoInvoice() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_INVOICE");

    // for purchaser
    Sites.forEach(site -> {
      List<TobeInvoice> list = noticeMapper.findLongTimeNoInvoice(site, -14);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeInvoice line = list.get(i);
        msg.append("项目号: ")
            .append(line.getProjectNO())
            .append("\n");
        msg.append("采购单号: ")
            .append(line.getPurchaseNO())
            .append(" [" + line.getPurchaseLine() + "] ")
            .append("\n");
        msg.append("采购PN: ")
            .append(line.getPN())
            .append("\n")
            .append(line.getDescription())
            .append("\n");
        msg.append("采购者: ")
            .append(line.getPurchaser())
            .append("\n");
        msg.append("采购日期: ")
            .append(Utils.formatDate(line.getPurchaseDate()))
            .append("\n");
        msg.append("供应商: ")
            .append(line.getVendorCode())
            .append(" ")
            .append(line.getVendorName())
            .append("\n");
        msg.append("收货单号: ")
            .append(line.getReceiveNO())
            .append(" [")
            .append(line.getReceiveLine())
            .append("]\n");
        msg.append("收货人: ")
            .append(line.getReceiptor())
            .append("\n");
        msg.append("收货日期: ")
            .append(Utils.formatDate(line.getReceiveDate()))
            .append("\n");
        msg.append("价格: ")
            .append(line.getPrice().setScale(2))
            .append(" ").append(line.getCurrency())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😬发票严重超期90天(仅2024年后)\n" + msg.toString();
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 0/5 8-11,13-17 * * MON-FRI")
  public void sendDuplicatePurchaseOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    robotLastRunService.getLastRun("DUPLICATE_PURCHASE_ORDER");

    Sites.forEach(site -> {
      List<SuspectDuplicatedPO> list = noticeMapper.findDuplicatedPOBySite(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        SuspectDuplicatedPO order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("PN: ")
            .append(order.getPN())
            .append("\n");
        msg.append("第" + order.getSeq() + "次采购单: ")
            .append(order.getPurchaseNO())
            .append("-")
            .append(order.getPurchaseLine())
            .append(" ")
            .append(Utils.formatDate(order.getPurchaseDate()))
            .append(" ")
            .append(order.getPurchaser())
            .append("\n");
        msg.append("第" + order.getSeq() + "次采购数量: ")
            .append(order.getPurchaseQty())
            .append("\n");
        msg.append("第" + order.getSeq() + "次金额: ")
            .append(order.getCost().setScale(2))
            .append(" ")
            .append(order.getCurrency())
            .append("\n");
        msg.append("项目总采购数量: ")
            .append(order.getTotalPurchaseQty())
            .append("\n");
        msg.append("项目关联销售单数量: ")
            .append(order.getTotalSalesQty())
            .append("\n");

        if (msg.length() > 0) {
          String s = "😵疑似重复采购\n" + msg.toString();
          s += "\n在PurchaseLine或者ReceiveLine的Text中添加'AGAIN'可抑制通知";
          s += "\n更多重复采购,请查看https://192.168.10.12/#/SuspectDuplicateRecords";
          sendMessage(s, site, "PURCHASE");
        }
      }
    });

  }

  @Scheduled(cron = "0 0/5 8-11,13-17 * * MON-FRI")
  public void sendDuplicateReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    robotLastRunService.getLastRun("DUPLICATE_RECEIVE");

    Sites.forEach(site -> {
      List<SuspectDuplicatedRA> list = noticeMapper.findDuplicatedRABySite(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        SuspectDuplicatedRA ra = list.get(i);
        msg.append("项目号: ")
            .append(ra.getProjectNO())
            .append("\n");
        msg.append("PN: ")
            .append(ra.getPN())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货单: ")
            .append(ra.getReceiptNO())
            .append("-")
            .append(ra.getReceiptLine())
            .append(" ")
            .append(Utils.formatDate(ra.getReceiptDate()))
            .append(" ")
            .append(ra.getReceiptor())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货数量: ")
            .append(ra.getReceiptQty())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次金额: ")
            .append(ra.getReceiptAmount().setScale(2))
            .append(" ")
            .append(ra.getCurrency())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购单: ")
            .append(ra.getPurchaseNO())
            .append("-")
            .append(ra.getPurchaseLine())
            .append(" ")
            .append(Utils.formatDate(ra.getPurchaseDate()))
            .append(" ")
            .append(ra.getPurchaser())
            .append("\n");
        msg.append("项目总收货数量: ")
            .append(ra.getTotalReceiptQty())
            .append("\n");
        msg.append("项目总采购数量: ")
            .append(ra.getTotalPurchaseQty())
            .append("\n");
        msg.append("项目关联销售单数量: ")
            .append(ra.getTotalSalesQty())
            .append("\n");

      }
      if (msg.length() > 0) {
        String s = "😵疑似重复收货\n" + msg.toString();
        s += "\n在PurchaseLine或者ReceiveLine的Text中添加'AGAIN'可抑制通知";
        s += "\n更多重复收货,请查看https://192.168.10.12/#/SuspectDuplicateRecords";
        sendMessage(s, site, "PURCHASE");
      }
    });

  }

  @Scheduled(cron = "0 0/5 8-11,13-17 * * MON-FRI")
  public void sendDuplicateWO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    robotLastRunService.getLastRun("DUPLICATE_WORK_ORDER");

    Sites.forEach(site -> {
      List<String> list = noticeMapper.duplicatedWO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        String PJT = list.get(i);
        msg.append(PJT)
            .append("\n");
      }

      if (msg.length() > 0) {
        sendMessage("😵疑似重复工包\n" + msg.toString(), site, "PURCHASE");
      }
    });

  }

  @Scheduled(cron = "0 0/5 9-11,13-17 * * MON-FRI")
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
          .append("\n");
    }

    if (msg.length() > 0) {
      String s = "🤯珠海和上海采购单混用项目号\n" + msg.toString();
      sendMessage(s, "ZHU", "PURCHASE");
    }

    List<String> list2 = noticeMapper.mixWOProjectBetweenZHUAndYSH();
    final StringBuilder msg2 = new StringBuilder();

    for (int i = 0; i < list2.size(); i++) {
      String PJT = list2.get(i);
      msg2.append(PJT)
          .append("\n");
    }

    if (msg2.length() > 0) {
      String s = "🤯珠海和上海工包混用项目号\n" + msg2.toString();
      sendMessage(s, "ZHU", "PURCHASE");
    }
  }

  @Scheduled(cron = "0 0/2 8-11,13-17 * * MON-FRI")
  public void sendNewReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    String lastRun = robotLastRunService.getLastRun("NEW_RECEIVE");

    Sites.forEach(site -> {
      List<SuspectDuplicatedRA> list = suspectDuplicateDataService.findDuplicatedRABySite(site, lastRun);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        SuspectDuplicatedRA ra = list.get(i);
        msg.append("项目号: ")
            .append(ra.getProjectNO())
            .append("\n");
        msg.append("PN: ")
            .append(ra.getPN())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购单: ")
            .append(ra.getPurchaseNO())
            .append(" [")
            .append(ra.getPurchaseLine())
            .append("] ")
            .append(Utils.formatDate(ra.getPurchaseDate()))
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货单: ")
            .append(ra.getReceiptNO())
            .append(" [")
            .append(ra.getReceiptLine())
            .append("] ")
            .append(Utils.formatDate(ra.getReceiptDate()))
            .append("\n");
        msg.append("第" + ra.getSeq() + "次收货数量: ")
            .append(ra.getReceiptQty())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次金额: ")
            .append(ra.getReceiptAmount().setScale(2))
            .append(" ")
            .append(ra.getCurrency())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购人: ")
            .append(ra.getPurchaser())
            .append("\n");
        msg.append("项目总收货数量: ")
            .append(ra.getTotalReceiptQty())
            .append("\n");
      }

      if (msg.length() > 0) {
        String s = "🤯新收货通知\n" + msg.toString();
        sendMessage(s, site, "PURCHASE");
      }
    });

  }

  @Scheduled(cron = "0 30 9 * * MON,WED,FRI")
  public void sendLongTimeNoDelivery() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("LONG_TIME_NO_DELIVERY");

    Sites.forEach(site -> {
      List<TobeDelivery> list = noticeMapper.findLongTimeNoDelivery(site, -30);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeDelivery order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("订单类型: ")
            .append(order.getOrderType())
            .append("\n");
        msg.append("PN: ")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量: ")
            .append(order.getQty())
            .append("\n");
        msg.append("金额: ")
            .append(order.getNetPrice().setScale(2))
            .append(" ")
            .append(order.getCurrency())
            .append("\n");
        msg.append("客户: ")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg.append("要求交货日期: ")
            .append(Utils.formatDate(order.getRequestDate()))
            .append("\n");
        msg.append("计划交货日期: ")
            .append(Utils.formatDate(order.getPlanedDate()))
            .append("\n");
      }

      if (msg.length() > 0) {
        String s = "🧯🧯订单交付严重超期🧯🧯\n" + msg.toString();
        sendMessage(s, site, "SALES");
      }
    });

  }

  @Scheduled(cron = "0 30 10,14 * * MON-FRI")
  public void sendNoBomServiceOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("SERVICE_ORDER_NO_WO");

    // for customer support service
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findNOBomServiceOrder(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeDealWithOrderLine order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg.append("类型: ")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg.append("订单日期: ")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg.append("PN: ")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg.append("数量: ")
            .append(order.getQty())
            .append("\n");
        msg.append("客户: ")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");

      }

      if (msg.length() > 0) {
        String s = "😟售后订单建议创建工包\n" + msg.toString();
        sendMessage(s, site, "SALES");
      }
    });

  }

  @Scheduled(cron = "0 0 9-11,13-17 * * MON-FRI")
  public void sendTobeClosedWO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("TO_BE_CLOSE_WO");

    Sites.forEach(site -> {
      List<TobeClosedWO> list = noticeMapper.findTobeClosedWO(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeClosedWO ra = list.get(i);
        msg.append("项目号: ")
            .append(ra.getProjectNO())
            .append("\n");
        msg.append("工包: ")
            .append(ra.getWorkOrderNO())
            .append("\n");
      }

      if (msg.length() > 0) {
        String s = "🧹WO关闭提醒, 订单项目已关闭, 工包未关闭\n" + msg.toString();
        sendMessage(s, site, "PURCHASE");
      }
    });

  }

  @Scheduled(cron = "0 0 14 * * MON-FRI")
  public void sendDeadPurchaseLine() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }
    robotLastRunService.getLastRun("DEAD_PURCHASE_LINE");

    Sites.forEach(site -> {
      List<DeadPurchaseLine> list = noticeMapper.findDeadPurchaseLine(site);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size() && i <= 5; i++) { // only send 5 lines, because too many
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        DeadPurchaseLine o = list.get(i);
        msg.append("项目号: ")
            .append(o.getProjectNO())
            .append("\n");
        msg.append("订单: ")
            .append(o.getOrderNO())
            .append("\n");
        msg.append("订单日期: ")
            .append(Utils.formatDate(o.getOrderDate()))
            .append("\n");
        msg.append("采购单: ")
            .append(o.getPurchaseNO())
            .append(" [")
            .append(o.getPurchaseLine())
            .append("]\n");
        msg.append("采购人: ")
            .append(o.getPurchaser())
            .append("\n");
        msg.append("采购日期: ")
            .append(Utils.formatDate(o.getPurchaseDate()))
            .append("\n");

      }

      if (msg.length() > 0) {
        String s = "🧹采购单关闭提醒, 订单项目已关闭, 采购单未收货\n" + msg.toString();
        sendMessage(s, site, "PURCHASE");
      }
    });

  }

  @Scheduled(cron = "0 0/10 9-11,13-17 * * MON-FRI")
  public void sendPreAnalyzeProjectProfit() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    String lastRun = robotLastRunService.getLastRun("PRE_ANALYZE_PROJECT_PROFIT");

    Sites.forEach(site -> {
      List<ProjectProfit> list = noticeMapper.findPreAnalysesProjectProfit(site, lastRun);
      final StringBuilder msg = new StringBuilder();

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        ProjectProfit o = list.get(i);
        msg.append("项目号: ")
            .append(o.getProjectNO())
            .append("\n");
        msg.append("订单: ")
            .append(o.getOrderNO())
            .append("\n");
        msg.append("PN: ")
            .append(o.getPN())
            .append(" ")
            .append(o.getDescription())
            .append("\n");
        msg.append("数量: ")
            .append(o.getQty())
            .append("\n");
        msg.append("利润: ")
            .append(o.getProfit().setScale(2))
            .append("\n");

      }
      if (msg.length() > 0) {
        String s = "🥶预分析项目利润\n" + msg.toString();
        sendMessage(s, site, "PURCHASE");
      }
    });

  }

}
