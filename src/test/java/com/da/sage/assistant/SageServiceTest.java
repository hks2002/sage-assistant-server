/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2023-03-16 17:14:44                                                                       *
 * @LastEditDate          : 2025-07-15 16:13:16                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.service.SageActionService;
import com.da.sage.assistant.service.SageLoginService;
import com.da.sage.assistant.service.SagePrintService;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SageServiceTest {

  private String auth;

  @Test
  void testDoLogin() {
    SageLoginService.doLogin(auth);
  }

  @Test
  void testDoLogout() {
    SageLoginService.doLogout(auth);
  }

  @Test
  void testEndSession() {
    SageLoginService.endSession(auth, "c961d328-f1bb-449d-b305-5446d9423fe0");
  }

  @Test
  void testDoLoginDoLogout() {
    SageLoginService.doLogin(auth);
    SageLoginService.getSageSessionCache(auth, "GESSOH", "2~1");
    SageLoginService.doLogout(auth);
  }

  @Test
  void testGetProfile() {
    SageLoginService.getProfile(auth);
  }

  @Test
  void testGetFunction() {
    SageLoginService.getFunction(auth);
  }

  @Test
  void testGetSageSession() throws ExecutionException {
    SageLoginService.getSageSessionCache(auth, "GESSOH", "2~1");
  }

  @Test
  void testUpdateField() {
    // SAD: EA55
    SageActionService.updateSageField(
        auth,
        "SalesOrder",
        "ZCC220007",
        8,
        "EA55",
        "1");
    SageLoginService.doLogout(auth);
  }

  @Test
  void testPrintUUID() throws ExecutionException {
    auth = "Basic cmh1YW5nOkRhekAyMDIyMDMwOA==";
    JSONObject rtn = SagePrintService.getPrintUUID(
        auth,
        "PurchaseOrder",
        "ZCF2400543",
        null);
    log.debug(rtn.toString());
  }
}
