/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2023-11-24 16:53:52                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2023-11-25 23:19:25                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.model;

import java.sql.Date;

import lombok.Data;

@Data
public class LinksInvoiceLine {
    private String InvoiceNO;
    private String InvoiceLine;
    private String InvoiceProjectNO;
    private String InvoiceCustomerCode;
    private String InvoiceCustomerName;
    private Date InvoiceDate;
    private String InvoicePN;
    private String InvoicePNDescription;
    private Float InvoiceQty;
    private String InvoiceUnit;
    private Double InvoiceAmount;
    private String InvoiceCurrency;
}
