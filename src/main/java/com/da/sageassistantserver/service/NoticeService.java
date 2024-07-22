/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2024-06-02 21:34:24                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-22 21:47:25                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.da.sageassistantserver.dao.NoticeMapper;
import com.da.sageassistantserver.dao.WeworkRobotMapper;
import com.da.sageassistantserver.model.DeadPurchaseLine;
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
  List<String> Sites = List.of("HKG", "ZHU", "YSH");

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

  @Scheduled(cron = "0 0 9 * * MON-FRI")
  public void sendPNNotActive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<PnStatus> list = pnService.findObsoletePnBySite(site);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "SALES"))
          .forEach(r -> {
            String s = "⚠︎PN状态不可用\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }

  }

  @Scheduled(cron = "0 0/5 8-11,13-17 * * MON-FRI")
  public void sendNewSalesOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();
    String lastRun = robotLastRunService.getLastRun("NEW_SALES_ORDER");

    Sites.forEach(site -> {
      List<TobeDelivery> list = noticeMapper.findNewSOSince(site, lastRun);

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg.append(site + ":\n-------------------\n");
        }

        TobeDelivery order = list.get(i);
        msg.append("项目号: ")
            .append(order.getProjectNO())
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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "🤑💰新订单来了\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 10 10,14 * * MON-FRI")
  public void sendSalesOrderDealWithDelay() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    // for purchaser
    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findTobeDealWithOrderLines(site, -7);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😡新订单超7天未处理\n" + msg.toString();

            s += "\n更多待处理新订单,请查看https://192.168.10.12/#/Todo 中的NEW-ORDER";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }

    // for sales
    final StringBuilder msg2 = new StringBuilder();
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findTobeDealWithOrderLines(site, -14);

      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          msg2.append(site + ":\n-------------------\n");
        }

        TobeDealWithOrderLine order = list.get(i);
        msg2.append("项目号: ")
            .append(order.getProjectNO())
            .append("\n");
        msg2.append("类型: ")
            .append(order.getOrderType())
            .append(" ")
            .append(order.getOrderCategory())
            .append("\n");
        msg2.append("订单日期: ")
            .append(Utils.formatDate(order.getOrderDate()))
            .append("\n");
        msg2.append("PN: ")
            .append(order.getPN())
            .append(" ")
            .append(order.getDescription())
            .append("\n");
        msg2.append("数量: ")
            .append(order.getQty())
            .append("\n");
        msg2.append("客户: ")
            .append(order.getCustomerCode())
            .append(" ")
            .append(order.getCustomerName())
            .append("\n");
        msg2.append("\n");

        if (i == (list.size() - 1)) {
          msg2.append("\n");
        }
      }
    });

    if (msg2.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "SALES"))
          .forEach(r -> {
            String s = "😟新订单超14天未处理\n" + msg2.toString();

            s += "\n更多待处理新订单,请查看https://192.168.10.12/#/Todo 中的NEW-ORDER";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 15 10,14 * * MON-FRI")
  public void sendBomDealWithDelay() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobePurchaseBom> list = noticeMapper.findTobePurchaseBom(site, -14);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😡Bom项超14天未采购\n" + msg.toString();

            s += "\n更多待采购状态,请查看https://192.168.10.12/#/Todo 中的SHORT-BOM";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0 11 * * MON-FRI")
  public void sendNoAckPO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    // for purchaser
    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findNoACkPO(site);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😡采购单没有供应商交付日期\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0 10 * * MON")
  public void sendLongTimeNoReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    // for purchaser
    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobeReceive> list = noticeMapper.findLongTimeNoReceive(site, -90);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😬采购交货严重超期(大于90天)\n" + msg.toString();

            s += "\n更多收货状态,请查看https://192.168.10.12/#/Todo 中的RECEIVE";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 20 10 * * MON-FRI")
  public void sendLongTimeNoInvoice() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    // for purchaser
    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobeInvoice> list = noticeMapper.findLongTimeNoInvoice(site, -90);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😬发票严重超期90天(仅2024年后)\n" + msg.toString();

            // s += "\n更多收货状态,请查看https://192.168.10.12/#/Todo 中的RECEIVE";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0/5 8-11,13-17 * * MON-FRI")
  public void sendDuplicatePurchaseOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();
    String lastRun = robotLastRunService.getLastRun("DUPLICATE_PURCHASE_ORDER");

    Sites.forEach(site -> {
      List<SuspectDuplicatedPO> list = suspectDuplicateDataService.findDuplicatedPOBySite(site, lastRun);

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
        msg.append("第" + order.getSeq() + "次采购人: ")
            .append(order.getPurchaser())
            .append("\n");
        msg.append("第" + order.getSeq() + "次采购单: ")
            .append(order.getPurchaseNO())
            .append(" [")
            .append(order.getPurchaseLine())
            .append("] ")
            .append(Utils.formatDate(order.getPurchaseDate()))
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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😵疑似重复采购\n" + msg.toString();
            s += "\n更多重复采购,请查看https://192.168.10.12/#/SuspectDuplicateRecords";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0/6 8-11,13-17 * * MON-FRI")
  public void sendDuplicateReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();
    String lastRun = robotLastRunService.getLastRun("DUPLICATE_RECEIVE");

    Sites.forEach(site -> {
      List<SuspectDuplicatedRA> list = suspectDuplicateDataService.findDuplicatedRABySite(site, lastRun);

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
            .append(ra.getReceiptAmount())
            .append(" ")
            .append(ra.getCurrency())
            .append("\n");
        msg.append("第" + ra.getSeq() + "次采购人: ")
            .append(ra.getPurchaser())
            .append("\n");
        msg.append("项目总收货数量: ")
            .append(ra.getTotalReceiptQty())
            .append("\n");
        msg.append("项目关联销售单数量: ")
            .append(ra.getTotalSalesQty())
            .append("\n");
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "😵疑似重复收货\n" + msg.toString();
            s += "\n更多重复收货,请查看https://192.168.10.12/#/SuspectDuplicateRecords";

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0/5 9-11,13-17 * * MON-FRI")
  public void sendMixProjectBetweenZHUAndYSH() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();

    List<String> list = noticeMapper.mixPOProjectBetweenZHUAndYSH();

    for (int i = 0; i < list.size(); i++) {
      String PJT = list.get(i);
      msg.append(PJT)
          .append("\n");
    }

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "🤯珠海和上海采购单混用项目号\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }

    List<String> list2 = noticeMapper.mixWOProjectBetweenZHUAndYSH();
    final StringBuilder msg2 = new StringBuilder();

    for (int i = 0; i < list2.size(); i++) {
      String PJT = list2.get(i);
      msg2.append(PJT)
          .append("\n");
    }

    if (msg2.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "🤯珠海和上海工包混用项目号\n" + msg2.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0/2 8-11,13-17 * * MON-FRI")
  public void sendNewReceive() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();
    String lastRun = robotLastRunService.getLastRun("NEW_RECEIVE");

    Sites.forEach(site -> {
      List<SuspectDuplicatedRA> list = suspectDuplicateDataService.findDuplicatedRABySite(site, lastRun);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "🤣收货通知\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 30 9 * * MON,WED,FRI")
  public void sendLongTimeNoDelivery() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobeDelivery> list = noticeMapper.findLongTimeNoDelivery(site, -30);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "SALES"))
          .forEach(r -> {
            String s = "🧯🧯订单交付严重超期🧯🧯\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 30 10,14 * * MON-FRI")
  public void sendNoBomServiceOrder() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    // for customer support service
    final StringBuilder msg = new StringBuilder();
    Sites.forEach(site -> {
      List<TobeDealWithOrderLine> list = noticeMapper.findNOBomServiceOrder(site);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "SALES"))
          .forEach(r -> {
            String s = "😟售后订单建议创建工包\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0 9-11,13-17 * * MON-FRI")
  public void sendTobeClosedWO() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<TobeClosedWO> list = noticeMapper.findTobeClosedWO(site);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PRODUCTION"))
          .forEach(r -> {
            String s = "🧹WO关闭提醒, 订单项目已关闭, 工包未关闭\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0 14 * * TUE,THU")
  public void sendDeadPurchaseLine() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();

    Sites.forEach(site -> {
      List<DeadPurchaseLine> list = noticeMapper.findDeadPurchaseLine(site);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "🧹采购单关闭提醒, 订单项目已关闭, 采购单未收货\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }

  @Scheduled(cron = "0 0/10 9-11,13-17 * * MON-FRI")
  public void sendPreAnalyzeProjectProfit() {
    if (!Utils.isZhuhaiServer()) {
      return;
    }

    final StringBuilder msg = new StringBuilder();
    String lastRun = robotLastRunService.getLastRun("PRE_ANALYZE_PROJECT_PROFIT");

    Sites.forEach(site -> {
      List<ProjectProfit> list = noticeMapper.findPreAnalysesProjectProfit(site, lastRun);

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
        msg.append("\n");

        if (i == (list.size() - 1)) {
          msg.append("\n");
        }
      }
    });

    if (msg.length() > 0) {
      weworkRobotMapper.selectList((new LambdaQueryWrapper<WeworkRobot>())
          .eq(WeworkRobot::getRobot_code, "PURCHASE"))
          .forEach(r -> {
            String s = "🥶🥶采购成本超过销售价格🥶🥶\n" + msg.toString();

            Utils.splitStringByByteSize(s, 2048).forEach(ss -> {
              WeWorkService.sendMessage(r.getRobot_uuid(), ss);
            });
          });
    }
  }
}
