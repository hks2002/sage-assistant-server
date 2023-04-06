/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 13:22:18                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/model/SupplierSummaryQty.java                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.model;

import lombok.Getter;
import lombok.Setter;
import sageassistant.data.model.base.ModelTemplate;

@Getter
@Setter
public class SupplierSummaryQty extends ModelTemplate {

    private static final long serialVersionUID = 1L;

    private String Site;
    private String SupplierCode;
    private String CountType;
    private int Qty;
}
