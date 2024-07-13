/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-25 15:19:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-06-27 19:14:30                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.model.User;
import com.da.sageassistantserver.service.LogService;
import com.da.sageassistantserver.service.SageLoginService;
import com.da.sageassistantserver.service.UserFuncService;
import com.da.sageassistantserver.service.UserService;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SageLoginController {

  @Autowired UserService userService;

  @Autowired UserFuncService userFuncService;

  @Autowired LogService logService;

  @PostMapping("/Data/Login")
  public JSONObject doLogin(@RequestHeader(value = "authorization", required = false) String Auth,
                            HttpServletRequest request) {

    HttpSession session = request.getSession(true);
    String finalAuth = Utils.getAuth(Auth, session);

    if (finalAuth == null) {
      return SageActionHelper.missingAuth();
    }
    // here skip the validation check
    String array[] = Utils.decodeBasicAuth(finalAuth).split(":");
    String loginName = array[0].toLowerCase();

    JSONObject loginRst = SageLoginService.doLogin(finalAuth);
    if (loginRst.getBoolean("success")) {
      session.setAttribute("authorization", finalAuth);
      // ignore cache login log, message is "Cache login success"
      if (loginRst.getString("msg").equals("Login success")) {
        CompletableFuture.supplyAsync(() -> {
          logService.addLog("LOGIN_SUCCESS", loginName);
          return null;
        });
      }

      User user = userService.getUserByLoginName(loginName);
      if (user == null) {
        JSONObject profileResult = SageLoginService.getProfile(finalAuth);
        if (profileResult.getBoolean("success")) {
          JSONObject profile = profileResult.getJSONObject("profile");

          userService.createUser(profile.getString("userId"), loginName, profile.getString("firstName"),
                                 profile.getString("lastName"), profile.getString("email"),
                                 profile.getString("language"));
        }
      }
      // skip update user profile

    } else {
      session.removeAttribute("authorization");
      CompletableFuture.supplyAsync(() -> {
        logService.addLog("LOGIN_FAILED", loginName);
        return null;
      });
    }

    return loginRst;
  }

  @PostMapping("/Data/Logout")
  public JSONObject doLogout(HttpServletRequest request,
                             @RequestHeader(value = "authorization", required = false) String Auth) {

    HttpSession session = request.getSession(true);
    String finalAuth = Utils.getAuth(Auth, session);

    if (finalAuth == null) {
      return SageActionHelper.missingAuth();
    }

    JSONObject rst = SageLoginService.doLogout(finalAuth);
    if (rst.getBoolean("success")) {

      CompletableFuture.supplyAsync(() -> {
        logService.addLog("LOGOUT_SUCCESS", session.getAttribute("userName").toString());
        return null;
      });
      session.invalidate();
    }
    return rst;
  }

  private JSONObject getProfileFromSage(String auth, HttpSession session) {
    JSONObject rst1 = SageLoginService.getProfile(auth);

    if (rst1.getBoolean("success")) {
      JSONObject profile = rst1.getJSONObject("profile");

      // Save user id to session
      session.setAttribute("sid", rst1.getJSONObject("profile").getString("userId"));
      session.setAttribute("loginUser", rst1.getJSONObject("profile").getString("userName"));
      userService.updateUserBySid(profile.getString("userId"), profile.getString("loginName"),
                                  profile.getString("firstName"), profile.getString("lastName"),
                                  profile.getString("email"), profile.getString("language"));
    }
    return rst1;
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
      session.setAttribute("loginUser", rst0.getJSONObject("profile").getString("userName"));

      // update profile in background
      CompletableFuture.supplyAsync(() -> {
        getProfileFromSage(finalAuth, session);
        return null;
      });
      return rst0;
    } else {
      return getProfileFromSage(finalAuth, session);
    }
  }

  private JSONObject getFunctionFromSage(String auth, HttpSession session) {
    JSONObject rst1 = SageLoginService.getFunction(auth);
    if (rst1.getBoolean("success")) {
      String sid = session.getAttribute("sid").toString();
      String functions = Utils.JSONArrayToString(rst1.getJSONArray("functions"));
      userFuncService.updateSageActionsBySid(sid, functions);
    }
    return rst1;
  }

  @PostMapping("/Data/Function")
  public JSONObject getFunction(HttpServletRequest request,
                                @RequestHeader(value = "authorization", required = false) String Auth) {

    HttpSession session = request.getSession(false);
    String finalAuth = Utils.getAuth(Auth, session);

    if (finalAuth == null) {
      return SageActionHelper.missingAuth();
    }

    JSONObject rst0 = userFuncService.getSageActionsByAuth(finalAuth);
    JSONArray functions = rst0.getJSONArray("functions");
    if (functions.size() > 0) {
      // get new from sage and update it to database in async
      CompletableFuture.supplyAsync(() -> {
        getFunctionFromSage(finalAuth, session);
        return null;
      });

      return rst0;
    } else {
      return getFunctionFromSage(finalAuth, session);
    }
  }
}
