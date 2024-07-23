/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:57:07                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-23 09:30:30                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.sageassistantserver.dao.DocsMapper;
import com.da.sageassistantserver.model.Docs;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;
import com.da.sageassistantserver.utils.Utils;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

/**
 * DocsService is provide the /docs/* api, documents is stored at the ZHU HAI
 * database/file server
 */
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

  public Long createDocs(String fileName, String location, Long size, Timestamp createAt,
      Timestamp lastModifiedAt, String md5) {
    Docs docs = new Docs();
    docs.setFile_name(fileName);
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

  public List<Docs> getDocsByPN(String Pn, String auth, Boolean notUsingLike) {
    LambdaQueryWrapper<Docs> queryWrapper = new LambdaQueryWrapper<>();
    if (notUsingLike == true) {
      queryWrapper.eq(Docs::getFile_name, Pn);
    } else {
      queryWrapper.like(Docs::getFile_name, Utils.makeShortPn(Pn));
    }
    queryWrapper.orderBy(true, false, Docs::getDoc_modified_at);
    queryWrapper.last("limit 20");

    List<Docs> docs = docsMapper.selectList(queryWrapper);

    // background search document in Sage Dms server , length >= 5 means more
    // precise target
    if (Pn.length() >= 5 && auth != null) {
      CompletableFuture.runAsync(() -> {
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

        downloadDmsDocs(docsNew);
      });

    }
    return docs;
  }

  public void downloadDmsDocs(List<Docs> docs) {
    for (Docs doc : docs) {
      String docBasePath = servletContext.getRealPath("/");
      String file = docBasePath + doc.getFile_name();
      Path filePath = Paths.get(file);

      // log
      log.debug("Auto download {} from Dms server to {} start ...", doc.getFile_name(), docBasePath);

      // save document to local
      byte[] content = DmsService.getDocumentContent(doc.getLocation()); // using location as id
      Utils.saveFileContent(file, content);

      if (Files.exists(filePath)) {
        log.info("Auto download {} from Dms server to {}", doc.getFile_name(), file);
        logService.addLog("DOC_AUTO_DOWNLOAD", doc.getFile_name(), file);
      } else {
        log.error("Auto download {} from Dms server to {} error", doc.getFile_name(), file);
        return;
      }

      // set file time
      File f = new File(file);
      f.setLastModified(doc.getDoc_modified_at().getTime());
      return;

    }
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

  public int updateDocsByFileName(String fileName, String location, Long size, Timestamp createAt,
      Timestamp lastModifiedAt, String md5) {
    Docs docs = new Docs();
    docs.setFile_name(fileName);
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
      log.info("[Docs] Update doc info: {}", file.getAbsolutePath());
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

        if (getDocsByPN(fileName, null, true).size() == 0) {
          logService.addLog("DOC_INFO_CREATE", "system", "Docs Service", fileName);
          createDocs(docs);
        } else {
          logService.addLog("DOC_INFO_UPDATE", "system", "Docs Service", fileName);
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

  public JSONObject handleFileUpload(final MultipartFile uploadFile, String loginName, String loginUser) {
    try {
      if (uploadFile.isEmpty()) {
        return SageActionHelper.rtnObj(false, MsgTyp.RESULT, "Upload file is empty");
      }
      String docBasePath = servletContext.getRealPath("/");
      String originalFilename = uploadFile.getOriginalFilename();
      Path fullSavePath = Paths.get(docBasePath + "/" + originalFilename);

      log.info("[Upload] [" + originalFilename + "] " + fullSavePath);
      if (fullSavePath.toFile().exists()) {
        updateDocInfo(fullSavePath.toFile());
        return SageActionHelper.rtnObj(true, MsgTyp.RESULT, originalFilename);
      }

      Files.createDirectories(fullSavePath.getParent());
      Files.copy(uploadFile.getInputStream(), fullSavePath);

      if (fullSavePath.toFile().exists()) {
        logService.addLog("DOC_UPLOAD_SUCCESS", loginName, loginUser, originalFilename);
        return SageActionHelper.rtnObj(true, MsgTyp.RESULT, originalFilename);
      } else {
        logService.addLog("DOC_UPLOAD_FAILED", loginName, loginUser, originalFilename);
        return SageActionHelper.rtnObj(false, MsgTyp.RESULT, originalFilename);
      }

    } catch (Exception e) {
      log.error("[Upload] " + e.getMessage());
      return SageActionHelper.rtnObj(false, MsgTyp.ERROR, e.getMessage());
    }
  }

  public JSONObject handleFileDelete(String path) {
    String attachmentPath = Utils.isWin() ? windowsPath : linuxPath;
    String docBasePath = servletContext.getRealPath("/");

    try {
      Path docBaseFilePath = Paths.get(docBasePath + URLDecoder.decode(path, "UTF-8"));
      Path attachmentFilePath = Paths.get(attachmentPath + URLDecoder.decode(path, "UTF-8"));
      if (Files.exists(attachmentFilePath)) {
        Files.delete(attachmentFilePath);
      }
      if (Files.exists(docBaseFilePath)) {
        Files.delete(docBaseFilePath);
      }

      if (Files.exists(attachmentFilePath) || Files.exists(docBaseFilePath)) {
        log.info("[Delete] {} with failed!", path);
        return SageActionHelper.rtnObj(false, MsgTyp.RESULT, path);
      } else {
        log.info("[Delete] {} with success!", path);
        return SageActionHelper.rtnObj(true, MsgTyp.RESULT, path);
      }

    } catch (Exception e) {
      log.error("[Delete] " + e.getMessage());
      return SageActionHelper.rtnObj(false, MsgTyp.ERROR, e.getMessage());
    }
  }

  @Scheduled(fixedRate = 1000 * 60 * 1)
  public void cronTask() {
    String docBasePath = servletContext.getRealPath("/");
    String attachmentPath = Utils.isWin() ? windowsPath : linuxPath;

    log.debug("Start to update doc info from [{}] to [{}], deep {}, len {}",
        docBasePath, attachmentPath, subFolderDeep, subFolderLen);
    // update doc info first, this amount of files, would be less
    updateDocInfo(new File(docBasePath));
    Utils.moveFiles(Paths.get(docBasePath), Paths.get(attachmentPath), subFolderDeep, subFolderLen);
  }
}
