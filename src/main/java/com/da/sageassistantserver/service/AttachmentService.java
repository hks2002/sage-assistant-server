/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:57:07                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-08 13:01:33                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.da.sageassistantserver.dao.AttachmentMapper;
import com.da.sageassistantserver.model.Attachment;

/**
 * AttachmentService is saved the old attachment in Toulouse file server
 */
@Service
public class AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public List<Attachment> getAttachmentPath(String pn) {
        if (pn.equals("NULL")) {
            return new ArrayList<>();
        }
        if (pn.endsWith("-")) {
            pn = pn.substring(0, pn.length() - 1);
        }
        if (pn.endsWith("_")) {
            pn = pn.substring(0, pn.length() - 1);
        }
        pn = "%" + pn.toUpperCase() + "%";
        List<Attachment> listAttachment = attachmentMapper.getAttachment(pn);
        for (Attachment o : listAttachment) {
            // make the url with server path
            String pathOri = o.getPath();
            pathOri = pathOri.replace("\\", "/");

            Path path = Paths.get(pathOri, "");
            o.setFile(path.getFileName().toString());

            // stand path is /File/docs_sagex3/*
            if (pathOri.startsWith("[DOCS_SAGEX3]")) {
                pathOri = "/File/srvdata01/DOCS_SAGEX3/" + pathOri.substring(14);
            } else {
                // Here keep the path
            }

            o.setPath(pathOri);
        }
        return listAttachment;
    }

}
