/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:57:07                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-03 02:26:49                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.sageassistantserver.dao.DocsMapper;
import com.da.sageassistantserver.model.Docs;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocsService {

    @Autowired
    private DocsMapper docsMapper;
    @Value("${attachment.path.linux}")
    private String linuxPath;
    @Value("${attachment.path.windows}")
    private String windowsPath;
    @Value("${attachment.path.folder.deep}")
    private int subFolderDeep;
    @Value("${attachment.path.folder.len}")
    private int subFolderLen;

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private LogService logService;

    public Long createDocs(Docs docs) {
        docs.setCreate_at(new Timestamp(System.currentTimeMillis()));
        docs.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        docs.setCreate_by(0L);
        docs.setUpdate_by(0L);
        docsMapper.insert(docs);
        return docs.getId();
    }

    public Long createDocs(String fileName, String sageId, String location, Long size, Timestamp createAt,
            Timestamp lastModifiedAt,
            String md5) {
        Docs docs = new Docs();
        docs.setFile_name(fileName);
        docs.setSage_id(sageId);
        docs.setLocation(location);
        docs.setSize(size);
        docs.setDoc_create_at(createAt);
        docs.setDoc_modified_at(lastModifiedAt);
        docs.setMd5(md5);
        return createDocs(docs);
    }

    public Docs getDocsById(Long id) {
        return docsMapper.selectById(id);
    }

    public List<Docs> getDocsByPN(String Pn, String auth) {
        LambdaQueryWrapper<Docs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Docs::getFile_name, Utils.makeShortPn(Pn));
        queryWrapper.orderBy(true, false, Docs::getDoc_modified_at);
        queryWrapper.last("limit 100");

        List<Docs> docs = docsMapper.selectList(queryWrapper);

        // background search document in Sage Dms server , length >= 5 means more
        // precise target
        if (Pn.length() >= 5 && auth != null) {
            CompletableFuture.supplyAsync(() -> {
                log.debug("Background searching document in Sage Dms server");
                List<Docs> docsInDms = DmsService.getDocuments(auth, Pn);
                List<Docs> docsNew = new ArrayList<>();

                // if docsInDms's doc not in docs, add it
                for (Docs docInDms : docsInDms) {
                    boolean found = false;
                    for (Docs doc : docs) {
                        if (docInDms.getFile_name().equals(doc.getFile_name())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        docsNew.add(docInDms);
                    }
                }

                for (Docs doc : docsNew) {
                    // save document to local
                    byte[] content = DmsService.getDocumentContent(doc.getSage_id());
                    String docBasePath = servletContext.getRealPath("/");
                    Utils.saveFileContent(docBasePath + doc.getFile_name(), content);

                    log.info("Auto download {} from Dms server to {}", doc.getFile_name(), docBasePath);
                    logService.addLog("DOC_AUTO_DOWNLOAD", doc.getFile_name(), docBasePath);
                    // update document info
                    updateDocInfo(new File(docBasePath + doc.getFile_name()));

                    // move document to final path
                    String finalPath = Utils.isWin() ? windowsPath : linuxPath;
                    Utils.moveFiles(new File(docBasePath), new File(finalPath), subFolderDeep,
                            subFolderLen);
                }
                return null;
            });

        }
        return docs;
    }

    public List<Docs> getDocsByMd5(String md5) {
        LambdaQueryWrapper<Docs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Docs::getMd5, md5);
        return docsMapper.selectList(queryWrapper);
    }

    public int updateDocsByWrapper(Docs docs, LambdaUpdateWrapper<Docs> wrapper) {
        docs.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        docs.setUpdate_by(0L);
        return docsMapper.update(docs, wrapper);
    }

    public int updateDocsByFileName(String fileName, String sageId, String location, Long size, Timestamp createAt,
            Timestamp lastModifiedAt,
            String md5) {
        Docs docs = new Docs();
        docs.setFile_name(fileName);
        docs.setSage_id(sageId);
        docs.setLocation(location);
        docs.setSize(size);
        docs.setDoc_create_at(createAt);
        docs.setDoc_modified_at(lastModifiedAt);
        docs.setMd5(md5);
        LambdaUpdateWrapper<Docs> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Docs::getFile_name, fileName);
        return updateDocsByWrapper(docs, updateWrapper);
    }

    public int deleteDocsByFileName(String fileName) {
        LambdaUpdateWrapper<Docs> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Docs::getFile_name, fileName);
        return docsMapper.delete(updateWrapper);
    }

    public int deleteDocsByMd5(String md5) {
        LambdaUpdateWrapper<Docs> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Docs::getMd5, md5);
        return docsMapper.delete(updateWrapper);
    }

    public int deleteDocsById(Long id) {
        return docsMapper.deleteById(id);
    }

    /**
     * Updates the information of a file or recursively updates the information of
     * all files in a directory.
     *
     * @param file the file or directory whose information is to be updated
     * 
     * @Note this method update database
     */
    public void updateDocInfo(File file) {
        if (file.isFile()) {
            log.info("[Webdav] Update doc info: {}", file.getAbsolutePath());
            String fileName = file.getName();
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameNoExt = dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
            // skip hidden files
            if (fileName.startsWith("~") || fileName.startsWith("$") ||
                    fileName.toLowerCase().equals("thumbs.db")) {
                file.delete();
                return;
            }

            Docs docs = new Docs();
            docs.setFile_name(fileName);

            BasicFileAttributes attribute = null;
            try {
                attribute = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                if (attribute != null) {
                    docs.setDoc_create_at(new Timestamp(attribute.creationTime().toMillis()));
                    docs.setDoc_modified_at(new Timestamp(attribute.lastModifiedTime().toMillis()));
                    docs.setSize(attribute.size());
                }
                docs.setLocation(Utils.getPathByFileName(fileNameNoExt, subFolderDeep, subFolderLen));
                docs.setMd5(Utils.computerMd5(file));

                if (getDocsByPN(fileName, null).size() == 0) {
                    createDocs(docs);
                } else {
                    LambdaUpdateWrapper<Docs> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(Docs::getFile_name, docs.getFile_name());
                    updateDocsByWrapper(docs, updateWrapper);
                }

            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        if (file.isDirectory()) {
            // skip META-INF and WEB-INF folder
            if (file.getName().equals("META-INF") || file.getName().equals("WEB-INF")) {
                return;
            }

            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    updateDocInfo(f);
                }
            }
        }

    }
}
