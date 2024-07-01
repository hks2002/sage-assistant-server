/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2024-06-02 21:34:24                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-07-01 16:49:41                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.sageassistantserver.dao.UserFuncMapper;
import com.da.sageassistantserver.dao.UserMapper;
import com.da.sageassistantserver.model.User;
import com.da.sageassistantserver.model.UserFunc;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;
import com.da.sageassistantserver.utils.Utils;

@Service
public class UserFuncService {
  @Autowired
  UserFuncMapper userFuncMapper;

  @Autowired
  UserMapper userMapper;

  /**
   * Adds a new user function to the database and returns a JSONObject indicating
   * the success or failure of the operation.
   *
   * @param userFunc the UserFunc object containing the user function's
   *                 information
   * @return a JSONObject with a boolean "success" field indicating the success or
   *         failure of the operation,
   *         and a "type" field indicating the type of message (ERROR or RESULT)
   */
  public JSONObject addUserFunc(UserFunc userFunc) {
    userFunc.setCreate_at(new Timestamp(System.currentTimeMillis()));
    userFunc.setUpdate_at(new Timestamp(System.currentTimeMillis()));
    userFunc.setCreate_by(0L);
    userFunc.setUpdate_by(0L);

    if (userFuncMapper.insert(userFunc) == 0) {
      return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User Func add failed.");
    } else {
      return SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    }
  }

  /**
   * Adds a new user function to the database for the given sage_id, func_system,
   * func_code,
   * func_name, and enable status.
   *
   * @param sage_id      the sage_id of the user
   * @param func_system  the system of the user function
   * @param func_code    the code of the user function
   * @param func_name    the name of the user function
   * @param func_details the details of the user function
   * @param enable       the enable status of the user function
   * @return a JSONObject indicating the success or failure of the operation
   */
  public JSONObject addUserFuncBySid(String sage_id, String func_system, String func_code, String func_name,
      String func_details, Boolean enable) {
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getSage_id, sage_id);
    User user = userMapper.selectOne(queryWrapper);

    if (user == null) {
      return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User not found.");
    }

    UserFunc userFunc = new UserFunc();
    userFunc.setUser_id(user.getId());
    userFunc.setSage_id(sage_id);
    userFunc.setFunc_system(func_system);
    userFunc.setFunc_code(func_code);
    userFunc.setFunc_name(func_name);
    userFunc.setFunc_details(func_details);
    userFunc.setEnable(enable);

    return addUserFunc(userFunc);
  }

  /**
   * Adds a new user function to the system.
   *
   * @param id           the ID of the user
   * @param func_system  the system of the function
   * @param func_code    the code of the function
   * @param func_name    the name of the function
   * @param func_details the details of the user function
   * @param enable       indicates whether the function is enabled or not
   * @return a JSON object indicating the success or failure of the operation
   */
  public JSONObject addUserFuncByUid(Long id, String func_system, String func_code, String func_name,
      String func_details, Boolean enable) {
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getId, id);
    User user = userMapper.selectOne(queryWrapper);

    if (user == null) {
      return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User not found.");
    }

    UserFunc userFunc = new UserFunc();
    userFunc.setUser_id(id);
    userFunc.setSage_id(user.getSage_id());
    userFunc.setFunc_system(func_system);
    userFunc.setFunc_code(func_code);
    userFunc.setFunc_name(func_name);
    userFunc.setFunc_details(func_details);
    userFunc.setEnable(enable);

    return addUserFunc(userFunc);
  }

  /**
   * Deletes a user function by its ID and returns a JSON object indicating the
   * success or failure of the operation.
   *
   * @param id the ID of the user function to be deleted
   * @return a JSON object indicating the success or failure of the operation
   */
  public JSONObject deleteUserFuncById(Long id) {
    return userFuncMapper.deleteById(id) == 0
        ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User function delete failed.")
        : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
  }

  public JSONObject deleteUserFuncBySid(String sid) {
    LambdaQueryWrapper<UserFunc> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(UserFunc::getSage_id, sid);

    return userFuncMapper.delete(queryWrapper) == 0
        ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User function delete failed.")
        : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
  }

  public JSONObject deleteUserFuncByUid(Long uid) {
    LambdaQueryWrapper<UserFunc> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(UserFunc::getUser_id, uid);

    return userFuncMapper.delete(queryWrapper) == 0
        ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User function delete failed.")
        : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
  }

  public JSONObject updateUserFunc(UserFunc updateUserFunc, LambdaUpdateWrapper<UserFunc> updateWrapper) {
    updateUserFunc.setUpdate_at(new Timestamp(System.currentTimeMillis()));
    updateUserFunc.setUpdate_by(0L);
    return userFuncMapper.update(updateUserFunc, updateWrapper) <= 0
        ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User function update failed.")
        : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
  }

  public JSONObject updateSageActionsBySid(String sid, String functions) {
    LambdaUpdateWrapper<UserFunc> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.eq(UserFunc::getSage_id, sid);

    UserFunc userFunc = new UserFunc();
    userFunc.setSage_id(sid);
    userFunc.setFunc_system("SAGE");
    userFunc.setFunc_code("ACTIONS");
    userFunc.setFunc_details(functions);
    userFunc.setFunc_name("functions in sage");
    userFunc.setEnable(true);
    return updateUserFunc(userFunc, updateWrapper);
  }

  /**
   * Retrieves the profile information for a given authentication token.
   *
   * @param auth the authentication token
   * @return a JSON object containing the profile information, including the list
   *         of functions
   */
  public JSONObject getSageActionsByAuth(String auth) {
    List<String> functions = userFuncMapper.findSageActionsByAuth(auth);

    JSONObject rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    rtn.put("functions", functions.toArray());
    return rtn;
  }

  /**
   * Retrieves the functions associated with a given sid.
   *
   * @param sid the sage id of the user
   * @return a JSON object containing the list of functions
   */
  public JSONObject getSageActionsBySid(String sid) {
    List<String> functions = userFuncMapper.findSageActionsBySid(sid);

    JSONObject rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    rtn.put("functions", Utils.ListToString(functions));
    return rtn;
  }

  public List<UserFunc> getWebDavAccess(String login_name) {
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getLogin_name, login_name);
    User user = userMapper.selectOne(queryWrapper);

    if (user == null) {
      return new ArrayList<>();
    }

    LambdaQueryWrapper<UserFunc> queryWrapper2 = new LambdaQueryWrapper<>();
    queryWrapper2.eq(UserFunc::getUser_id, user.getId());
    queryWrapper2.eq(UserFunc::getFunc_system, "WEBDAV");
    return userFuncMapper.selectList(queryWrapper2);
  }

  public boolean initWebDavAccessByLoginName(String login_name) {
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getLogin_name, login_name);
    User user = userMapper.selectOne(queryWrapper);

    if (user == null) {
      return false;
    }

    LambdaQueryWrapper<UserFunc> queryWrapper2 = new LambdaQueryWrapper<>();
    queryWrapper2.eq(UserFunc::getFunc_system, "WEBDAV");
    queryWrapper2.eq(UserFunc::getUser_id, user.getId());
    List<UserFunc> userFuncs = userFuncMapper.selectList(queryWrapper2);
    if (userFuncs.size() > 0) {
      return true;
    } else {

      UserFunc userFunc = new UserFunc();

      userFunc.setUser_id(user.getId());
      userFunc.setFunc_system("WEBDAV");
      userFunc.setFunc_code("WRITE");
      userFunc.setFunc_name("write webdav");
      userFunc.setEnable(false);
      userFuncMapper.insert(userFunc);

      userFunc = new UserFunc();
      userFunc.setUser_id(user.getId());
      userFunc.setFunc_system("WEBDAV");
      userFunc.setFunc_code("READ");
      userFunc.setFunc_name("read webdav");
      userFunc.setEnable(false);
      userFuncMapper.insert(userFunc);

      userFuncs = userFuncMapper.selectList(queryWrapper2);
      return userFuncs.size() > 0;
    }
  }

  public List<UserFunc> getFunctions(String system, String code) {
    LambdaQueryWrapper<UserFunc> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(UserFunc::getFunc_system, system);
    queryWrapper.eq(UserFunc::getFunc_code, code);
    return userFuncMapper.selectList(queryWrapper);
  }

  public List<UserFunc> getFunctions(String system) {
    LambdaQueryWrapper<UserFunc> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(UserFunc::getFunc_system, system);
    return userFuncMapper.selectList(queryWrapper);
  }
}
