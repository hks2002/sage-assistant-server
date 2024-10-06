/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2023-03-14 23:38:37                                                                       *
 * @LastEditDate          : 2025-07-15 16:13:19                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant;

import org.junit.jupiter.api.Test;

import com.da.sage.assistant.service.HttpService;

public class HttpServiceTest {

  @Test
  void testRequest() {
    HttpService.request("https://www.baidu.com", "GET");
    HttpService.request("https://srvsyr01/auth/login/page", "GET");
  }
}
