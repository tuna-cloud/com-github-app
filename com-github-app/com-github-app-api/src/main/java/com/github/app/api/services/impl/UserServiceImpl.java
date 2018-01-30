package com.github.app.api.services.impl;

import com.github.app.api.services.UserService;
import com.github.app.utils.MD5Utils;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

//    @Autowired
//    private UserMapper userMapper;

    @Override
    public boolean auth(String account, String password) {
        /*
        UserExample userExample = new UserExample();
        userExample.or().andAccountEqualTo(account);
        List<User> list = userMapper.selectByExample(userExample);

        if(list == null || list.size() != 1)
            return false;

        if(!MD5Utils.validateMd5WithSalt(password, list.get(0).getPassword())) {
            return false;
        }
        */

        return true;
    }

    @Override
    public JsonObject selectUserInfoByAccount(String account) {
        /*
        UserExample userExample = new UserExample();
        userExample.or().andAccountEqualTo(account);
        List<User> list = userMapper.selectByExampleWithRole(userExample);

        if(list == null || list.size() != 1)
            return null;

        JsonObject jsonObject = JsonObject.mapFrom(list.get(0));
        jsonObject.remove("password");
        return jsonObject;
        */
        return null;
    }

}
