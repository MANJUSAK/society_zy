package com.goodsoft.society_zy.domain.dao;

import com.goodsoft.society_zy.domain.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 2017/10/24.
 */
@Repository
public interface UserDao {

    User loginDao(@Param("uName") String uName, @Param("pwd") String pwd) throws Exception;

    int addUserDao(User msg) throws Exception;
}
