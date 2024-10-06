/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2023-03-10 15:42:04                                                                      *
 * @LastEditDate          : 2025-07-18 14:08:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant.utils;

import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PNUtils {

  public static String makeShortPn(String pn) {
    // if PN start with
    // 2C,7C,9C,9R,11C,97A,98A,98D,98F,98S,98L,98V,98F,99A,99D,99F,856A,956A,HU,RRT,330A,332A,350A,365A,9446M,9426M,9429M,9465M,9471M,9448M,9486M
    // end with G01, G01_A
    // remove G01-G09,P01-G09
    // remove end _-
    // remove version number
    log.debug("[makeShortPn] " + pn);
    // change / \ * ? to -
    String newPn = pn.replaceAll("(\\\\|\\*|\\/|\\?)", "-");
    log.debug("[makeShortPn00] " + newPn);
    // remove Complete, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*complete", "");
    // remove history, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*history", "");
    // remove trolley, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*trolley", "");
    // remove full, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*full", "");
    // remove all, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*all", "");
    // remove tds, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*tds", "");
    // remove omsd, case ignore
    newPn = newPn.replaceAll("(?i)omsd(-|_)*", "");
    // remove cad, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*cad", "");
    // remove rev, case ignore
    newPn = newPn.replaceAll("(?i)rev", "");
    // remove dwg, case ignore
    newPn = newPn.replaceAll("(?i)(-|_)*dwg", "");
    // split by space and get the first words
    newPn = newPn.split(" ")[0];
    log.debug("[makeShortPn00] " + newPn);

    // remove _-|__-- at tail
    newPn = newPn.replaceAll("_+", "_");
    newPn = newPn.replaceAll("-+", "-");
    newPn = newPn.replaceAll("(.*?)(_-)$", "$1");
    newPn = newPn.replaceAll("(.*?)(_)*$", "$1");
    newPn = newPn.replaceAll("(.*?)(-)*$", "$1");

    log.debug("[makeShortPn01] " + newPn);
    // remove _DRAFT|_QU|_NQ|_NQD|_CPD_PRT anywhere
    newPn = newPn.replaceAll("(.*)(_DRAFT|_QU|_NQD|_NQ|_CPD|_PRT)(.*)", "$1$3");
    log.debug("[makeShortPn02] " + newPn);
    // remove DRAFT|QU|NQ|NQD|CPDPRT at tail
    newPn = newPn.replaceAll("(.*)(DRAFT|QU|NQD|NQ|CPD|PRT)$", "$1");
    log.debug("[makeShortPn03] " + newPn);
    // remove DRAFT|QU|NQ|NQD|CPD|PRT|AF at midst
    newPn = newPn.replaceAll(
        "(.*)(DRAFT|QU|NQD|NQ|CPD|PRT|AF)([_|-][A-Z|\\d]{1,3})$",
        "$1$3");
    newPn = newPn.replaceAll(
        "(.*)(DRAFT|QU|NQD|NQ|CPD|PRT|AF)(-[\\d]{1,3})(.*)",
        "$1$4");
    log.debug("[makeShortPn04] " + newPn);
    // remove P01 at tail
    newPn = newPn.replaceAll("(.*)(P\\d{2})$", "$1");
    log.debug("[makeShortPn05] " + newPn);
    // remove PO1 at midst
    newPn = newPn.replaceAll("(.*)(P\\d{2})(_[A-Z]{1,3})$", "$1$3");
    log.debug("[makeShortPn06] " + newPn);
    // remove G01 at tail
    newPn = newPn.replaceAll("(.*)(G\\d{2})$", "$1");
    log.debug("[makeShortPn07] " + newPn);
    // remove G01|G01XX at midst
    newPn = newPn.replaceAll("(.*)(G\\d{2})([A-Z]{2})?(_[A-Z]{1,3})$", "$1$4");
    log.debug("[makeShortPn08] " + newPn);

    ///////////////////////////////////////////////////////////////////////////////
    // 9[7|8|9][ADFGKSLV] + 8 bit
    // remove -00, _P-00
    newPn = newPn.replaceAll("^([9][7|8|9][ADFGKSLV])(\\d{8})\\d*(.*)", "$1$2$3");
    newPn = newPn.replaceAll(
        "^([9][7|8|9][ADFGKSLV])(\\d{8})((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$2$6");
    log.debug("[makeShortPn09] " + newPn);

    // 856A|956A + 4 bit
    // remove -00, _P-00
    newPn = newPn.replaceAll("^(856A|956A)(\\d{4})\\d*(.*)", "$1$2$3");
    newPn = newPn.replaceAll(
        "^(856A|956A)(\\d{4})((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$2$6");
    log.debug("[makeShortPn10] " + newPn);

    // 2C|7C|9C|9R|11C + 4 or 5 bit and 2 ?
    // remove -00, _P-00
    newPn = newPn.replaceAll(
        "^(2C|7C|9C|9R|11C\\d{4,5})(-\\d{1,2})?((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$6");
    log.debug("[makeShortPn11] " + newPn);

    newPn = newPn.replaceAll(
        "^(RRT\\d{6})(-\\d{1,3})?((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$2$6");
    log.debug("[makeShortPn12] " + newPn);

    newPn = newPn.replaceAll(
        "^(HU\\d{5})(-\\d{1,3})?((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$2$6");
    log.debug("[makeShortPn13] " + newPn);

    newPn = newPn.replaceAll(
        "^(330A|332A|350A|365A)(\\d{6})\\d*((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$2$6");
    log.debug("[makeShortPn14] " + newPn);

    newPn = newPn.replaceAll(
        "^(94\\d{2}M\\d{2})\\d*((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$5");
    log.debug("[makeShortPn15] " + newPn);

    newPn = newPn.replaceAll(
        "^(98AMS\\d{6})\\d*((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$5");
    log.debug("[makeShortPn16] " + newPn);

    newPn = newPn.replaceAll(
        "^(98DNSA\\d{5})\\d*((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$5");
    log.debug("[makeShortPn17] " + newPn);

    newPn = newPn.replaceAll(
        "^([A|B|C|F|G|J|K]\\d{5})(-\\d{1,3})?((-[0-9|A-Z]{0,2})|(_P-\\d{1,3}))?(.*)",
        "$1$6");
    log.debug("[makeShortPn18] " + newPn);

    // remove version
    newPn = newPn.replaceAll("(.*)([_|-][A-Z|\\d]{1,3})$", "$1");

    if (!StringUtils.hasText(newPn)) {
      log.error("[makeShortPn] " + pn);
    }
    return newPn;
  }
}
