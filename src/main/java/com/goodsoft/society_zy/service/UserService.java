package com.goodsoft.society_zy.service;

import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.user.User;

/**
 * Created by ASUS on 2017/10/24.
 */
public interface UserService {
    <T> T loginService(String uName, String pwd);

    Status addUserService(User mag) throws Exception;
}
