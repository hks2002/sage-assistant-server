/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-25 15:19:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-13 23:26:41                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.service.SageActionService;
import com.da.sageassistantserver.utils.SageActionHelper;

@CrossOrigin
@RestController
public class SageRecorderController {

    @PutMapping("/Data/SageSADReady")
    public JSONObject updateSageSADReady(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Ready", required = false) String Ready) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        if (Ready == null) {
            return SageActionHelper.paraRequired("Ready");
        }

        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA51", Ready);
    }

    @PutMapping("/Data/SageDeliveryReady")
    public JSONObject updateSageDeliveryReady(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Ready", required = false) String Ready) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        if (Ready == null) {
            return SageActionHelper.paraRequired("Ready");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA52", Ready);
    }

    @PutMapping("/Data/SageProductReady")
    public JSONObject updateSageProductReady(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Flag", required = false) String Flag) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        if (Flag == null) {
            return SageActionHelper.paraRequired("Flag");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA54", Flag);
    }

    @PutMapping("/Data/SageProjectStatus")
    public JSONObject updateSageProjectStatus(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Status", required = false) String Status) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA72", Status);
    }

    @PutMapping("/Data/SageProjectBlockReason")
    public JSONObject updateSageProjectBlockReason(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Reason", required = false) String Reason) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA73", Reason);
    }

    @PutMapping("/Data/SageProjectComment")
    public JSONObject updateSageProjectComment(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Comment", required = false) String Comment) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA77", Comment);
    }

    @PutMapping("/Data/SageProjectAction")
    public JSONObject updateSageProjectAction(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Action", required = false) String Action) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA78", Action);
    }

    @PutMapping("/Data/SageDeliveryPlanDate")
    public JSONObject updateSageDeliveryPlanDate(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "PlanDate", required = false) String PlanDate) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "SalesOrder", OrderNO, Line, "EA29", PlanDate);
    }

    @PutMapping("/Data/SagePurchaseAckDate")
    public JSONObject updateSagePurchaseAckDate(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "AckDate", required = false) String AckDate) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "PurchaseOrder", OrderNO, Line, "CA66", AckDate);
    }

    @PutMapping("/Data/SagePurchaseComment")
    public JSONObject updateSagePurchaseComment(
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "OrderNO", required = false) String OrderNO,
            @RequestParam(value = "Line", required = false) Integer Line,
            @RequestParam(value = "Comment", required = false) String Comment) {
        if (Auth == null) {
            return SageActionHelper.missingAuth();
        }
        if (OrderNO == null) {
            return SageActionHelper.paraRequired("OrderNO");
        }
        if (Line == null) {
            return SageActionHelper.paraRequired("Line");
        }
        return SageActionService.updateSageField(Auth, "PurchaseOrder", OrderNO, Line, "CA68", Comment);
    }
}
