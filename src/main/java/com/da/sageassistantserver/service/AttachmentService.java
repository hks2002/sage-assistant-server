/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-03-26 17:57:07                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2023-09-07 01:02:28                                                                       *
 * @FilePath              : src/main/java/com/da/sageassistantserver/service/AttachmentService.java                   *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.dao.AttachmentMapper;
import com.da.sageassistantserver.model.Attachment;
import com.da.sageassistantserver.utils.Utils;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Value("${attachment.path.linux}")
    private String attachmentPathLinux;

    @Value("${attachment.path.windows}")
    private String attachmentPathWindows;

    /**
     * google guava cache
     */
    public LoadingCache<String, JSONArray> cache = Caffeine
            .newBuilder()
            .initialCapacity(100)
            .maximumSize(1000)
            .expireAfterAccess(60, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, JSONArray>() {
                        @Override
                        public JSONArray load(String pn) {
                            // change / \ * ? to -
                            String pnShort = pn.replaceAll("(\\\\|\\*|\\/|\\?)", "-");
                            pnShort = Utils.makeShortPn(pnShort);

                            // Manual's folder without version end with '_', if pn with version,
                            // ManualsShort is empty, only return Drawing.
                            // And if Pn without version and Pn==PnRoot, both return Manual and Drawing
                            JSONArray ManualsShort = makeJsonArray(pn, "Manual", pnShort);
                            JSONArray DrawingShort = makeJsonArray(pn, "Drawing", pnShort);

                            JSONArray all = new JSONArray();

                            all.addAll(ManualsShort);
                            all.addAll(DrawingShort);

                            return all;
                        }
                    });

    public String handleFileUpload(
            String pn,
            String cat,
            final MultipartFile uploadFile,
            HttpServletResponse response) {
        String attachmentPath = Utils.isWin() ? attachmentPathWindows : attachmentPathLinux;

        // change / \ * ? to -
        String shortPn = Utils.makeShortPn(pn.replaceAll("(\\\\|\\*|\\/|\\?)", "-"));
        // Remove version
        String shortPnRoot = shortPn.replaceAll("_\\S+$", "");

        Path path = null;

        if (cat.equals("Manual")) {
            path = Paths.get(attachmentPath + "/Manual/" + shortPnRoot + "/" + uploadFile.getOriginalFilename());
        } else if (cat.equals("Drawing")) {
            path = Paths.get(attachmentPath + "/Drawing/" + shortPn + "/" + uploadFile.getOriginalFilename());
        } else {
            log.error("[Upload] [" + pn + "]" + path);
        }

        byte[] bytes;
        try {
            log.info("[Upload] [" + shortPn + "] " + path);
            bytes = uploadFile.getBytes();
            Files.write(path, bytes);

            return "Upload file with success!";
        } catch (IOException e) {
            log.error("[Upload] " + e.toString());
            response.setStatus(500);
            return "Upload file failed!";
        }
    }

    public String handleFileDelete(String file, HttpServletResponse response) {
        String attachmentPath = Utils.isWin() ? attachmentPathWindows : attachmentPathLinux;
        Path path = Paths.get(attachmentPath + file.replaceFirst("^(/File)", ""));

        try {
            Files.delete(path);
            log.info("[Delete] " + path);
            return "Delete file with success!";
        } catch (IOException e) {
            log.error("[Delete] " + e.toString());
            response.setStatus(500);
            return "Delete file failed!";
        }
    }

    public List<Attachment> getAttachmentPath(String pn) {
        if (pn.equals("NULL")) {
            return new ArrayList<>();
        }

        List<Attachment> listAttachment = attachmentMapper.getAttachment(pn);
        for (Attachment o : listAttachment) {
            // make the url with server path
            String pathOri = o.getPath();
            pathOri = pathOri.replace("\\", "/");

            Path path = Paths.get(pathOri, "");
            o.setFile(path.getFileName().toString());

            // stand path is /File/docs_sagex3/*
            if (pathOri.startsWith("[DOCS_SAGEX3]")) {
                pathOri = "/File/docs_sagex3/" + pathOri.substring(14);
            } else {
                // Here keep the path
            }

            o.setPath(pathOri);
        }
        return listAttachment;
    }

    public JSONArray getAttachmentPathForChina(String pn) {
        if (pn.equals("NULL")) {
            return new JSONArray();
        }

        return cache.get(pn);
    }

    private JSONArray makeJsonArray(String pn, String catStr, String folder) {
        JSONArray arr = new JSONArray();

        String attachmentPath = Utils.isWin() ? attachmentPathWindows : attachmentPathLinux;

        String[] files = Utils.findFiles(attachmentPath + "/" + catStr + "/" + folder);

        for (int i = 0, l = files.length; i < l; i++) {
            JSONObject obj = new JSONObject();
            obj.put("PN", pn);
            obj.put("Cat", catStr);
            obj.put("Path", "/File/" + catStr + "/" + folder + "/" + files[i]);
            obj.put("File", files[i]);
            obj.put("DocType", Utils.getFileExt(files[i]));

            arr.add(obj);
        }
        return arr;
    }
}
