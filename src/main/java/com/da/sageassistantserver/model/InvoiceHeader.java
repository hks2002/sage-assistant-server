/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-23 22:10:51                                                                      *
 * @FilePath              : src/main/java/com/da/sageassistantserver/model/InvoiceHeader.java                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class InvoiceHeader {

    private String Facility;
    private String Currency;
    private String InvoiceNO;
    private String CreateDate;
    private String CreateUser;
    private String Note;
    private String InvoiceStatus;
    private String FaPiao;
    private String Customer;
    private String Address;
    private BigDecimal AmountTaxInclude;
    private BigDecimal AmountTaxNotInclude;
    private BigDecimal AmountTax;
    private Float CurrRate;
}
