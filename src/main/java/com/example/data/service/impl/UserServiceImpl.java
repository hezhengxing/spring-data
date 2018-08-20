package com.example.data.service.impl;

import com.example.data.config.Constants;
import com.example.data.entity.User;
import com.example.data.repository.UserRepository;
import com.example.data.service.UserService;
import com.example.data.utils.RedisTemplateUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: hzx
 * @Date: 2018/8/20 16:28
 * @Description:
 * @Modify By:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisTemplateUtil redisTemplateUtil;
    @Override
    public void addUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userRepository.save(user);
        redisTemplateUtil.cacheStringValue("user:",Constants.USER_VALUE_SAVE, JSONObject.valueToString(user));
    }
}
