/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-27 17:16:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2023-11-21 11:31:13                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class FinancialInvoicePayPro {
    private String Id;
    private String Site;
    private String Currency;
    private String Customer;
    private String Name;
    private String InvoiceNO;
    private BigDecimal Amount;
    private BigDecimal AmountLocal;
    private BigDecimal Pay;
    private BigDecimal PayLocal;
    private Date CreateDate;
    private Date DueDate;
    private Date PayDate;
    private String Fapiao;
    private String CustRef;
    private String OrderNO;
    private String Status;
    private String MatchedBy;
    private String PayNO;
    private String PayCurrency;
    private BigDecimal PayInPayNO;
    private String Desc0;
    private String Desc1;
}
