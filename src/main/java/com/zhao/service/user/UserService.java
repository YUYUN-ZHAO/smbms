package com.zhao.service.user;

import com.zhao.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    // 用户登录
    User login(String userCode, String password);

    // 根据用户id修改密码
    boolean updatePsw(int id, String password);

    // 查询记录数
    int getUserCount(String username, int userRole);

    // 根据用户名或者角色查询用户信息及分页
    List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize);


}
