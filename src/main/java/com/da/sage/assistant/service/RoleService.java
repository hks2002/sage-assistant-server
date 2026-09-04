/***********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                              *
 * @CreatedDate           : 2026-08-31 17:46:25                                                                        *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                              *
 * @LastEditDate          : 2026-08-31 17:50:26                                                                        *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                            *
 **********************************************************************************************************************/
package com.da.sage.assistant.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class RoleService {
  public Future<JsonObject> query(JsonObject info) {
    return MapperService.query("RoleMapper.role", info);
  }

  public Future<Long> insert(JsonObject info) {
    return MapperService.insert("RoleMapper.role", info);
  }

  public Future<Integer> update(JsonObject info) {
    return MapperService.update("RoleMapper.role", info);
  }

  public Future<Integer> delete(JsonObject info) {
    return MapperService.delete("RoleMapper.role", info);
  }

  public Future<JsonObject> permissionQuery(JsonObject info) {
    return MapperService.query("RolePermissionMapper.rolePermission_permission", info);
  }

  public Future<Long> assignPermission(JsonObject info) {
    return MapperService.insert("RolePermissionMapper.rolePermission", info);
  }

  public Future<Integer> unAssignPermission(JsonObject info) {
    return MapperService.delete("RolePermissionMapper.rolePermission", info);
  }

  public Future<JsonObject> userQuery(JsonObject info) {
    return MapperService.query("UserRoleMapper.userRole_user", info);
  }

  public Future<Long> assignUser(JsonObject info) {
    return MapperService.insert("UserRoleMapper.userRole", info);
  }

  public Future<Integer> unAssignUser(JsonObject info) {
    return MapperService.delete("UserRoleMapper.userRole", info);
  }
}
