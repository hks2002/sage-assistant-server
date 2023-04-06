/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-03-12 19:31:39                                                                      *
 * @FilePath              : src/main/java/sageassistant/dataSrv/dao/PnMapper.java                                    *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sageassistant.data.model.CostHistory;
import sageassistant.data.model.DeliveryDuration;
import sageassistant.data.model.PnDetails;
import sageassistant.data.model.PnRootPn;
import sageassistant.data.model.PnStatus;
import sageassistant.data.model.QuoteHistory;
import sageassistant.data.model.SalesHistory;

@Mapper
public interface PnMapper {
    List<PnRootPn> findPnByLike(@Param("PnOrPnRoot") String PnRootOrPn, @Param("Count") Integer Count);

    List<String> findOptionPn(@Param("PnRoot") String PnRoot);

    List<PnDetails> findAllPnByPnRoot(@Param("PnRoot") String PnRoot);

    List<PnStatus> findObsoletePnBySite(@Param("Site") String Site);

    List<SalesHistory> findSalesHistoryByPnRoot(@Param("PnRoot") String PnRoot);

    List<QuoteHistory> findQuoteHistoryByPnRoot(@Param("PnRoot") String PnRoot);

    List<CostHistory> findCostHistoryByPnRoot(@Param("PnRoot") String PnRoot);

    List<DeliveryDuration> findDeliveryDurationByPnRoot(@Param("PnRoot") String PnRoot);
}
