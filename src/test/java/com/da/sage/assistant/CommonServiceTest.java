/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2023-03-11 16:26:53                                                                       *
 * @LastEditDate          : 2025-07-21 23:39:50                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.da.sage.assistant.service.CommonService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class CommonServiceTest {

  private final CommonService commonService;

  @Test
  void testAllSites() {
    commonService.getAllSites();
  }
}
