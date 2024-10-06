/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CreatedDate           : 2022-06-27 13:39:00                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 * @LastEditDate          : 2024-12-25 14:57:06                                                                       *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 *********************************************************************************************************************/

package com.da.sage.assistant.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.da.sage.assistant.model.CurrencyHistory;

@Mapper
public interface CurrencyMapper {
  List<CurrencyHistory> findCurrencyRate(
      @Param("Sour") String Sour,
      @Param("Dest") String Dest,
      @Param("Date") String Date);
}
