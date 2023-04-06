/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:55:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-04-06 14:36:05                                                                      *
 * @FilePath              : src/main/java/sageassistant/data/dao/AnalysesMapper.java                                 *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package sageassistant.data.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sageassistant.data.model.AnalysesPurchase;
import sageassistant.data.model.AnalysesQuote;
import sageassistant.data.model.AnalysesQuoteSalesCost;
import sageassistant.data.model.AnalysesSales;

@Mapper
public interface AnalysesMapper {
    List<AnalysesQuoteSalesCost> analysesQuoteSalesCost(
        @Param("Site") String Site,
        @Param("CategoryCode") String CategoryCode,
        @Param("PnRoot") String PnRoot,
        @Param("DateFrom") String DateFrom,
        @Param("DateTo") String DateTo,
        @Param("Limit") Integer Limit
    );

    List<AnalysesPurchase> analysesPurchase(
        @Param("Site") String Site,
        @Param("PnRoot") String PnRoot,
        @Param("Currency") String Currency,
        @Param("LastN") String LastN
    );

    List<AnalysesQuote> analysesQuote(
        @Param("Site") String Site,
        @Param("PnRoot") String PnRoot,
        @Param("Currency") String Currency,
        @Param("LastN") String LastN
    );

    List<AnalysesSales> analysesSales(
        @Param("Site") String Site,
        @Param("PnRoot") String PnRoot,
        @Param("Currency") String Currency,
        @Param("LastN") String LastN
    );
}
