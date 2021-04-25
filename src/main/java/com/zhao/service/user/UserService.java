package com.zhao.service.user;

import com.zhao.pojo.User;

public interface UserService {
    // 用户登录
    User login(String userCode, String password);

    // 根据用户id修改密码
    boolean updatePsw(int id, String password);

}
