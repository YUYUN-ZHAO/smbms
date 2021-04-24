package com.zhao.service.user;

import com.zhao.pojo.User;

public interface UserService {
    // 用户登录
    User login(String userCode, String password);
}
