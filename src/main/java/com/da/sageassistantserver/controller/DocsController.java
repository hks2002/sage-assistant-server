/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2022-03-26 22:53:00                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-02 09:34:57                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.sageassistantserver.model.Docs;
import com.da.sageassistantserver.service.DocsService;

@CrossOrigin
@RestController
public class DocsController {

    @Autowired
    DocsService docsService;

    /*
     * @param pn could be Pn or PnRoot, PnRoot without version
     */
    @GetMapping("/Data/GetDocsInfo")
    public List<Docs> getAttachmentPath(
            @RequestParam(value = "Pn", required = false, defaultValue = "NULL") String pn) {
        return docsService.getDocsByFileName(pn);
    }

}
