/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:57:07                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-01 12:56:24                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.sageassistantserver.dao.DocsMapper;
import com.da.sageassistantserver.model.Docs;
import com.da.sageassistantserver.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocsService {

    @Autowired
    private DocsMapper docsMapper;
    @Value("${attachment.path.folder.deep}")
    private int subFolderDeep;
    @Value("${attachment.path.folder.len}")
    private int subFolderLen;

    public Long createDocs(Docs docs) {
        docs.setCreate_at(new Timestamp(System.currentTimeMillis()));
        docs.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        docs.setCreate_by(0L);
        docs.setUpdate_by(0L);
        docsMapper.insert(docs);
        return docs.getId();
    }

    public Long createDocs(String fileName, String location, Long size, Timestamp createAt, Timestamp lastModifiedAt,
            String md5) {
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

    public List<Docs> getDocsByFileName(String fileName) {
        LambdaQueryWrapper<Docs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Docs::getFile_name, fileName);
        return docsMapper.selectList(queryWrapper);
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
            Timestamp lastModifiedAt,
            String md5) {
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

    public void updateDocInfo(File file) {
        if (file.isFile()) {

            String fileName = file.getName();
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameNoExt = dotIndex >= -1 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
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

                if (getDocsByFileName(fileName).size() == 0) {
                    createDocs(docs);
                } else {
                    LambdaUpdateWrapper<Docs> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(Docs::getFile_name, docs.getFile_name());
                    docsMapper.update(docs, updateWrapper);
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
