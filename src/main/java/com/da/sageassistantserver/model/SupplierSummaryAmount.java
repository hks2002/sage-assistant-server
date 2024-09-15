/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2022-03-26 17:01:00                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-09-16 01:22:59                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.model;

import java.math.BigDecimal;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class SupplierSummaryAmount {

    private String Site;
    private String SupplierCode;
    private String Currency;
    private BigDecimal Amount;
    private Float Rate;
    private BigDecimal USD;
}
