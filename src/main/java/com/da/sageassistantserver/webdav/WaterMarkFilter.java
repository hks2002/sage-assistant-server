/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-06-16 23:24:10                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-13 21:43:24                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.webdav;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import com.da.sageassistantserver.utils.ITextTools;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(filterName = "WebdavFilter", urlPatterns = "/docs/*")
public class WaterMarkFilter implements Filter {

  final List<String> skipFiles = Arrays.asList("desktop.ini", "Desktop.ini", "AutoRun.ini", "AutoRun.inf");
  final List<String> waterMakerFileTypes = Arrays.asList("PDF", "JPG", "JPEG", "PNG", "TIF", "TIFF", "BMP");

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) request;
    HttpServletResponse httpRes = (HttpServletResponse) response;

    HttpSession session = httpReq.getSession();
    String loginUser = (String) session.getAttribute("loginUser");
    String bpNames = (String) session.getAttribute("bpNames");

    String pathInfo = httpReq.getPathInfo();
    String fileName = "";
    String fileExt = "";
    String fileNameNoExt = "";

    if (pathInfo != null) {
      File file = new File(pathInfo);
      fileName = file.getName();
      fileExt = Utils.getFileExt(fileName);
      fileNameNoExt = fileExt.length() > 0 ? fileName.substring(0, fileName.length() - fileExt.length() - 1) : fileName;

      // skip some files, these files request will happen when User-Agent is
      // Microsoft-WebDAV-MiniRedir
      if (skipFiles.contains(fileName)) {
        log.debug("Skip file: {} ", fileName);
        return;
      }
    }

    if (httpReq.getMethod().equals("GET") && waterMakerFileTypes.contains(fileExt)) {
      RequestWrapper reqWrapper = new RequestWrapper(httpReq);
      ResponseWrapper resWrapper = new ResponseWrapper(httpRes);

      String text = "DEDIENNE";
      if (loginUser != null) {
        text += " " + loginUser;
      }
      if (bpNames != null) {
        text += " " + bpNames;
      }
      text += " " + Utils.now();

      resWrapper.addHeader("Cache-Control", "no-store");
      resWrapper.addHeader("Content-Disposition",
          "inline; filename=\"" + URLEncoder.encode(fileNameNoExt, "UTF-8") + ' ' + text + '.' + fileExt + "\"");

      chain.doFilter(reqWrapper, resWrapper);

      byte[] data = resWrapper.getBytes();
      ByteArrayOutputStream bosForWaterMark = new ByteArrayOutputStream();
      boolean success = false;

      if (fileExt.equals("PDF")) {
        success = ITextTools.addTextFull(data, bosForWaterMark, text, 0.1f, 30);
        httpRes.setContentType("application/pdf"); // Must set it before write
      } else {
        // for browser, change to PDF
        ByteArrayOutputStream bosForNewPdf = new ByteArrayOutputStream();
        if (ITextTools.toPdf(fileExt, data, bosForNewPdf)) {
          success = ITextTools.addTextFull(bosForNewPdf.toByteArray(), bosForWaterMark, text, 0.1f, 30);
          httpRes.setContentType("application/pdf"); // Must set it before write
        } else {
          success = false;
        }
      }

      byte[] finalData = bosForWaterMark.toByteArray();

      if (success) {
        httpRes.setContentLength(finalData.length); // Must set it before write
        httpRes.getOutputStream().write(finalData);
      } else {
        httpRes.setContentLength(data.length); // Must set it before write
        httpRes.getOutputStream().write(data);
      }

      httpRes.getOutputStream().flush();
      httpRes.getOutputStream().close();

    } else {
      // do nothing
      chain.doFilter(request, response);
    }
  }

}
