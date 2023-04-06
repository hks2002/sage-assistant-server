/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 19:31:30                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/dao/InvoiceMapper.java                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sageassistant.data.model.InvoiceBody;
import sageassistant.data.model.InvoiceHeader;

@Mapper
public interface InvoiceMapper {
    InvoiceHeader findInvoiceHeaderByInvoiceNO(@Param("InvoiceNO") String InvoiceNO);

    List<InvoiceHeader> findInvoiceHeaderByFaPiao(@Param("FaPiao") String FaPiao);

    List<String> findInvoiceNOByInvoiceNO(@Param("InvoiceNO") String InvoiceNO, @Param("Count") Integer Count);

    List<InvoiceBody> findInvoiceBodyByInvoiceNO(@Param("InvoiceNO") String InvoiceNO);

    List<InvoiceBody> findInvoiceBodyByFaPiao(@Param("FaPiao") String FaPiao);
}
