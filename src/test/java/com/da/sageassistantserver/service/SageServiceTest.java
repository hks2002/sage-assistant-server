/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-16 17:14:44                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-24 16:07:46                                                                      *
 * @FilePath              : src/test/java/com/da/sageassistantserver/service/SageServiceTest.java                    *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.service;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.da.sageassistantserver.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SageServiceTest {

 

    private static String auth;

    @BeforeAll
    public static void loadAuth() {
        auth = Utils.readFileContent("auth.txt");
        log.info(auth);
    }

    @AfterEach
    public void tearDown() {
        log.info("do logout for each");
        SageService.doLogout(auth);
    }

    @Test
    void testDoLogin() {
        SageService.doLogin(auth);
    }

    @Test
    void testDoLogout() {
        SageService.doLogout(auth);
    }

    @Test
    void testEndSession() {
        SageService.endSession(auth, "c961d328-f1bb-449d-b305-5446d9423fe0");
    }

    @Test
    void testDoLoginDoLogout() {
        SageService.doLogin(auth);
        SageService.getSageSessionCache(auth, "GESSOH", "2~1");
        SageService.doLogout(auth);
    }

    @Test
    void testGetProfile() {
        SageService.getProfile(auth);
    }

    @Test
    void testGetFunction() {
        SageService.getFunction(auth);
    }

    @Test
    void testGetSageSession() throws ExecutionException {
        SageService.getSageSessionCache(auth, "GESSOH", "2~1");
    }

    @Test
    void testUpdateField() {
        // SAD: EA55
        SageService.updateSageField(auth, "SalesOrder", "ZCC220007", 8, "EA55", "1");
        SageService.doLogout(auth);
    }
}
