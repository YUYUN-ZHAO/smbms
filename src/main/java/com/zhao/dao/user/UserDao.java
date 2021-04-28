package com.zhao.dao.user;

import com.zhao.pojo.Role;
import com.zhao.pojo.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    // 得到要登录的用户
    User getLoginUser(Connection connection, String userCode) throws SQLException;

    // 修改用户密码
    int updatePsw(Connection connection, int id, String password) throws SQLException;

    // 根据用户名或者角色查询用户总数
    int getUserCount(Connection connection, String username, int userRole) throws SQLException;

    // 根据用户名或者角色查询用户信息及分页
    List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException;

}
