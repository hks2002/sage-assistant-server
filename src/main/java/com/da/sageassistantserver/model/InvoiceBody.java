/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:21:01                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/InvoiceBody.java                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class InvoiceBody {

    private Integer Line;
    private String PN;
    private String Description;
    private Integer Qty;
    private String Unit;
    private BigDecimal NetPrice;
    private BigDecimal AmountNoTax;
    private BigDecimal AmountTaxInclude;
    private BigDecimal AmountTax;
    private BigDecimal TaxRate;
}
