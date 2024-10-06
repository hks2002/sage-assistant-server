/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2023-03-11 15:45:58                                                                       *
 * @LastEditDate          : 2025-07-27 21:19:01                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import com.da.sage.assistant.utils.CommonUtils;
import com.da.sage.assistant.utils.DateUtils;
import com.da.sage.assistant.utils.PNUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UtilsTest {

  // @Test
  // void testIsServerAtZhuhai() {
  // Assertions.assertTrue(Utils.isServerAtZhuhai());
  // }

  @Test
  void testIsClientFromZhuhai() {
    Assertions.assertTrue(CommonUtils.isZhuhaiClient("192.168.0.1"));
    Assertions.assertTrue(CommonUtils.isZhuhaiClient("192.168.253.1"));
    Assertions.assertFalse(CommonUtils.isZhuhaiClient("192.168.254.1"));
  }

  @Test
  void testIsWin() {
    Assertions.assertTrue(CommonUtils.isWin());
  }

  @Test
  void testDateDiff() throws ParseException {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    Date start = fmt.parse("2010-01-02");
    Date end = fmt.parse("2010-03-02");
    long diff = DateUtils.dateDiff(start, end);
    log.debug("diff:" + diff);
    Assertions.assertEquals(diff, 59);
  }

  @Test
  void testGetFileExt() {
    Assertions.assertEquals(StringUtils.getFilename("/"), "");
    Assertions.assertEquals(
        Optional.ofNullable(StringUtils.getFilename("/")).orElse(""),
        "");

    Assertions.assertEquals(
        StringUtils.getFilenameExtension("filename.ext"),
        "ext");
    Assertions.assertEquals(
        StringUtils.getFilenameExtension("filename.ext.ext"),
        "ext");
    Assertions.assertEquals(
        Optional
            .ofNullable(StringUtils.getFilenameExtension("filename"))
            .orElse(""),
        "");
    Assertions.assertEquals(
        Optional.ofNullable(StringUtils.getFilenameExtension("/")).orElse(""),
        "");
  }

  @Test
  void testFormatDate() {
    Assertions.assertEquals("", DateUtils.formatDate(null));
    Assertions.assertTrue(
        DateUtils.formatDate(new Date()).matches("\\d{4}-\\d{2}-\\d{2}"));
    Assertions.assertFalse(
        (new Date()).toString().matches("\\d{4}-\\d{2}-\\d{2}"));
    log.debug((new Date()).toString());
  }

  @Test
  void testDecodeBase64() {
    String s = CommonUtils.decodeBasicAuth("cmh1YW5nOkRhekAyMDIyMDMwMg");
    int i = s.indexOf(':');
    Assertions.assertTrue(i > 0 && i < s.length());
    log.debug(s.substring(0, i));
    log.debug(s.substring(i + 1));
  }

  @Test
  void testMakeShortPn() {
    Assertions.assertEquals(
        PNUtils.makeShortPn("98A1234567890_CPD_P-11_D"),
        "98A12345678_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("98A1234567890_CPD-11_D"),
        "98A12345678_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("98A1234567890G01P01_NQ_D_-"),
        "98A12345678_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("98A1234567890G01P01NQ_D_-"),
        "98A12345678_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("9C12345G01P01NQ_D_-"),
        "9C12345_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("9C12345-67G01P01NQ_D_-"),
        "9C12345-67_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("9C12345G01P01NQ-11_D_-"),
        "9C12345_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("856A1234567890G01P01NQ_D"),
        "856A1234_D");

    Assertions.assertEquals(
        PNUtils.makeShortPn("RRT123456G01P01NQ_A_-"),
        "RRT123456_A");

    Assertions.assertEquals(
        PNUtils.makeShortPn("HU12345G01P01NQ_A_-"),
        "HU12345_A");

    Assertions.assertEquals(
        PNUtils.makeShortPn("330A12345678G01P01NQ_A_-"),
        "330A123456_A");

    Assertions.assertEquals(
        PNUtils.makeShortPn("9401M01G01P01NQ_A_-"),
        "9401M01_A");

    Assertions.assertEquals(PNUtils.makeShortPn("A12345-1_A_-"), "A12345_A");

    Assertions.assertEquals(PNUtils.makeShortPn("956A1001_QU_A"), "956A1001_A");
  }

  @Test
  void testParserDateString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "MM/dd/yyyy hh:mm:ss aa",
        Locale.ENGLISH);
    Date date;
    try {
      date = dateFormat.parse("11/28/2022 12:00:00 AM");
    } catch (ParseException e) {
      log.error(e.getMessage());
      date = new Date();
    }
    log.info(date.toString());
  }

  @Test
  void testChangeFileTime() throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "MM/dd/yyyy hh:mm:ss aa",
        Locale.ENGLISH);
    Date date;
    try {
      date = dateFormat.parse("11/28/2022 12:00:00 AM");
    } catch (ParseException e) {
      log.error(e.getMessage());
      date = new Date();
    }

    File file = new File("C:/var/HU80001-1_B.pdf");
    file.setLastModified(date.getTime());
  }

}
