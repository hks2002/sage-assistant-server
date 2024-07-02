/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-07-01 00:04:54                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-02 11:21:47                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.webdav;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.catalina.WebResource;
import org.apache.catalina.servlets.WebdavServlet;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.tomcat.util.security.Escape;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.da.sageassistantserver.service.DocsService;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "MyWebdavServlet", urlPatterns = { "/docs/*" }, initParams = {
        @WebInitParam(name = "listings", value = "true"),
        @WebInitParam(name = "maxDepth", value = "4"),
        @WebInitParam(name = "readonly", value = "false"),
        @WebInitParam(name = "debug", value = "0")
})
public class MyWebDav extends WebdavServlet {

    private String appName = "";
    private String appVersion = "";
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

            appName = null == prop.getProperty("project.name") ? "" : prop.getProperty("project.name");
            appVersion = null == prop.getProperty("project.version") ? "" : prop.getProperty("project.version");

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

    @Override
    protected InputStream renderHtml(HttpServletRequest request, String contextPath, WebResource resource,
            String encoding) throws IOException {

        // Prepare a writer to a buffered area
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter osWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        PrintWriter writer = new PrintWriter(osWriter);

        StringBuilder sb = new StringBuilder();

        String directoryWebappPath = resource.getWebappPath();
        WebResource[] entries = resources.listResources(directoryWebappPath);

        // rewriteUrl(contextPath) is expensive. cache result for later reuse
        String rewrittenContextPath = rewriteUrl(contextPath);

        // Render the page header
        sb.append("<!doctype html><html>\r\n");
        /*
         * TODO Activate this as soon as we use smClient with the request locales
         * sb.append("<!doctype html><html lang=\"");
         * sb.append(smClient.getLocale().getLanguage()).append("\">\r\n");
         */
        sb.append("<head>\r\n");
        sb.append("<title>");
        sb.append("Documents in [" + directoryWebappPath + "]");
        sb.append("</title>\r\n");
        sb.append("<style>");
        sb.append(org.apache.catalina.util.TomcatCSS.TOMCAT_CSS);
        sb.append("</style> ");
        sb.append("</head>\r\n");
        sb.append("<body>");
        sb.append("<h1>");
        sb.append("Documents in [" + directoryWebappPath + "]");

        // Render the link to our parent (if required)
        String parentDirectory = directoryWebappPath;
        if (parentDirectory.endsWith("/")) {
            parentDirectory = parentDirectory.substring(0, parentDirectory.length() - 1);
        }
        int slash = parentDirectory.lastIndexOf('/');
        if (slash >= 0) {
            String parent = directoryWebappPath.substring(0, slash);
            sb.append(" - <a href=\"");
            sb.append(rewrittenContextPath);
            if (parent.equals("")) {
                parent = "/";
            }
            sb.append(rewriteUrl(parent));
            if (!parent.endsWith("/")) {
                sb.append('/');
            }
            sb.append("\">");
            sb.append("<b>");
            sb.append(sm.getString("directory.parent", parent));
            sb.append("</b>");
            sb.append("</a>");
        }

        // always Render the link to root (if required)
        sb.append(" - <a href=\"");
        sb.append(rewrittenContextPath);
        sb.append(rewriteUrl("/"));
        sb.append("\">");
        sb.append("<b>");
        sb.append(sm.getString("directory.parent", "/"));
        sb.append("</b>");
        sb.append("</a>");
        // end of Render the link to root

        sb.append("<input type=\"search\" placeholder=\"Search\" id=\"search\" style=\"margin-left:20px;\">");
        sb.append("<script>\r\n");
        sb.append(
                Utils.readFileContent("webdavFun.js"));
        sb.append("</script>\r\n");

        sb.append("</h1>");
        sb.append("<hr class=\"line\">");

        sb.append("<table id=\"documentList\" width=\"100%\" cellspacing=\"0\""
                + " cellpadding=\"5\" align=\"center\">\r\n");

        // Render the column headings
        sb.append("<tr>\r\n");
        sb.append("<td align=\"left\"><font size=\"+1\"><strong>");

        sb.append(sm.getString("directory.filename"));

        sb.append("</strong></font></td>\r\n");
        sb.append("<td align=\"center\"><font size=\"+1\"><strong>");

        sb.append(sm.getString("directory.size"));

        sb.append("</strong></font></td>\r\n");
        sb.append("<td align=\"right\"><font size=\"+1\"><strong>");

        sb.append(sm.getString("directory.lastModified"));

        sb.append("</strong></font></td>\r\n");
        sb.append("</tr>");

        boolean shade = false;
        for (WebResource childResource : entries) {
            String filename = childResource.getName();
            if (filename.equalsIgnoreCase("WEB-INF") || filename.equalsIgnoreCase("META-INF")) {
                continue;
            }

            if (!childResource.exists()) {
                continue;
            }

            sb.append("<tr");
            if (shade) {
                sb.append(" bgcolor=\"#eeeeee\"");
            }
            sb.append(">\r\n");
            shade = !shade;

            sb.append("<td align=\"left\">&nbsp;&nbsp;\r\n");
            sb.append("<a href=\"");
            sb.append(rewrittenContextPath);
            sb.append(rewriteUrl(childResource.getWebappPath()));
            if (childResource.isDirectory()) {
                sb.append('/');
            }
            sb.append("\"><tt>");
            sb.append(Escape.htmlElementContent(filename));
            if (childResource.isDirectory()) {
                sb.append('/');
            }
            sb.append("</tt></a></td>\r\n");

            sb.append("<td align=\"right\"><tt>");
            if (childResource.isDirectory()) {
                sb.append("&nbsp;");
            } else {
                sb.append(renderSize(childResource.getContentLength()));
            }
            sb.append("</tt></td>\r\n");

            sb.append("<td align=\"right\"><tt>");
            sb.append(childResource.getLastModifiedHttp());
            sb.append("</tt></td>\r\n");

            sb.append("</tr>\r\n");
        }

        // Render the page footer
        sb.append("</table>\r\n");

        sb.append("<hr class=\"line\">");

        String readme = getReadme(resource, encoding);
        if (readme != null) {
            sb.append(readme);
            sb.append("<hr class=\"line\">");
        }

        if (showServerInfo) {
            sb.append("<h3>").append(appName + " " + appVersion).append("</h3>");
        }
        sb.append("</body>\r\n");
        sb.append("</html>\r\n");

        // Return an input stream to the underlying bytes
        writer.write(sb.toString());
        writer.flush();
        return new ByteArrayInputStream(stream.toByteArray());

    }
}
