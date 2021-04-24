package com.zhao.service.user;

import com.zhao.dao.BaseDao;
import com.zhao.dao.user.UserDao;
import com.zhao.dao.user.UserDaoImpl;
import com.zhao.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    // 业务层都会调用dao层，所以要引入dao层
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);

            // 如果查询出的密码和输入的密码不相同，user置空
            if (!password.equals(user.getUserPassword())){
                user = null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.release(connection, null, null);
        }

        return user;
    }

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login("admin", "111");
        System.out.println(user.getUserPassword());

    }

}
