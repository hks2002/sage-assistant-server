/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-07-02 15:44:21                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-03 12:59:17                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

package com.da.sageassistantserver.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.da.sageassistantserver.model.Docs;
import com.da.sageassistantserver.utils.Utils;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;

import lombok.extern.slf4j.Slf4j;

/**
 * Dms server is provided for Audros, which is stored new attachments for Sage.
 * <p>
 * This class is mainly provide APIs for Dms server.
 * Core is get fileId by PN searched.
 * <ul>
 * <li>extractFileId</li>
 * <li>extractFileModifiedDate</li>
 * <li>getDocuments</li>
 * <li>getDocument</li>
 * <li>getDocumentContent</li>
 * </ul>
 */
@Slf4j
public class DmsService {
    private static LoadingCache<String, String> dmsSessionCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .removalListener((RemovalListener<String, String>) (key, value, cause) -> {
                /*
                 * Dms will expired created session automatically, we exit page to avoid this
                 */
                log.info("[Dms] SessionId cache {} is removed, cause is {}", value, cause);
                doLogout(value);
            })
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String Auth) {
                    log.info("[Dms] session cache login with Auth: {}", Auth);
                    return doLogin(Auth);
                }
            });

    private static String encode(String value) {
        try {
            return Base64.getEncoder().encodeToString(URLEncoder.encode(value, "UTF-8").getBytes());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private static List<String> extractLIValues(String htmlContent) {
        List<String> liValues = new ArrayList<>();
        Pattern pattern = Pattern.compile("<LI>(.*?)</LI>");
        Matcher matcher = pattern.matcher(htmlContent);

        while (matcher.find()) {
            liValues.add(matcher.group(1));
        }

        log.debug("liValues: {}", liValues);
        return liValues;
    }

    private static String extractModifiedDate(String htmlContent) {
        String date = "";
        Pattern pattern = Pattern.compile("<td data-name-attr=\"obj_modificationdate\" nowrap=\"1\">(.*?)</td>");
        Matcher matcher = pattern.matcher(htmlContent);

        while (matcher.find()) {
            date = matcher.group(1);
        }

        log.debug("date: {}", date);
        return date;
    }

    private static String extractSessionId(String htmlContent) {
        String id = "";
        Pattern pattern = Pattern.compile("sSessionID = '(.*?)';");
        Matcher matcher = pattern.matcher(htmlContent);

        while (matcher.find()) {
            id = matcher.group(1);
        }

        log.debug("id: {}", id);
        return id;
    }

    private static String extractFileId(String htmlContent) {
        Pattern pattern = Pattern.compile("var id\t\t= '(.*?)';");
        Matcher matcher = pattern.matcher(htmlContent);
        String id = null;
        while (matcher.find()) {
            id = matcher.group(1);
        }
        return id;
    }

    private static String doLogin(String Auth) {
        SageLoginService.doLogin(Auth);
        String html = HttpService.request(
                "http://192.168.10.64:4040/cocoon/View/LoginCAD/fr/AW_AutoLogin.html?userName=TEMP&dsn=dmsDS&Client_Type=25&computerName=AWS&LDAPControl=true",
                "GET", null, null).body();
        return extractSessionId(html);
    }

    private static void doLogout(String sessionId) {
        HttpService.request(
                "http://192.168.10.64:4040/cocoon/View/LogoutXML/fr/AW_Logout7.html?userName=TEMP&dsn=dmsDS&Client_Type=25&AUSessionID="
                        + sessionId,
                "GET", null, null).body();
    }

    private static List<String> getAttachmentNames(String Auth, String Pn) {
        List<String> attachments = new ArrayList<>();
        String sessionId = dmsSessionCache.get(Auth);
        String text = encode(Pn);
        String xml = HttpService.request(
                "http://192.168.10.64:4040/cocoon/View/ExecuteService/fr/AW_AuplResult3.html?ServiceName=aws.au&ServiceParameters=GET_AUTOCOMPLETION@"
                        + text
                        + "@&ServiceSubPackage=aws&UserName=TEMP&dsn=dmsDS&Client_Type=25&AUSessionID=" + sessionId,
                "GET", null, null).body();
        List<String> liValues = extractLIValues(xml);

        for (int i = 0; i < liValues.size(); i++) {
            if (Utils.getFileExt(liValues.get(i)).isEmpty()) {
                continue;
            }
            attachments.add(liValues.get(i));
        }

        return attachments;
    }

    private static String getFileInfo(String Auth, String FileName) {
        String sessionId = dmsSessionCache.getIfPresent(Auth);
        String text = encode(FileName);
        String html = HttpService.request(
                "http://192.168.10.64:4040/cocoon/View/ExecuteService/fr/AW_QuickSearchView7.post?ServiceName=aws.au&ServiceParameters=GET_OBJECTS_LIST@SEARCH@"
                        + text
                        + "@@@0@9999@0@&ServiceSubPackage=aws&URL_Encoding=UTF-8&date_format=enDateHour&AUSessionID="
                        + sessionId,
                "GET", null, null).body();
        return html;
    }

    public static List<Docs> getDocuments(String Auth, String Pn) {
        List<String> attachments = getAttachmentNames(Auth, Pn);

        List<Docs> docs = new ArrayList<>();
        for (int i = 0; i < attachments.size(); i++) {
            String html = getFileInfo(Auth, attachments.get(i));
            String id = extractFileId(html);
            String dateString = extractModifiedDate(html);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);
            Date date;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                log.error(e.getMessage());
                date = new Date();
            }
            Timestamp modifiedAt = new Timestamp(date.getTime());

            Docs doc = new Docs();
            doc.setFile_name(attachments.get(i));
            doc.setDoc_modified_at(modifiedAt);
            doc.setLocation(id); // using file id as location
            if (id != null) {
                docs.add(doc);
            }
        }
        return docs;
    }

    public static byte[] getDocumentContent(String fileId) {
        byte[] data = HttpService.getFile(
                "http://192.168.10.64:4040/cocoon/viewDocument/ANY?FileID="
                        + fileId
                        + "&UserName=TEMP&dsn=dmsDS&Client_Type=25");
        return data;
    }

}
