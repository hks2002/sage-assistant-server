/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2024-06-02 21:34:24                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-06-07 14:30:44                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.sql.Date;
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
        userFunc.setCreate_at(new Date(System.currentTimeMillis()));
        userFunc.setUpdate_at(new Date(System.currentTimeMillis()));
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
     * @param sage_id     the sage_id of the user
     * @param func_system the system of the user function
     * @param func_code   the code of the user function
     * @param func_name   the name of the user function
     * @param enable      the enable status of the user function
     * @return a JSONObject indicating the success or failure of the operation
     */
    public JSONObject addUserFunc(String sage_id, String func_system, String func_code,
            String func_name, Boolean enable) {
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
        userFunc.setEnable(enable);

        return addUserFunc(userFunc);
    }

    /**
     * Adds a new user function to the system.
     *
     * @param id          the ID of the user
     * @param func_system the system of the function
     * @param func_code   the code of the function
     * @param func_name   the name of the function
     * @param enable      indicates whether the function is enabled or not
     * @return a JSON object indicating the success or failure of the operation
     */
    public JSONObject addUserFunc(Long id, String func_system, String func_code,
            String func_name, Boolean enable) {
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
    public JSONObject deleteUserFunc(Long id) {
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
        updateUserFunc.setUpdate_at(new Date(System.currentTimeMillis()));
        updateUserFunc.setUpdate_by(0L);
        return userFuncMapper.update(updateUserFunc, updateWrapper) <= 0
                ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User function update failed.")
                : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    }

    public JSONObject updateUserFuncBySid(String sid, String functions) {
        LambdaUpdateWrapper<UserFunc> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserFunc::getSage_id, sid);

        UserFunc userFunc = new UserFunc();
        userFunc.setSage_id(sid);
        userFunc.setFunc_system("SAGE");
        userFunc.setFunc_code(functions);
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
    public JSONObject getFunctions(String auth) {
        List<String> functions = userFuncMapper.findSageFuncByAuth(auth);

        JSONObject rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
        rtn.put("functions", Utils.ListToString(functions));
        return rtn;
    }

    /**
     * Retrieves the functions associated with a given sid.
     *
     * @param sid the sage id of the user
     * @return a JSON object containing the list of functions
     */
    public JSONObject getFunctionsBySid(String sid) {
        List<String> functions = userFuncMapper.findSageFuncBySid(sid);

        JSONObject rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
        rtn.put("functions", Utils.ListToString(functions));
        return rtn;
    }
}
