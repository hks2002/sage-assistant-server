/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-25 15:19:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-02 21:09:43                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.service.HttpService;
import com.da.sageassistantserver.service.SagePrintService;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class SagePrintController {

    @PostMapping("/Data/PrintUUID")
    public JSONObject getPrintUUID(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "Report", required = false) String Report,
            @RequestParam(value = "ReportNO", required = false) String ReportNO,
            @RequestParam(value = "Opt", required = false, defaultValue = "") String Opt) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }

        if (SageActionHelper.getFunction(Report) == null) {
            return SageActionHelper.rtnObj(false, MsgTyp.WARN, "Report is not supported.");
        }

        if (ReportNO == null) {
            return SageActionHelper.paraRequired("ReportNO");
        }

        return SagePrintService.getPrintUUID(Auth, Report, ReportNO, Opt);
    }

    @PostMapping("/Data/ReportUUID")
    public JSONObject getReportUUID(
            @RequestHeader(value = "authorization", required = true) String Auth,
            @RequestParam(value = "PrintUUID", required = true) String PrintUUID) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }

        if (PrintUUID == null) {
            return SageActionHelper.paraRequired("PrintUUID");
        }

        return SagePrintService.getReportUUID(Auth, PrintUUID);
    }

    @GetMapping("/Data/ReportFile")
    public void getReportFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ReportUUID = request.getParameter("ReportUUID");
        String ReportNO = request.getParameter("ReportNO");

        OutputStream os = response.getOutputStream();
        try {
            if (ReportUUID == null) {
                os.write(SageActionHelper.paraRequired("ReportUUID").toJSONBBytes());
            }
            if (ReportNO == null) {
                ReportNO = "Untitled-Report";
            }
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + ReportNO + ".pdf\"");

            byte[] data = HttpService.getFile("https://192.168.10.62/print/$report('" + ReportUUID + "')");

            response.setContentLength(data.length);
            os.write(data);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                os.write("<H1>Handle report error!</H1>".getBytes());
            } catch (IOException e1) {
            }
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
