/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2023-03-11 15:45:58                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-01 12:00:40                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsTest {

  @Test
  void testIsNullOrEmpty() {
    Assertions.assertTrue(Utils.isNullOrEmpty(""));
    Assertions.assertTrue(Utils.isNullOrEmpty(" "));
    Assertions.assertTrue(Utils.isNullOrEmpty(null));
  }

  // @Test
  // void testIsServerAtZhuhai() {
  // Assertions.assertTrue(Utils.isServerAtZhuhai());
  // }

  @Test
  void testIsClientFromZhuhai() {
    Assertions.assertTrue(Utils.isClientFromZhuhai("192.168.0.1"));
    Assertions.assertTrue(Utils.isClientFromZhuhai("192.168.253.1"));
    Assertions.assertFalse(Utils.isClientFromZhuhai("192.168.254.1"));
  }

  @Test
  void testIsWin() {
    Assertions.assertTrue(Utils.isWin());
  }

  @Test
  void testDateDiff() throws ParseException {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    Date start = fmt.parse("2010-01-02");
    Date end = fmt.parse("2010-03-02");
    long diff = Utils.dateDiff(start, end);
    log.debug("diff:" + diff);
    Assertions.assertEquals(diff, 59);
  }

  @Test
  void testFindFiles() {
    String classLoaderPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString();
    log.debug(classLoaderPath);
    // maven doesn't copy the test resource, why?
    String fPath = classLoaderPath + "../../src/test/resources";
    log.debug(fPath);
    Assertions.assertEquals(Utils.findFiles(fPath + "/XXXX").length, 0);
    Assertions.assertEquals(Utils.findFiles(fPath + "/test.sql").length, 0);
    Assertions.assertTrue(Utils.findFiles(fPath).length == 3);
    Assertions.assertTrue(Utils.findFiles(fPath)[0].equals("auth.txt"));
  }

  @Test
  void testReadFileContent() {
    String sql = Utils.readFileContent("../../src/test/resources/test.sql");
    log.debug(sql);
    Assertions.assertEquals(sql, "This is test sql");
  }

  @Test
  void testGetFileExt() {
    Assertions.assertEquals(Utils.getFileExt("filename.ext"), "EXT");
    Assertions.assertEquals(Utils.getFileExt("filename.ext.ext"), "EXT");
    Assertions.assertEquals(Utils.getFileExt("filename"), "");
  }

  @Test
  void testFormatDate() {
    Assertions.assertEquals("", Utils.formatDate(null));
    Assertions.assertTrue(Utils.formatDate(new Date()).matches("\\d{4}-\\d{2}-\\d{2}"));
    Assertions.assertFalse((new Date()).toString().matches("\\d{4}-\\d{2}-\\d{2}"));
    log.debug((new Date()).toString());
  }

  @Test
  void testDecodeBase64() {
    String s = Utils.decodeBasicAuth("cmh1YW5nOkRhekAyMDIyMDMwMg");
    int i = s.indexOf(':');
    Assertions.assertTrue(i > 0 && i < s.length());
    log.debug(s.substring(0, i));
    log.debug(s.substring(i + 1));
  }

  @Test
  void testMakeShortPn() {
    Assertions.assertEquals(Utils.makeShortPn("98A1234567890_CPD_P-11_D"), "98A12345678_D");

    Assertions.assertEquals(Utils.makeShortPn("98A1234567890_CPD-11_D"), "98A12345678_D");

    Assertions.assertEquals(Utils.makeShortPn("98A1234567890G01P01_NQ_D_-"), "98A12345678_D");

    Assertions.assertEquals(Utils.makeShortPn("98A1234567890G01P01NQ_D_-"), "98A12345678_D");

    Assertions.assertEquals(Utils.makeShortPn("9C12345G01P01NQ_D_-"), "9C12345_D");

    Assertions.assertEquals(Utils.makeShortPn("9C12345-67G01P01NQ_D_-"), "9C12345-67_D");

    Assertions.assertEquals(Utils.makeShortPn("9C12345G01P01NQ-11_D_-"), "9C12345_D");

    Assertions.assertEquals(Utils.makeShortPn("856A1234567890G01P01NQ_D"), "856A1234_D");

    Assertions.assertEquals(Utils.makeShortPn("RRT123456G01P01NQ_A_-"), "RRT123456_A");

    Assertions.assertEquals(Utils.makeShortPn("HU12345G01P01NQ_A_-"), "HU12345_A");

    Assertions.assertEquals(Utils.makeShortPn("330A12345678G01P01NQ_A_-"), "330A123456_A");

    Assertions.assertEquals(Utils.makeShortPn("9401M01G01P01NQ_A_-"), "9401M01_A");

    Assertions.assertEquals(Utils.makeShortPn("A12345-1_A_-"), "A12345_A");

    Assertions.assertEquals(Utils.makeShortPn("956A1001_QU_A"), "956A1001_A");
  }

  @Test
  void testMoveFile() {
    Utils.moveFiles(new File("Y:\\Manual"), new File("Y:\\New"), 2, 3);
  }

  @Test
  void testComputeMd5() {
    String md5 = Utils.computerMd5(new File("Y:\\Drawing\\0\\0FA\\BSO\\0FABSOU0025-[A].tif"));
    log.info(md5);
  }
}
