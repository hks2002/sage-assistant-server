/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2025-03-21 15:17:16                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-08-28 19:20:12                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import java.util.HashSet;
import java.util.Set;

import com.da.sage.assistant.AppConfig;
import com.da.sage.assistant.db.DB;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.auth.authorization.Authorization;
import io.vertx.ext.auth.authorization.Authorizations;
import io.vertx.ext.auth.authorization.PermissionBasedAuthorization;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserService {
  private String ip = "unknown";
  private String adminPassword = AppConfig.config.getString("adminPassword");
  private final ADService adServices = new ADService();

  public UserService() {
  }

  public UserService(String fromIp) {
    this.ip = fromIp;
  }

  public UserService(RoutingContext context) {
    this.ip = context.get("ip").toString();
  }

  public void setupAdminPassword(String adminPassword) {
    this.adminPassword = adminPassword;
  }

  /**
   * login and return the user with permission
   */
  public Future<User> login(UsernamePasswordCredentials credentials) {
    String userName = credentials.getUsername();
    String password = credentials.getPassword();

    if (userName.equals("sa") && password.equals(adminPassword)) {
      return loginAdmin();
    } else {
      // for ldap users:
      return loginByLadp(userName, password);
    }
  }

  /**
   * login and return the admin with permission
   */
  private Future<User> loginAdmin() {
    // for buildIn admin
    LogService.addLog("LOGIN", ip, "as", "SUCCESS", "Administrator");

    return MapperService.query("UserMapper.user", JsonObject.of("login_name", "sa"))
        .compose(val -> {
          JsonArray data = val.getJsonArray("data");
          Integer total = val.getInteger("total");

          if (total.equals(0)) {
            return Future.failedFuture("user admin not exists in database");
          } else if (total.equals(1)) {
            JsonObject userInfo = data.getJsonObject(0);
            // add the missing 'full_name'
            userInfo.put("full_name", userInfo.getString("first_name") + " " + userInfo.getString("last_name"));
            return setAdminPermission(User.create(userInfo));
          } else {
            // should never happened, login name is unique by database
            return Future.failedFuture("User name is not unique");
          }
        });
  }

  /**
   * login and return the user with permission
   */
  public Future<User> loginByLadp(String userName, String password) {
    return adServices.Authenticate(userName, password)
        .onFailure(err -> {
          LogService.addLog("LOGIN", ip, userName, "FAILED");
        })
        .compose(useInfo -> {
          LogService.addLog("LOGIN", ip, userName, "SUCCESS");

          User user = User.create(useInfo);

          return MapperService.query("UserMapper.user", JsonObject.of("login_name", userName))
              .compose(val -> {
                JsonArray data = val.getJsonArray("data");
                Integer total = val.getInteger("total");

                if (total.equals(0)) {
                  return MapperService.insert("UserMapper.user", user.principal()).compose(lastInsertId -> {
                    user.principal().put("id", lastInsertId);
                    return setUserPermission(user);
                  });
                } else if (total.equals(1)) {
                  Integer userId = data.getJsonObject(0).getInteger("id");
                  user.principal().put("id", userId);
                  return setUserPermission(user);
                } else {
                  // should never happened, login name is unique by database
                  return Future.failedFuture("User name is not unique");
                }
              });
        });
  }

  public Future<Void> logout(RoutingContext context) {
    User user = context.user();
    String userName = user.principal().getString("login_name");
    LogService.addLog("LOGOUT", ip, userName, "SUCCESS");
    return Future.succeededFuture();
  }

  public Future<JsonObject> query(JsonObject info) {
    return MapperService.query("UserMapper.user", info);
  }

  public Future<Long> insert(JsonObject info) {
    return MapperService.insert("UserMapper.user", info);
  }

  public Future<Integer> update(JsonObject info) {
    return MapperService.update("UserMapper.user", info);
  }

  public Future<Integer> delete(JsonObject info) {
    return MapperService.delete("UserMapper.user", info);
  }

  public Future<Long> assignPermission(JsonObject permissionInfo) {
    return MapperService.insert("UserPermissionMapper.userPermission", permissionInfo);
  }

  public Future<JsonArray> assignPermissionBatch(JsonArray permissionInfo) {
    return DB.insertBySqlIdBatch("UserPermissionMapper.userPermission", permissionInfo);
  }

  public Future<Integer> unAssignPermission(JsonObject permissionInfo) {
    return MapperService.delete("UserPermissionMapper.userPermission", permissionInfo);
  }

  private Future<User> setAdminPermission(User user) {
    return MapperService.query("PermissionMapper.permission", JsonObject.of("limit", Integer.MAX_VALUE))
        .compose(val -> {
          Set<Authorization> authorizations = new HashSet<>();
          JsonArray data = val.getJsonArray("data");

          for (int i = 0; i < data.size(); i++) {
            JsonObject item = data.getJsonObject(i);
            authorizations.add(PermissionBasedAuthorization.create(item.getString("permission_code")));
          }

          user.authorizations().put("sa", authorizations);
          return Future.succeededFuture(user);
        });
  }

  private Future<User> setUserPermission(User user) {
    JsonObject userInfo = user.principal();
    String loginName = userInfo.getString("login_name");

    Future<JsonObject> f1 = MapperService.query("UserPermissionMapper.userPermission_permission",
        JsonObject.of("login_name", loginName, "limit", Integer.MAX_VALUE));
    Future<JsonObject> f2 = MapperService.query("UserPermissionMapper.userRolePermission",
        JsonObject.of("login_name", loginName, "limit", Integer.MAX_VALUE));

    return Future.all(f1, f2)
        .compose(val -> {

          JsonObject d1 = val.resultAt(0);
          JsonObject d2 = val.resultAt(1);
          JsonArray data1 = d1.getJsonArray("data");
          JsonArray data2 = d2.getJsonArray("data");

          Set<Authorization> authorizations = new HashSet<>();

          for (int i = 0; i < data1.size(); i++) {
            JsonObject item = data1.getJsonObject(i);
            authorizations.add(PermissionBasedAuthorization.create(item.getString("permission_code")));
          }
          for (int i = 0; i < data2.size(); i++) {
            JsonObject item = data2.getJsonObject(i);
            authorizations.add(PermissionBasedAuthorization.create(item.getString("permission_code")));
          }

          user.authorizations().put("sa", authorizations);
          return Future.succeededFuture(user);
        });
  }

  public Future<JsonArray> getUserPermissions(User user) {
    Authorizations authz = user.authorizations();
    JsonArray permissions = new JsonArray();
    authz.forEach((providerId, authorization) -> {
      String permission = authorization.toJson().getString("permission");
      log.debug("permission:{}", permission);
      permissions.add(permission);
    });

    return Future.succeededFuture(permissions);
  }

  public Future<JsonObject> permissionQuery(JsonObject userInfo) {
    return MapperService.query("UserPermissionMapper.userPermission_permission", userInfo);
  }

  public Future<JsonObject> roleQuery(JsonObject userInfo) {
    return MapperService.query("UserRoleMapper.userRole_role", userInfo);
  }

  public Future<Long> assignRole(JsonObject roleInfo) {
    return MapperService.insert("UserRoleMapper.userRole", roleInfo);
  }

  public Future<Integer> unAssignRole(JsonObject roleInfo) {
    return MapperService.delete("UserRoleMapper.userRole", roleInfo);
  }

}
