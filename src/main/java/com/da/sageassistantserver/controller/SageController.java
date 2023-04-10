/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-25 15:19:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-21 14:13:23                                                                      *
 * @FilePath              : src/main/java/com/da/sage/assistant/controller/SageController.java                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import com.da.sageassistantserver.service.HttpService;
import com.da.sageassistantserver.service.SageService;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SageController {

    public String missingAuth() {
        return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "Authorization is required.").toJSONString();
    }

    public String paraRequired(String name) {
        return SageActionHelper.rtnObj(false, MsgTyp.ERROR, name + " is required.").toJSONString();
    }

    @PostMapping("/Data/Login")
    public String doLogin(@RequestHeader(value = "authorization", required = false) String Auth) {
        if (Auth == null) {
            return missingAuth();
        }

        return SageService.doLogin(Auth).toJSONString();
    }

    @PostMapping("/Data/Profile")
    public String getProfile(@RequestHeader(value = "authorization", required = false) String Auth) {
        if (Auth == null) {
            return missingAuth();
        }

        return SageService.getProfile(Auth).toJSONString();
    }

    @PostMapping("/Data/Function")
    public String getFunction(@RequestHeader(value = "authorization", required = false) String Auth) {
        if (Auth == null) {
            return missingAuth();
        }

        return SageService.getFunction(Auth).toJSONString();
    }

    @PostMapping("/Data/PrintUUID")
    public String getPrintUUID(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "Report", required = false) String Report,
        @RequestParam(value = "ReportNO", required = false) String ReportNO,
        @RequestParam(value = "Opt", required = false, defaultValue = "") String Opt
    ) {
        if (Auth == null) {
            return missingAuth();
        }

        if (SageActionHelper.getFunction(Report) == null) {
            return SageActionHelper.rtnObj(false, MsgTyp.WARN, "Report is not supported.").toJSONString();
        }

        if (ReportNO == null) {
            return paraRequired("ReportNO");
        }

        return SageService.getPrintUUID(Auth, Report, ReportNO, Opt).toJSONString();
    }

    @PostMapping("/Data/ReportUUID")
    public String getReportUUID(
        @RequestHeader(value = "authorization", required = true) String Auth,
        @RequestParam(value = "PrintUUID", required = true) String PrintUUID
    ) {
        if (Auth == null) {
            return missingAuth();
        }

        if (PrintUUID == null) {
            return paraRequired("PrintUUID");
        }

        return SageService.getReportUUID(Auth, PrintUUID).toJSONString();
    }

    @GetMapping("/Data/ReportFile")
    public String getReportFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ReportUUID = request.getParameter("ReportUUID");
            String ReportNO = request.getParameter("ReportNO");

            OutputStream out = response.getOutputStream();

            if (ReportUUID == null) {
                out.write(paraRequired("ReportUUID").getBytes());
            }
            if (ReportNO == null) {
                ReportNO = "Untitled-Report";
            }
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + ReportNO + ".pdf\"");

            InputStream in = HttpService.getFile("https://192.168.10.62/print/$report('" + ReportUUID + "')").body();

            int len = 0;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write("<H1>Handle report error!</H1>");
            } catch (IOException e1) {}
        }
        return null;
    }

    @PutMapping("/Data/SageSADReady")
    public String updateSageSADReady(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Ready", required = false) String Ready
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        if (Ready == null) {
            return paraRequired("Ready");
        }

        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA51", Ready).toJSONString();
    }

    @PutMapping("/Data/SageDeliveryReady")
    public String updateSageDeliveryReady(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Ready", required = false) String Ready
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        if (Ready == null) {
            return paraRequired("Ready");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA52", Ready).toJSONString();
    }

    @PutMapping("/Data/SageProductReady")
    public String updateSageProductReady(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Flag", required = false) String Flag
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        if (Flag == null) {
            return paraRequired("Flag");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA54", Flag).toJSONString();
    }

    @PutMapping("/Data/SageProjectStatus")
    public String updateSageProjectStatus(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Status", required = false) String Status
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA72", Status).toJSONString();
    }

    @PutMapping("/Data/SageProjectBlockReason")
    public String updateSageProjectBlockReason(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Reason", required = false) String Reason
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA73", Reason).toJSONString();
    }

    @PutMapping("/Data/SageProjectComment")
    public String updateSageProjectComment(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Comment", required = false) String Comment
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA77", Comment).toJSONString();
    }

    @PutMapping("/Data/SageProjectAction")
    public String updateSageProjectAction(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Action", required = false) String Action
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA78", Action).toJSONString();
    }

    @PutMapping("/Data/SageDeliveryPlanDate")
    public String updateSageDeliveryPlanDate(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "PlanDate", required = false) String PlanDate
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA29", PlanDate).toJSONString();
    }

    @PutMapping("/Data/SagePurchaseAckDate")
    public String updateSagePurchaseAckDate(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "AckDate", required = false) String AckDate
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "PurchaseOrder", OrderNO, Line, "CA66", AckDate).toJSONString();
    }

    @PutMapping("/Data/SagePurchaseComment")
    public String updateSagePurchaseComment(
        @RequestHeader(value = "authorization", required = false) String Auth,
        @RequestParam(value = "OrderNO", required = false) String OrderNO,
        @RequestParam(value = "Line", required = false) Integer Line,
        @RequestParam(value = "Comment", required = false) String Comment
    ) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "PurchaseOrder", OrderNO, Line, "CA68", Comment).toJSONString();
    }
}
