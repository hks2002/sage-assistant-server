/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-25 15:19:00                                                                       *
 * @LastEditDate          : 2025-07-27 22:10:27                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.controller;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.service.HttpService;
import com.da.sage.assistant.service.SagePrintService;
import com.da.sage.assistant.utils.ResponseJson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/sa-api")
public class SagePrintController {

  @PostMapping("/Sage/PrintUUID")
  public JSONObject getPrintUUID(
      @RequestHeader(value = "authorization", required = false) String Auth,
      @RequestParam(value = "ReportType", required = false) String ReportType,
      @RequestParam(value = "ReportNO", required = false) String ReportNO,
      @RequestParam(value = "Opt", required = false, defaultValue = "") String Opt) {
    if (Auth == null) {
      return ResponseJson.missingAuth();
    }

    if (ReportType == null) {
      return ResponseJson.paraRequired("Report");
    }

    if (ReportNO == null) {
      return ResponseJson.paraRequired("ReportNO");
    }

    return SagePrintService.getPrintUUID(Auth, ReportType, ReportNO, Opt);
  }

  @PostMapping("/Sage/ReportUUID")
  public JSONObject getReportUUID(
      @RequestHeader(value = "authorization", required = true) String Auth,
      @RequestParam(value = "PrintUUID", required = true) String PrintUUID) {
    if (Auth == null) {
      return ResponseJson.missingAuth();
    }

    if (PrintUUID == null) {
      return ResponseJson.paraRequired("PrintUUID");
    }

    return SagePrintService.getReportUUID(Auth, PrintUUID);
  }

  @GetMapping("/Sage/ReportFile")
  public void getReportFile(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String ReportUUID = request.getParameter("ReportUUID");
    String ReportNO = request.getParameter("ReportNO");

    OutputStream os = response.getOutputStream();
    try {
      if (ReportUUID == null) {
        os.write(ResponseJson.paraRequired("ReportUUID").toJSONBBytes());
      }
      if (ReportNO == null) {
        ReportNO = "Untitled-Report";
      }
      response.setContentType("application/pdf");
      response.setHeader(
          "Content-Disposition",
          "inline; filename=\"" + ReportNO + ".pdf\"");

      byte[] data = HttpService.getFile(
          "https://192.168.10.62/print/$report('" + ReportUUID + "')");

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
