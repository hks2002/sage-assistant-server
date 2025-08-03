/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2025-03-28 00:03:05                                                                       *
 * @LastEditDate          : 2025-08-06 12:49:44                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.service;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.da.sage.assistant.config.ADProperties;
import com.da.sage.assistant.utils.CommonUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ADServices {

  private ADProperties adProperties = null;

  public ADServices(ADProperties adProperties) {
    log.info("ADServices init:{}", adProperties);
    this.adProperties = adProperties;
  }

  /**
   * Authenticates a user against an Active Directory server and retrieves user
   * information.
   *
   * @param Auth The authentication token
   * @return A JsonObject with user details (login_name, first_name, last_name,
   *         email, full_name) if authentication succeeds, or a failed Future if
   *         authentication fails
   */
  public JSONObject adAuthorization(String Auth) {
    try {
      String plantUserPassword = CommonUtils.decodeBasicAuth(Auth);
      String[] userPassword = plantUserPassword.split(":");
      String username = userPassword[0];
      String password = userPassword[1];

      return adAuthorization(username, password);
    } catch (Exception e) {
      log.error("adAuthorization error: ", e);
      return null;
    }
  }

  /**
   * Authenticates a user against an Active Directory server and retrieves user
   * information.
   *
   * @param username The username to authenticate
   * @param password The password for authentication
   * @return A JsonObject with user details (login_name, first_name, last_name,
   *         email, full_name) if authentication succeeds, or a failed Future if
   *         authentication fails
   */
  public JSONObject adAuthorization(String username, String password) {

    Hashtable<String, String> env = new Hashtable<>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.PROVIDER_URL, adProperties.getUrl());
    env.put(Context.SECURITY_PRINCIPAL, username + "@" + adProperties.getDomain());
    env.put(Context.SECURITY_CREDENTIALS, password);

    // Skip SSL verification if using LDAPS
    // hostname verification still works, so must using a valid full hostname
    if (adProperties.getUrl().toLowerCase().startsWith("ldaps")) {
      env.put("java.naming.ldap.factory.socket", "com.da.sage.assistant.utils.TrustAllSSLSocketFactory");
    }

    DirContext dirCtx = null;
    JSONObject user = null;

    try {
      // dirCtx = new InitialDirContext(env);
      dirCtx = new InitialDirContext(env);

      SearchControls searchControls = new SearchControls();
      searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
      searchControls.setCountLimit(1);
      searchControls.setTimeLimit(10000);
      String searchFilter = "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + username + "))";

      NamingEnumeration<SearchResult> results = dirCtx.search(adProperties.getSearchBase(), searchFilter,
          searchControls);

      if (results.hasMore()) {
        SearchResult searchResult = results.next();
        Attributes attributes = searchResult.getAttributes();

        // [some attributes]:
        // name(cn)
        // givenName
        // sn
        // sAMAccountName
        // mail
        // memberOf
        // whenCreated
        // whenChanged

        Attribute loginName = attributes.get("sAMAccountName");
        Attribute sn = attributes.get("sn");
        Attribute givenName = attributes.get("givenName");
        Attribute mail = attributes.get("mail");
        // Attribute memberOf = attributes.get("memberOf");
        // Attribute whenCreated = attributes.get("whenCreated");
        // Attribute whenChanged = attributes.get("whenChanged");

        user = new JSONObject();
        user.put("login_name", loginName == null ? "" : loginName.toString().split(": ")[1]);
        user.put("first_name", givenName == null ? "" : givenName.toString().split(": ")[1]);
        user.put("last_name", sn == null ? "" : sn.toString().split(": ")[1]);
        user.put("email", mail == null ? "" : mail.toString().split(": ")[1]);
        user.put("full_name", user.getString("first_name") + " " + user.getString("last_name"));
      }
    } catch (AuthenticationException e) {
      log.info("Login failed for user {}", username);
      log.info("AuthenticationException:", e.getCause());
    } catch (NamingException e) {
      log.error("Login failed for user {}", username);
      log.error("NamingException:", e.getCause());
    } finally {
      if (dirCtx != null) {
        try {
          dirCtx.close();
        } catch (NamingException e) {
          log.error("{}", e);
        }
      }
    }

    // return user, it may be null
    return user;
  }
}
