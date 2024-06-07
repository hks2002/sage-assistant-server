/******************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                     *
 * @CreatedDate           : 2024-06-02 21:34:24                               *
 * @LastEditors           : Robert Huang<56649783@qq.com>                     *
 * @LastEditDate          : 2024-06-07 18:54:00                               *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                   *
 *****************************************************************************/

package com.da.sageassistantserver.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.sageassistantserver.dao.UserMapper;
import com.da.sageassistantserver.model.User;
import com.da.sageassistantserver.utils.SageActionHelper;
import com.da.sageassistantserver.utils.SageActionHelper.MsgTyp;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * Creates a new user in the database and returns a JSONObject indicating the
     * success or failure of the operation.
     *
     * @param user the User object containing the user's information
     * @return a JSONObject with a boolean "success" field indicating the success or
     *         failure of the operation,
     *         and a "message" field with a corresponding message
     */
    public JSONObject createUser(User user) {
        user.setCreate_at(new Date(System.currentTimeMillis()));
        user.setUpdate_at(new Date(System.currentTimeMillis()));
        user.setCreate_by(0L);
        user.setUpdate_by(0L);

        return userMapper.insert(user) == 0
                ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User create failed.")
                : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");

    }

    /**
     * Creates a new user with the given information and returns a JSON object
     * representing the result.
     *
     * @param sage_id    the unique identifier for the user
     * @param login_name the login name for the user
     * @param first_name the first name of the user
     * @param last_name  the last name of the user
     * @param auth       the authorization level of the user
     * @param email      the email address of the user
     * @param language   the preferred language of the user
     * @return a JSON object representing the result of creating the user
     */
    public JSONObject createUser(String sage_id, String login_name,
            String first_name, String last_name,
            String auth, String email, String language) {
        User user = new User();
        user.setSage_id(sage_id);
        user.setLogin_name(login_name);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setAuth(auth);
        user.setEmail(email);
        user.setLanguage(language);

        return createUser(user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param uid the ID of the user to delete
     * @return a JSONObject indicating the success or failure of the deletion
     */
    public JSONObject deleteUser(Long uid) {
        return userMapper.deleteById(uid) == 0
                ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User delete failed.")
                : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    }

    /**
     * Deletes a user from the database based on the provided Sage ID.
     *
     * @param sid the Sage ID of the user to be deleted
     * @return a JSON object indicating the success or failure of the deletion
     *         operation
     */
    public JSONObject deleteUser(String sid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getSage_id, sid);

        return userMapper.delete(queryWrapper) == 0
                ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User delete failed.")
                : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    }

    public JSONObject updateUser(User updateUser, LambdaUpdateWrapper<User> updateWrapper) {
        updateUser.setUpdate_at(new Date(System.currentTimeMillis()));
        updateUser.setUpdate_by(0L);
        return userMapper.update(updateUser, updateWrapper) <= 0
                ? SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User update failed.")
                : SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
    }

    public JSONObject updateUser(String sage_id, String login_name, String first_name, String last_name,
            String auth, String email, String language) {

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getSage_id, sage_id);

        User user = new User();
        user.setLogin_name(login_name);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setAuth(auth);
        user.setEmail(email);
        user.setLanguage(language);
        return updateUser(user, updateWrapper);
    }

    /**
     * Retrieves the profile information of a user based on the provided
     * authentication token.
     *
     * @param auth the authentication token of the user
     * @return a JSON object containing the user's profile information, including
     *         user ID, first name, last name, full name, email, and language,
     *         or an error message if the user is not found
     */
    public JSONObject getProfile(String auth) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAuth, auth);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return SageActionHelper.rtnObj(false, MsgTyp.ERROR, "User not found.");
        }

        JSONObject profile = new JSONObject();
        profile.put("userId", user.getSage_id());
        profile.put("loginName", user.getLogin_name());
        profile.put("firstName", user.getFirst_name());
        profile.put("lastName", user.getLast_name());
        profile.put("userName", user.getFirst_name() + " " + user.getLast_name());
        profile.put("email", user.getEmail());
        profile.put("language", user.getLanguage());

        JSONObject rtn = SageActionHelper.rtnObj(true, MsgTyp.RESULT, "success");
        rtn.put("profile", profile);
        return rtn;
    }

}
