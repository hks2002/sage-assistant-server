/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-16 23:24:10                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-01 15:23:39                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.webdav;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.model.BusinessPartnerName;
import com.da.sageassistantserver.model.User;
import com.da.sageassistantserver.model.UserFunc;
import com.da.sageassistantserver.service.LogService;
import com.da.sageassistantserver.service.SageLoginService;
import com.da.sageassistantserver.service.SupplierService;
import com.da.sageassistantserver.service.UserFuncService;
import com.da.sageassistantserver.service.UserService;
import com.da.sageassistantserver.utils.ITextTools;
import com.da.sageassistantserver.utils.ImageTools;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(filterName = "WebdavFilter", urlPatterns = "/docs/*")
public class WebdavFilter implements Filter {

  private UserFuncService userFuncService;
  private UserService userService;
  private SupplierService supplierService;
  private LogService logService;

  final List<String> skipFiles = Arrays.asList("desktop.ini", "Desktop.ini", "AutoRun.ini", "AutoRun.inf");
  final List<String> modifiableMethods = Arrays.asList("PUT", "POST", "PROPPATCH", "MKCOL", "MOVE");
  final List<String> waterMakerFileTypes = Arrays.asList("PDF", "JPG", "JPEG", "PNG", "TIF", "TIFF", "BMP");

  private String loginUser = null;
  private String businessPartner = null;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    ServletContext sc = filterConfig.getServletContext();
    WebApplicationContext ctx = (WebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);

    if (ctx != null) {
      log.info("[WebdavFilter] WebApplicationContext loaded");
      userFuncService = ctx.getBean(UserFuncService.class);
      userService = ctx.getBean(UserService.class);
      supplierService = ctx.getBean(SupplierService.class);
      logService = ctx.getBean(LogService.class);
    } else {
      log.error("[WebdavFilter] WebApplicationContext is null");
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) request;
    HttpServletResponse httpRes = (HttpServletResponse) response;
    String pathInfo = httpReq.getPathInfo();
    String fileName = "";
    String fileExt = "";
    String userAgent = httpReq.getHeader("User-Agent");

    if (pathInfo != null) {
      File file = new File(pathInfo);
      fileName = file.getName();
      fileExt = Utils.getFileExt(fileName);

      // skip some files, these files request will happen when User-Agent is
      // Microsoft-WebDAV-MiniRedir
      if (skipFiles.contains(fileName)) {
        log.debug("Skip file: {} ", fileName);
        return;
      }
    }

    // // trace request code block
    // log.info("xxxxxxx {} {} xxxxxxxx", httpReq.getMethod(), pathInfo);
    // BufferedReader br = new BufferedReader(httpReq.getReader());
    // String line;
    // while ((line = br.readLine()) != null) {
    // log.info(line);
    // }
    // Enumeration<String> headers = httpReq.getHeaderNames();
    // while (headers.hasMoreElements()) {
    // String header = headers.nextElement();
    // log.info("Header {}: {}", header, httpReq.getHeader(header));
    // }
    // Enumeration<String> attributes = httpReq.getAttributeNames();
    // while (attributes.hasMoreElements()) {
    // String attribute = attributes.nextElement();
    // log.info("Attribute {}: {}", attribute, httpReq.getAttribute(attribute));
    // }
    // Enumeration<String> parameters = httpReq.getParameterNames();
    // while (parameters.hasMoreElements()) {
    // String parameter = parameters.nextElement();
    // log.info("Parameter {}: {}", parameter, httpReq.getParameter(parameter));
    // }

    // Browser using GET to list folders
    // Windows disk mapping using PROPFIND to list folders
    if (auth(httpReq, httpRes)) {
      if (httpReq.getMethod().equals("GET") && waterMakerFileTypes.contains(fileExt)) {
        RequestWrapper reqWrapper = new RequestWrapper(httpReq);
        ResponseWrapper resWrapper = new ResponseWrapper(httpRes);

        resWrapper.addHeader("Cache-Control", "no-store");
        resWrapper.addHeader("Content-Disposition", "inline;");

        chain.doFilter(reqWrapper, resWrapper);

        byte[] data = resWrapper.getBytes();
        ByteArrayOutputStream bosForWaterMark = new ByteArrayOutputStream();

        String text = "DEDIENNE";
        if (loginUser != null) {
          text += " " + loginUser;
        }
        if (businessPartner != null) {
          text += " " + businessPartner;
        }
        text += " " + Utils.now();

        if (fileExt.equals("PDF")) {
          ITextTools.addTextFull(data, bosForWaterMark, text, 0.5f, 30);
          httpRes.setContentType("application/pdf"); // Must set it before write
        } else {
          // other files is JPG, JPEG, PNG, TIF, TIFF, BMP
          if (userAgent.contains("Microsoft-WebDAV-MiniRedir")) {
            // for webdav client, keep file format
            ImageTools.addTextFull(data, fileExt.toLowerCase(), bosForWaterMark, text, 0.5f, 30);
          } else {
            // for browser, change to PDF
            ByteArrayOutputStream bosForNewPdf = new ByteArrayOutputStream();
            ITextTools.toPdf(fileExt, data, bosForNewPdf);
            ITextTools.addTextFull(bosForNewPdf.toByteArray(), bosForWaterMark, text, 0.5f, 30);
            httpRes.setContentType("application/pdf"); // Must set it before write
          }
        }

        byte[] finalData = bosForWaterMark.toByteArray();

        httpRes.setContentLength(finalData.length); // Must set it before write
        httpRes.getOutputStream().write(finalData);

        httpRes.getOutputStream().flush();
        httpRes.getOutputStream().close();

      } else {
        // do nothing
        chain.doFilter(request, response);
      }
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
  private boolean auth(HttpServletRequest httpReq, HttpServletResponse httpRes) {
    // if (httpRes != null) {
    // return true;
    // }

    String method = httpReq.getMethod();
    String path = getRelativePath(httpReq);

    String authorization = httpReq.getHeader("Authorization");
    if (authorization != null) {
      String array[] = Utils.decodeBasicAuth(authorization).split(":");
      String loginName1 = array[0].toLowerCase();
      String loginName2 = null;

      // for supplier or customer, loginName is maybe business partner code
      List<BusinessPartnerName> bps = supplierService.getBusinessPartnerByCode(loginName1);
      if (bps.size() > 0) {
        businessPartner = bps.get(0).getBPName();
        String array2[] = array[1].split(" ");
        loginName2 = array2[0].toLowerCase();
        authorization = Utils.encodeBasicAuth(array2[0], array2[1]);
      } else {
        businessPartner = null;
      }

      // auth by Sage User
      JSONObject loginResult = SageLoginService.doLogin(authorization);
      if (loginResult.getBoolean("success")) {
        String finalLoginName = loginName2 != null ? loginName2 : loginName1;
        // ignore cache login log, message is "Cache login success"
        if (loginResult.getString("msg").equals("Login success")) {
          CompletableFuture.supplyAsync(() -> {
            logService.addLog("LOGIN_SUCCESS", finalLoginName);
            return null;
          });
        }

        User user = userService.getUserByLoginName(finalLoginName);
        if (user == null) {
          JSONObject profileResult = SageLoginService.getProfile(authorization);
          if (profileResult.getBoolean("success")) {
            JSONObject profile = profileResult.getJSONObject("profile");
            loginUser = profile.getString("userName");

            userService.createUser(
                profile.getString("userId"), finalLoginName,
                profile.getString("firstName"), profile.getString("lastName"),
                profile.getString("email"), profile.getString("language"));
          }
        } else {
          loginUser = user.getFirst_name() + " " + user.getLast_name();
        }

        return authByPath(httpRes, method, path, finalLoginName);
      }

      // Finally login fail
      CompletableFuture.supplyAsync(() -> {
        logService.addLog("LOGIN_FAILED", loginName1);
        return null;
      });
      return unauthorized(httpRes, loginName1, path);

    } else { // no authorization
      return unauthorized(httpRes, "unknown", path);
    }
  }

  private boolean authByPath(HttpServletResponse httpRes, String method, String path, String loginName) {
    List<UserFunc> userFuncs = userFuncService.getWebDavAccess(loginName);
    if (userFuncs.size() == 0) {
      boolean result = userFuncService.initWebDavAccessByLoginName(loginName);
      CompletableFuture.supplyAsync(() -> {
        if (result) {
          logService.addLog("DOC_ACCESS_INIT_SUCCESS", loginName);
        } else {
          logService.addLog("DOC_ACCESS_INIT_FAILED", loginName);
        }
        return null;
      });
      return forbidden(httpRes, loginName, path);

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
        return allow(httpRes, loginName, path);
      }
      if (allowRead) {
        return modifiableMethods.contains(method)
            ? forbidden(httpRes, loginName, path)
            : allow(httpRes, loginName, path);
      }
      // final return forbidden
      return forbidden(httpRes, loginName, path);

    }
  }

  private boolean unauthorized(HttpServletResponse res, String user, String path) {
    CompletableFuture.supplyAsync(() -> {
      logService.addLog("DOC_ACCESS_FAILED", user, path);
      return null;
    });

    res.setStatus(401);
    res.setHeader("WWW-Authenticate", "Basic realm=\"DAV\"");
    return false;
  }

  private boolean forbidden(HttpServletResponse res, String user, String path) {
    CompletableFuture.supplyAsync(() -> {
      logService.addLog("DOC_ACCESS_FAILED", user, path);
      return null;
    });

    res.setStatus(403);
    return false;
  }

  private boolean allow(HttpServletResponse res, String user, String path) {
    CompletableFuture.supplyAsync(() -> {
      logService.addLog("DOC_ACCESS_SUCCESS", user, path);
      return null;
    });

    res.setStatus(200);
    return true;
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
