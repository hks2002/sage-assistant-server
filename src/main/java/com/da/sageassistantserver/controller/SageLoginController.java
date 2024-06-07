/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-25 15:19:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-06-08 03:06:02                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.service.LogService;
import com.da.sageassistantserver.service.SageLoginService;
import com.da.sageassistantserver.service.UserFuncService;
import com.da.sageassistantserver.service.UserService;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class SageLoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserFuncService userFuncService;

    @Autowired
    LogService logService;

    @PostMapping("/Data/Login")
    public JSONObject doLogin(@RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "Report", required = false) String Report,
            HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        String finalAuth = Utils.getAuth(Report, session);

        if (finalAuth == null) {
            return SageActionHelper.missingAuth();
        }

        JSONObject rst = SageLoginService.doLogin(finalAuth);
        if (rst.getBoolean("success")) {
            session.setAttribute("authorization", finalAuth);
            CompletableFuture.supplyAsync(() -> {
                logService.addLog("Login_Success", session.getAttribute("userName").toString());
                return null;
            });
        } else {
            session.removeAttribute("authorization");
            CompletableFuture.supplyAsync(() -> {
                logService.addLog("Login_Fail", session.getAttribute("userName").toString());
                return null;
            });
        }

        return rst;
    }

    @PostMapping("/Data/Logout")
    public JSONObject doLogout(HttpServletRequest request,
            @RequestHeader(value = "authorization", required = false) String Auth,
            @RequestParam(value = "Report", required = false) String Report) {

        HttpSession session = request.getSession(true);
        String finalAuth = Utils.getAuth(Report, session);

        if (finalAuth == null) {
            return SageActionHelper.missingAuth();
        }

        JSONObject rst = SageLoginService.doLogout(finalAuth);
        if (rst.getBoolean("success")) {

            CompletableFuture.supplyAsync(() -> {
                logService.addLog("Logout_Success", session.getAttribute("userName").toString());
                return null;
            });
            session.invalidate();
        }
        return rst;
    }

    @PostMapping("/Data/Profile")
    public JSONObject getProfile(HttpServletRequest request,
            @RequestHeader(value = "authorization", required = false) String Auth) {

        HttpSession session = request.getSession(false);
        String finalAuth = Utils.getAuth(Auth, session);

        if (finalAuth == null) {
            return SageActionHelper.missingAuth();
        }

        JSONObject rst0 = userService.getProfile(finalAuth);
        if (rst0.getBoolean("success")) {
            // Save user id to session
            session.setAttribute("sid", rst0.getJSONObject("profile").getString("userId"));
            session.setAttribute("userName", rst0.getJSONObject("profile").getString("userName"));
            return rst0;
        } else {
            JSONObject rst1 = SageLoginService.getProfile(finalAuth);

            if (rst1.getBoolean("success")) {
                JSONObject profile = rst1.getJSONObject("profile");

                // Save user id to session
                session.setAttribute("sid", rst1.getJSONObject("profile").getString("userId"));
                session.setAttribute("sid", rst1.getJSONObject("profile").getString("userName"));
                userService.updateUser(profile.getString("userId"), profile.getString("loginName"),
                        profile.getString("firstName"), profile.getString("lastName"),
                        profile.getString("finalAuth"), profile.getString("email"),
                        profile.getString("language"));
            }
            return rst1;
        }
    }

    @PostMapping("/Data/Function")
    public JSONObject getFunction(HttpServletRequest request,
            @RequestHeader(value = "authorization", required = false) String Auth) {

        HttpSession session = request.getSession(false);
        String finalAuth = Utils.getAuth(Auth, session);

        if (finalAuth == null) {
            return SageActionHelper.missingAuth();
        }

        JSONObject rst0 = userFuncService.getFunctions(finalAuth);
        if (rst0.getBoolean("success") && rst0.getJSONArray("functions").size() > 0) {
            // get new from sage and update it to database in async
            CompletableFuture.supplyAsync(() -> {
                JSONObject rst1 = SageLoginService.getFunction(finalAuth);
                if (rst1.getBoolean("success")) {
                    String sid = session.getAttribute("sid").toString();
                    String functions = Utils.JSONArrayToString(rst1.getJSONArray("functions"));
                    userFuncService.updateUserFuncBySid(sid, functions);
                }
                return null;
            });

            return rst0;
        } else {
            JSONObject rst1 = SageLoginService.getFunction(finalAuth);
            if (rst1.getBoolean("success")) {
                // update it to database
                CompletableFuture.supplyAsync(() -> {
                    String sid = session.getAttribute("sid").toString();
                    String functions = Utils.JSONArrayToString(rst1.getJSONArray("functions"));
                    userFuncService.updateUserFuncBySid(sid, functions);
                    return null;
                });
            }
            return rst1;
        }
    }
}
