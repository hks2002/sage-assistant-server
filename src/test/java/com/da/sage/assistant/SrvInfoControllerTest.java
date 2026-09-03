/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 * @CreatedDate           : 2023-03-15 23:49:52                                                                        *
 * @LastEditDate          : 2026-09-04 00:49:40                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 **********************************************************************************************************************/

package com.da.sage.assistant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class SrvInfoControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void testSrvVersion() throws Exception {
    MockHttpServletRequestBuilder req = MockMvcRequestBuilders
        .get("/sage-assistant-api/SrvVersion")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(req)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

  }

  @Test
  void testSrvName() throws Exception {
    MockHttpServletRequestBuilder req = MockMvcRequestBuilders
        .get("/sage-assistant-api/SrvName")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(req)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("sage.assistant"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

  }

  @SuppressWarnings("null")
  @Test
  void testSrvInfo() throws Exception {
    MockHttpServletRequestBuilder req = MockMvcRequestBuilders
        .get("/sage-assistant-api/SrvInfo")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(req)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

  }

  @SuppressWarnings("null")
  @Test
  void testSrvProjectDependencies() throws Exception {
    MockHttpServletRequestBuilder req = MockMvcRequestBuilders
        .get("/sage-assistant-api/SrvProjectDependencies")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(req)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

  }
}
