/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-31 16:25:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 19:31:03                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/dao/CustomerMapper.java                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sageassistant.data.model.CustomerDelayHistory;
import sageassistant.data.model.CustomerDeliveryHistory;
import sageassistant.data.model.CustomerDetails;
import sageassistant.data.model.CustomerName;
import sageassistant.data.model.CustomerOpenItems;
import sageassistant.data.model.CustomerSummaryAmount;
import sageassistant.data.model.CustomerSummaryQty;

@Mapper
public interface CustomerMapper {
    List<CustomerDetails> findCustomerDetailsByCode(@Param("CustomerCode") String CustomerCode);

    List<CustomerName> findCustomerByCodeOrName(
        @Param("CustomerCodeOrName") String CustomerCodeOrName,
        @Param("Count") Integer Count
    );

    List<CustomerSummaryAmount> findCustomerOpenAmount(@Param("CustomerCode") String CustomerCode);

    List<CustomerSummaryQty> findCustomerOpenProjectQty(@Param("CustomerCode") String CustomerCode);

    List<CustomerSummaryQty> findCustomerOpenItemQty(@Param("CustomerCode") String CustomerCode);

    List<CustomerSummaryQty> findCustomerOpenProductQty(@Param("CustomerCode") String CustomerCode);

    List<CustomerOpenItems> findCustomerOpenItems(@Param("CustomerCode") String CustomerCode);

    List<CustomerSummaryAmount> findCustomerTotalAmount(
        @Param("CustomerCode") String CustomerCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<CustomerSummaryQty> findCustomerTotalProjectQty(
        @Param("CustomerCode") String CustomerCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<CustomerSummaryQty> findCustomerTotalItemQty(
        @Param("CustomerCode") String CustomerCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<CustomerSummaryQty> findCustomerTotalProductQty(
        @Param("CustomerCode") String CustomerCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<CustomerDeliveryHistory> findCustomerDeliveryHistory(
        @Param("CustomerCode") String CustomerCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );

    List<CustomerDelayHistory> findCustomerDelayHistory(
        @Param("CustomerCode") String CustomerCode,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo
    );
}
