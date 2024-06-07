/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-11-23 20:45:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-06-07 17:10:06                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.Action;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;

@Service
public class SagePrintService {
    /*
     * Return Report print UUID
     */
    private static JSONObject doActPrint(
            String auth,
            String function,
            String trans,
            String sessionId,
            String xid,
            String val,
            String rptSelect) {
        // Step one: go to recorder
        JSONObject rtn = SageActionService.doSageAct(auth, function, trans, sessionId,
                SageActionHelper.set("B", xid, 0, val), null);
        if (!rtn.getBooleanValue("success")) {
            if (rtn.getString("msgTyp").equals("WARN")) {
                // rewrite message, original message is ""
                rtn.put("msg", "Recorder not found");
            }
            return rtn;
        }

        // Step two: go to print page
        rtn = SageActionService.doSageAct(auth, function, trans, sessionId,
                SageActionHelper.act(Action.ENTER_PRINT_PAGE), null);
        if (!rtn.getBoolean("success")) {
            return rtn;
        }

        // Step three: select print type, if val2 is null, then select default
        if (rptSelect != null && !rptSelect.isBlank()) {
            rtn = SageActionService.doSageAct(auth, function, trans, sessionId,
                    SageActionHelper.popSel("C", "bA", 0, rptSelect), null);
            if (!rtn.getBoolean("success")) {
                return rtn;
            }
        }

        // Step four: print
        rtn = SageActionService.doSageAct(auth, function, trans, sessionId, SageActionHelper.act(Action.PRINT), null);
        if (!rtn.getBoolean("success")) {
            return rtn;
        }

        // Step five: get uuid
        JSONObject obj = JSONObject.parseObject(rtn.getString("result"));
        String uuid = obj
                .getJSONObject("sap")
                .getJSONObject("jobs")
                .getJSONObject("report")
                .getJSONArray("submitted")
                .getJSONObject(0)
                .getString("uuid");

        rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
        rtn.put("printUUID", uuid);

        return rtn;
    }

    public static JSONObject getPrintUUID(String auth, String report, String reportNO, String opt) {
        String function = SageActionHelper.getFunction(report);

        // trans
        String trans = switch (function) {
            case "GESSOH" -> SageActionHelper.getSalesOrderTransaction(reportNO);
            case "GESSIH" -> SageActionHelper.getInvoiceTransaction(reportNO);
            case "GESPOH" -> SageActionHelper.getPurchaseTransaction(reportNO);
            default -> "";
        };

        String rptSelect = switch (function) {
            case "GESSDH" -> "7~1:BONLIV!1";
            case "GESPOH" -> (opt == null || opt.isEmpty()) ? "7~1:BONTTC2!1" : "7~1:BONCDE2!1";
            default -> "";
        };

        JSONObject session = SageLoginService.getSageSessionCache(auth, function, trans);
        // if is not successful, return it
        if (!session.getBooleanValue("success")) {
            return session;
        }
        String sessionId = session.getString("SessionId");
        String reportNOField = session.getString("xid");

        return doActPrint(auth, function, trans, sessionId, reportNOField, reportNO, rptSelect);
    }

    public static JSONObject getReportUUID(String auth, String printUUID) {
        int statusCode = 202;
        do {
            // Sleep 0.5 seconds
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            statusCode = HttpService
                    .request(
                            "https://192.168.10.62/print/srvedi01.dedaero.lan:1890/$getState('" + printUUID + "')",
                            "GET",
                            null,
                            auth)
                    .statusCode();
        } while (statusCode == 202); // 202 creating, 201 created

        // location style:
        // /print/$report('36144e17-6272-442b-aa4c-4c2e667fbe67')
        String reportUUID = HttpService
                .request(
                        "https://192.168.10.62/print/srvedi01.dedaero.lan:1890/$getReport('" + printUUID + "')",
                        "GET",
                        null,
                        auth)
                .headers()
                .firstValue("location")
                .orElse("");

        Pattern p = Pattern.compile("[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}");
        Matcher m = p.matcher(reportUUID);
        if (m.find()) {
            JSONObject rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
            rtn.put("reportUUID", m.group(0));
            return rtn;
        } else {
            return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "Get report uuid Error");
        }
    }

}
