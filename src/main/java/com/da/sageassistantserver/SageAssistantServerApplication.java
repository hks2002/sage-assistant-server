/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2023-06-22 12:15:04                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2023-08-31 23:16:27                                                                       *
 * @FilePath              : src/main/java/com/da/sageassistantserver/SageAssistantServerApplication.java              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sageassistantserver;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SageAssistantServerApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SpringApplication.run(SageAssistantServerApplication.class, args);
    }
}
