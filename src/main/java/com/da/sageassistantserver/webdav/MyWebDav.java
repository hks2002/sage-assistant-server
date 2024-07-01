/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-07-01 00:04:54                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-01 15:11:52                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.webdav;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.catalina.servlets.WebdavServlet;
import org.apache.catalina.webresources.DirResourceSet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.da.sageassistantserver.service.DocsService;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "MyWebdavServlet", urlPatterns = { "/docs/*" }, initParams = {
        @WebInitParam(name = "listings", value = "true"),
        @WebInitParam(name = "maxDepth", value = "4"),
        @WebInitParam(name = "readonly", value = "false"),
        @WebInitParam(name = "debug", value = "0")
})
public class MyWebDav extends WebdavServlet {
    /**
     * Path to the web application root
     */
    private String docBase;

    /**
     * Path to the webdav attachment path
     */
    private String attachmentPath;
    private int subFolderDeep = 2;
    private int subFolderLen = 3;
    private int updateDocInfoDelay = 60000;
    private int updateDocInfoRepeat = 600000;

    private DocsService docsService;

    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext sc = getServletContext();
        WebApplicationContext ctx = (WebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if (ctx != null) {
            log.info("[Webdav] WebApplicationContext loaded");
            docsService = ctx.getBean(DocsService.class);
        } else {
            log.error("[Webdav] WebApplicationContext is null");
        }

        try {
            Properties prop = new Properties();
            FileInputStream iso = new FileInputStream(
                    getServletContext().getClassLoader().getResource("").getPath() + "application.properties");
            prop.load(iso);
            if (Utils.isWin()) {
                attachmentPath = null == prop.getProperty("attachment.path.windows") ? ""
                        : prop.getProperty("attachment.path.windows");
            } else {
                attachmentPath = null == prop.getProperty("attachment.path.linux") ? ""
                        : prop.getProperty("attachment.path.linux");
            }
            if (attachmentPath != "") {
                File additionPath = new File(attachmentPath);
                DirResourceSet dirResourceSet = new DirResourceSet(super.resources, "/",
                        additionPath.getAbsolutePath(),
                        "/");
                super.resources.addPostResources(dirResourceSet);
            }
            // super.resources.setCacheMaxSize(10000000);
            // super.resources.setCacheTtl(1000 * 60 * 15); // 15 minutes
            docBase = super.resources.getContext().getDocBase();

            subFolderDeep = null == prop.getProperty("attachment.path.folder.deep") ? 2
                    : Integer.parseInt(prop.getProperty("attachment.path.folder.deep"));
            subFolderLen = null == prop.getProperty("attachment.path.folder.len") ? 3
                    : Integer.parseInt(prop.getProperty("attachment.path.folder.len"));
            updateDocInfoDelay = null == prop.getProperty("update.doc.info.delay") ? 60000
                    : Integer.parseInt(prop.getProperty("update.doc.info.delay"));
            updateDocInfoRepeat = null == prop.getProperty("update.doc.info.repeat") ? 300000
                    : Integer.parseInt(prop.getProperty("update.doc.info.repeat"));

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updateDocInfo();
                }
            }, updateDocInfoDelay, updateDocInfoRepeat);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("[Webdav] " + e.toString());
        }

    }

    protected void updateDocInfo() {
        try {
            // log.info("[Webdav] Start to update doc info, {} {} {} {}", docBase,
            // attachmentPath, subFolderDeep,
            // subFolderLen);
            // update doc info first, this amount of files, would be less
            docsService.updateDocInfo(new File(docBase));
            Utils.moveFiles(new File(docBase), new File(attachmentPath), subFolderDeep, subFolderLen);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[Webdav] " + e.toString());
        }
    }

}
