package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.domain.entity.user.User;
import com.goodsoft.society_zy.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ASUS on 2017/10/24.
 */
@RestController
public class UserController {

    @Resource
    private UserService service;

    @RequestMapping("/login")
    public Object loginController(String uName, String pwd) {
        return this.service.loginService(uName, pwd);
    }

    @RequestMapping("/register")
    public Status addUserController(User msg) {
        try {
            return this.service.addUserService(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return new Status(StatusEnum.SERVER_ERROR.getCODE(), StatusEnum.SERVER_ERROR.getEXPLAIN());
        }
    }
}
