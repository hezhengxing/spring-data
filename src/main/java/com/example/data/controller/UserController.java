package com.example.data.controller;

import com.example.data.config.BaseController;
import com.example.data.config.BaseResult;
import com.example.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hzx
 * @Date: 2018/8/20 16:45
 * @Description:
 * @Modify By:
 */
@RestController
@RequestMapping("/v1/UserController")
public class UserController extends BaseController{
    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public BaseResult addUser(String name ,String password) {
        userService.addUser(name, password);
        return sendResult200();
    }
}
