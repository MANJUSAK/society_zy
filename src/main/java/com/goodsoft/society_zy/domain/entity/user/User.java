package com.goodsoft.society_zy.domain.entity.user;

import java.util.Objects;

/**
 * 用户实体
 * Created by ASUS on 2017/10/24.
 */
public class User implements java.io.Serializable {
    private static final long serialVersionUID = -133813466102564310L;
    private String id;
    private String userName;
    private String passWord;
    private String tel;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(passWord, user.passWord) &&
                Objects.equals(tel, user.tel) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passWord, tel, email);
    }
}
