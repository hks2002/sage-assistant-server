/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2023-03-10 15:42:04                                                                      *
 * @LastEditDate          : 2025-07-27 10:09:25                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateUtils {
  public static long dateDiff(Date start, Date end) {
    return (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
  }

  public static String formatDate(Date date) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    if (date == null) {
      return "";
    }
    return formatter.format(date);
  }

  public static String today() {
    Date date = new Date();
    return formatDate(date);
  }

  public static String now() {
    Date date = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date);
  }
}
