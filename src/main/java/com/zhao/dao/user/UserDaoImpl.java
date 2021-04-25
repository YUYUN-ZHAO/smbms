package com.zhao.dao.user;

import com.zhao.dao.BaseDao;
import com.zhao.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};

            result = BaseDao.execute(connection, statement, result, sql, params);

            if (result.next()) {
                user = new User();
                user.setId(result.getInt("id"));
                user.setUserCode(result.getString("userCode"));
                user.setUserName(result.getString("userName"));
                user.setUserPassword(result.getString("userPassword"));
                user.setGender(result.getInt("gender"));
                user.setBirthday(result.getDate("birthday"));
                user.setPhone(result.getString("phone"));
                user.setAddress(result.getString("address"));
                user.setUserRole(result.getInt("userRole"));
                user.setCreatedBy(result.getInt("createdBy"));
                user.setCreationDate(result.getTimestamp("creationDate"));
                user.setModifyBy(result.getInt("modifyBy"));
                user.setModifyDate(result.getTimestamp("modifyDate"));
            }
            BaseDao.release(null, statement, result);
        }

        return user;
    }


    public int updatePsw(Connection connection, int id, String password) throws SQLException {
        PreparedStatement statement = null;
        int result = 0;

        if (connection != null) {
            String sql = "update smbms_user set userPassword=? where id=?";
            Object[] params = {password, id};
            result = BaseDao.execute(connection, statement, sql, params);
            BaseDao.release(null, statement, null);
        }

        return result;
    }


}
