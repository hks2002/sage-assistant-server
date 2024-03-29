/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:22:14                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/SupplierSummaryAmount.java                     *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class SupplierSummaryAmount {

    private String Site;
    private String SupplierCode;
    private String Currency;
    private BigDecimal Amount;
    private Float Rate;
    private BigDecimal USD;
}
