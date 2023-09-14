/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 22:53:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-09-07 01:02:50                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/controller/AttachmentController.java            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSONArray;
import com.da.sageassistantserver.model.Attachment;
import com.da.sageassistantserver.service.AttachmentService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/Data/FileUpload")
    public String handleFileUpload(
        @RequestParam(value = "Pn", required = true) String pn,
        @RequestParam(value = "Cat", required = true) String cat,
        @RequestParam("file") final MultipartFile uploadFile,
        HttpServletResponse response
    ) {
        return attachmentService.handleFileUpload(pn, cat, uploadFile, response);
    }

    @GetMapping("/Data/FileDelete")
    public String handleFileDelete(
        @RequestParam(value = "Path", required = true) String file,
        HttpServletResponse response
    ) {
        return attachmentService.handleFileDelete(file, response);
    }

    /*
     * @param pn could be Pn or PnRoot, PnRoot without version
     */
    @GetMapping("/Data/AttachmentPath")
    public List<Attachment> getAttachmentPath(
        @RequestParam(value = "Pn", required = false, defaultValue = "NULL") String pn
    ) {
        return attachmentService.getAttachmentPath(pn);
    }

    @GetMapping("/Data/AttachmentPathForChina")
    public JSONArray getAttachmentPathForChina(
        @RequestParam(value = "Pn", required = false, defaultValue = "NULL") String pn
    ) {
        return attachmentService.getAttachmentPathForChina(pn);
    }
}
