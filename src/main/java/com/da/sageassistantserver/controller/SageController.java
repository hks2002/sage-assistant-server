/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2022-03-25 15:19:00                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-06-02 13:02:10                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.controller;

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

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.service.HttpService;
import com.da.sageassistantserver.service.SageService;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class SageController {

    public JSONObject missingAuth() {
        return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "Authorization is required.");
    }

    public JSONObject paraRequired(String name) {
        return SageActionHelper.rtnObj(false, MsgTyp.ERROR, name + " is required.");
    }

    @PostMapping("/Data/Login")
    public JSONObject doLogin(@RequestHeader(value = "authorization", required = false) String Auth,
            HttpServletRequest request) {
        if (Auth == null) {
            return missingAuth();
        }

        JSONObject rst = SageService.doLogin(Auth);

        if (rst.getBoolean("success")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("authorization", rst.getString(Auth));
        }

        return SageService.doLogin(Auth);
    }

    @PostMapping("/Data/Profile")
    public JSONObject getProfile(@RequestHeader(value = "authorization", required = false) String Auth) {
        if (Auth == null) {
            return missingAuth();
        }

        return SageService.getProfile(Auth);
    }

    @PostMapping("/Data/Function")
    public JSONObject getFunction(@RequestHeader(value = "authorization", required = false) String Auth) {
        if (Auth == null) {
            return missingAuth();
        }

        return SageService.getFunction(Auth);
    }

    @PostMapping("/Data/PrintUUID")
    public JSONObject getPrintUUID(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "Report", required = false) String Report,
            @RequestParam(value = "ReportNO", required = false) String ReportNO,
            @RequestParam(value = "Opt", required = false, defaultValue = "") String Opt) {
        if (Auth == null) {
            return missingAuth();
        }

        if (SageActionHelper.getFunction(Report) == null) {
            return SageActionHelper.rtnObj(false, MsgTyp.WARN, "Report is not supported.");
        }

        if (ReportNO == null) {
            return paraRequired("ReportNO");
        }

        return SageService.getPrintUUID(Auth, Report, ReportNO, Opt);
    }

    @PostMapping("/Data/ReportUUID")
    public JSONObject getReportUUID(
            @RequestHeader(value = "authorization", required = true) String Auth,
            @RequestParam(value = "PrintUUID", required = true) String PrintUUID) {
        if (Auth == null) {
            return missingAuth();
        }

        if (PrintUUID == null) {
            return paraRequired("PrintUUID");
        }

        return SageService.getReportUUID(Auth, PrintUUID);
    }

    @GetMapping("/Data/ReportFile")
    public String getReportFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ReportUUID = request.getParameter("ReportUUID");
            String ReportNO = request.getParameter("ReportNO");

            OutputStream out = response.getOutputStream();

            if (ReportUUID == null) {
                out.write(paraRequired("ReportUUID").toJSONBBytes());
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
            } catch (IOException e1) {
            }
        }
        return null;
    }

    @PutMapping("/Data/SageSADReady")
    public JSONObject updateSageSADReady(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Ready", required = false) String Ready) {
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

        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA51", Ready);
    }

    @PutMapping("/Data/SageDeliveryReady")
    public JSONObject updateSageDeliveryReady(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Ready", required = false) String Ready) {
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
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA52", Ready);
    }

    @PutMapping("/Data/SageProductReady")
    public JSONObject updateSageProductReady(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Flag", required = false) String Flag) {
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
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA54", Flag);
    }

    @PutMapping("/Data/SageProjectStatus")
    public JSONObject updateSageProjectStatus(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Status", required = false) String Status) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA72", Status);
    }

    @PutMapping("/Data/SageProjectBlockReason")
    public JSONObject updateSageProjectBlockReason(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Reason", required = false) String Reason) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA73", Reason);
    }

    @PutMapping("/Data/SageProjectComment")
    public JSONObject updateSageProjectComment(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Comment", required = false) String Comment) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA77", Comment);
    }

    @PutMapping("/Data/SageProjectAction")
    public JSONObject updateSageProjectAction(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Action", required = false) String Action) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA78", Action);
    }

    @PutMapping("/Data/SageDeliveryPlanDate")
    public JSONObject updateSageDeliveryPlanDate(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "PlanDate", required = false) String PlanDate) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA29", PlanDate);
    }

    @PutMapping("/Data/SagePurchaseAckDate")
    public JSONObject updateSagePurchaseAckDate(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "AckDate", required = false) String AckDate) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "PurchaseOrder", OrderNO, Line, "CA66", AckDate);
    }

    @PutMapping("/Data/SagePurchaseComment")
    public JSONObject updateSagePurchaseComment(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Comment", required = false) String Comment) {
        if (Auth == null) {
            return missingAuth();
        }
        if (OrderNO == null) {
            return paraRequired("OrderNO");
        }
        if (Line == null) {
            return paraRequired("Line");
        }
        return SageService.updateSageField(Auth, "PurchaseOrder", OrderNO, Line, "CA68", Comment);
    }
}
