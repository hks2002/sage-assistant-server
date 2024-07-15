/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-07-04 09:39:40                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-15 09:05:53                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.webdav;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.model.BusinessPartnerName;
import com.da.sageassistantserver.model.User;
import com.da.sageassistantserver.model.UserFunc;
import com.da.sageassistantserver.service.LogService;
import com.da.sageassistantserver.service.SageLoginService;
import com.da.sageassistantserver.service.SupplierService;
import com.da.sageassistantserver.service.UserFuncService;
import com.da.sageassistantserver.service.UserService;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    UserService userService;
    @Autowired
    UserFuncService userFuncService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    LogService logService;

    final List<String> modifiableMethods = Arrays.asList("PUT", "POST", "PROPPATCH", "MKCOL", "MOVE");

    @Override
    protected boolean shouldNotFilter(@SuppressWarnings("null") HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/Data/Login") ||
                request.getRequestURI().startsWith("/Data/Logout") ||
                request.getRequestURI().startsWith("/Data/Analyses")
                        ? true
                        : false;
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("Microsoft-WebDAV-MiniRedir")) {
            return;
        }

        // // trace request code block
        // String path = getRelativePath(request);
        // log.info("{} {}", request.getMethod(), path);

        // Enumeration<String> headers = request.getHeaderNames();
        // while (headers.hasMoreElements()) {
        // String header = headers.nextElement();
        // log.info("Header {}: {}", header, request.getHeader(header));
        // }
        // Enumeration<String> attributes = request.getAttributeNames();
        // while (attributes.hasMoreElements()) {
        // String attribute = attributes.nextElement();
        // log.info("Attribute {}: {}", attribute, request.getAttribute(attribute));
        // }
        // Enumeration<String> parameters = request.getParameterNames();
        // while (parameters.hasMoreElements()) {
        // String parameter = parameters.nextElement();
        // log.info("Parameter {}: {}", parameter, request.getParameter(parameter));
        // }
        if (auth(request, response)) {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Check if the request is authorized.
     *
     * @note ❗❗❗ Window doesn't allow connect to no ssl webdav by default.. ❗❗❗
     *       <p>
     *       If Mapping disk or add web folder failed by default. To enable it,
     *       you need to set:
     *       <p>
     *       \HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\WebClient\Parameters
     *       <p>
     *       BasicAuthLevel to 2
     *       <p>
     *       Open CMD with Administrator privileges and run
     *       <p>
     *       net stop webclient
     *       <p>
     *       net start webclient
     * @return
     */
    public boolean auth(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        // if (httpRes != null) {
        // return false;
        // }
        HttpSession session = httpReq.getSession();

        String authorization = httpReq.getHeader("Authorization");
        if (authorization != null) {
            String array[] = Utils.decodeBasicAuth(authorization).split(":");
            String loginName = array[0].toLowerCase();
            session.setAttribute("loginName", loginName);

            // auth by Sage User
            JSONObject loginResult = SageLoginService.doLogin(authorization);
            if (loginResult.getBoolean("success")) {
                User user = userService.getUserByLoginName(loginName);
                if (user == null) {
                    JSONObject profileResult = SageLoginService.getProfile(authorization);
                    if (profileResult.getBoolean("success")) {
                        JSONObject profile = profileResult.getJSONObject("profile");
                        session.setAttribute("loginUser", profile.getString("userName"));

                        userService.createUser(
                                profile.getString("userId"), loginName,
                                profile.getString("firstName"), profile.getString("lastName"),
                                profile.getString("email"), profile.getString("language"));
                    }
                } else {
                    session.setAttribute("loginUser", user.getFirst_name() + " " + user.getLast_name());
                }

                // ignore cache login log, message is "Cache login success"
                if (loginResult.getString("msg").equals("Login success")) {
                    String userName = (String) session.getAttribute("loginUser");
                    String remoteIP = getTrueRemoteIp(httpReq);
                    logService.addLog("LOGIN_SUCCESS", loginName, userName, null, remoteIP);
                }

                setBusinessPartner(httpReq, httpRes);
                return authByPath(httpReq, httpRes);
            }

            return unauthorized(httpReq, httpRes);

        } else { // no authorization
            return unauthorized(httpReq, httpRes);
        }
    }

    private void setBusinessPartner(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        String bpCode = httpReq.getParameter("bpCode");
        if (bpCode != null) {
            List<BusinessPartnerName> bpNames = supplierService.getBusinessPartnerByCode(bpCode);
            if (bpNames.size() > 0) {
                httpReq.getSession().setAttribute("bpNames", bpNames.get(0).getBPName());
            }
        }
    }

    private boolean authByPath(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        // String path = getRelativePath(httpReq);
        String method = httpReq.getMethod();
        String loginName = (String) httpReq.getSession().getAttribute("loginName");

        List<UserFunc> userFuncs = userFuncService.getWebDavAccess(loginName);
        if (userFuncs.size() == 0) {
            boolean result = userFuncService.initWebDavAccessByLoginName(loginName);

            if (result) {
                logService.addLog("DOC_ACCESS_INIT_SUCCESS", loginName);
                userFuncs = userFuncService.getWebDavAccess(loginName);
            } else {
                logService.addLog("DOC_ACCESS_INIT_FAILED", loginName);
                return forbidden(httpReq, httpRes);
            }
        }

        // try again
        if (userFuncs.size() == 0) {
            return forbidden(httpReq, httpRes);
        } else {
            boolean allowWrite = false;
            boolean allowRead = false;
            for (UserFunc userFunc : userFuncs) {
                if (userFunc.getFunc_system().equals("WEBDAV") &&
                        userFunc.getFunc_code().equals("WRITE") &&
                        userFunc.isEnable()) {
                    allowWrite = true;
                } else if (userFunc.getFunc_system().equals("WEBDAV") &&
                        userFunc.getFunc_code().equals("READ") &&
                        userFunc.isEnable()) {
                    allowRead = true;
                }
            }

            if (allowWrite) {
                return allow(httpReq, httpRes);
            }
            if (allowRead) {
                return modifiableMethods.contains(method)
                        ? forbidden(httpReq, httpRes)
                        : allow(httpReq, httpRes);
            }
            // final return forbidden
            return forbidden(httpReq, httpRes);
        }
    }

    private boolean allow(HttpServletRequest req, HttpServletResponse res) {
        String user = (String) req.getSession().getAttribute("loginName");
        String userName = (String) req.getSession().getAttribute("loginUser");
        String path = getRelativePath(req);
        String remoteIP = getTrueRemoteIp(req);
        if (!path.endsWith("/")) {
            logService.addLog("DOC_ACCESS_SUCCESS", user, userName, path, remoteIP);
        }

        res.setStatus(200);
        return true;
    }

    private boolean unauthorized(HttpServletRequest req, HttpServletResponse res) {
        String user = (String) req.getSession().getAttribute("loginName");
        String userName = (String) req.getSession().getAttribute("loginUser");
        String path = getRelativePath(req);
        String remoteIP = getTrueRemoteIp(req);
        if (!path.endsWith("/")) {
            logService.addLog("DOC_ACCESS_FAILED", user, userName, path, remoteIP);
        }

        res.setStatus(401);
        res.setHeader("WWW-Authenticate", "Basic realm=\"DAV\"");
        return true;
    }

    private boolean forbidden(HttpServletRequest req, HttpServletResponse res) {
        String user = (String) req.getSession().getAttribute("loginName");
        String userName = (String) req.getSession().getAttribute("loginUser");
        String path = getRelativePath(req);
        String remoteIP = getTrueRemoteIp(req);
        if (!path.endsWith("/")) {
            logService.addLog("DOC_ACCESS_FAILED", user, userName, path, remoteIP);
        }

        res.setStatus(403);
        return false;
    }

    private String getTrueRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * This function is copied from WebDavServlet.getRelativePath()
     * 
     * @param request
     * @return
     */
    protected String getRelativePath(HttpServletRequest request) {
        String pathInfo;

        if (request.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
            // For includes, get the info from the attributes
            pathInfo = (String) request.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);
        } else {
            pathInfo = request.getPathInfo();
        }

        StringBuilder result = new StringBuilder();
        if (pathInfo != null) {
            result.append(pathInfo);
        }
        if (result.length() == 0) {
            result.append('/');
        }

        return result.toString();
    }

}
