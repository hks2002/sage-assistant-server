/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2022-03-26 22:53:00                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-07 14:16:16                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSONObject;
import com.da.sageassistantserver.model.Docs;
import com.da.sageassistantserver.service.DocsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@Slf4j
public class DocsController {

    @Autowired
    DocsService docsService;

    /*
     * @param pn could be Pn or PnRoot, PnRoot without version
     */
    @GetMapping("/Data/GetDocsInfoFromZHU")
    public List<Docs> getAttachmentPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String auth = request.getHeader("authorization");
        String Pn = request.getParameter("Pn");
        log.debug(auth);
        return docsService.getDocsByPN(Pn, auth);
    }

    @PostMapping("/Data/FileUpload")
    public JSONObject handleFileUpload(@RequestParam("file") final MultipartFile uploadFile) {
        return docsService.handleFileUpload(uploadFile);

    }

    @DeleteMapping("/Data/FileDelete")
    public JSONObject handleFileDelete(@RequestParam(value = "Path", required = true) String path) {
        return docsService.handleFileDelete(path);
    }

}
