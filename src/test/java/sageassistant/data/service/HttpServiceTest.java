/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2023-03-14 23:38:37                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:44:51                                                                      *
 * @FilePath              : src/test/java/sageassistant/data/service/HttpServiceTest.java                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.service;

import org.junit.jupiter.api.Test;

public class HttpServiceTest {

    @Test
    void testRequest() {
        HttpService.request("https://www.baidu.com", "GET");
        HttpService.request("https://srvsyr01/auth/login/page", "GET");
    }
}
