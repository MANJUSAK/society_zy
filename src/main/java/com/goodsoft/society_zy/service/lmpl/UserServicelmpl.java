package com.goodsoft.society_zy.service.lmpl;

import com.goodsoft.society_zy.domain.dao.UserDao;
import com.goodsoft.society_zy.domain.entity.result.Result;
import com.goodsoft.society_zy.domain.entity.result.Status;
import com.goodsoft.society_zy.domain.entity.result.StatusEnum;
import com.goodsoft.society_zy.domain.entity.user.User;
import com.goodsoft.society_zy.service.UserService;
import com.goodsoft.society_zy.util.UUIDUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by ASUS on 2017/10/24.
 */
@SuppressWarnings("ALL")
@Service
public class UserServicelmpl implements UserService {
    @Resource
    private UserDao dao;
    @Resource
    private SqlSessionTemplate sqlSession;
    private UUIDUtil uuid = UUIDUtil.getInstance();

    @Override
    public <T> T loginService(String uName, String pwd) {
        User data = null;
        try {
            data = this.dao.loginDao(uName, pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return (T) new Status(StatusEnum.SERVER_ERROR.getCODE(), StatusEnum.SERVER_ERROR.getEXPLAIN());
        }
        if (null != data) {
            return (T) new Result(0, data);
        }
        return (T) new Status(StatusEnum.CHECKUSER.getCODE(), StatusEnum.CHECKUSER.getEXPLAIN());
    }

    @Override
    @Transactional
    public Status addUserService(User msg) throws Exception {
        msg.setId(this.uuid.getUUID().toString());
        this.dao.addUserDao(msg);
        return new Status(StatusEnum.SUCCESS.getCODE(), StatusEnum.SUCCESS.getEXPLAIN());
    }
}
