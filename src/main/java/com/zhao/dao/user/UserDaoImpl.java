package com.zhao.dao.user;

import com.mysql.jdbc.StringUtils;
import com.zhao.dao.BaseDao;
import com.zhao.pojo.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user as u inner join smbms_role as r where u.`userRole` = r.`id`");
            ArrayList<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and `u`.`username` Like ?");
                list.add("%" + username + "%");
            }

            if (userRole > 0) {
                sql.append(" and `r`.`id` = ?");
                list.add(userRole);
            }
            System.out.println("sql: " + sql.toString());

            Object[] params = list.toArray();

            resultSet = BaseDao.execute(connection, statement, resultSet, sql.toString(), params);

            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }

            BaseDao.release(null, statement, null);
        }

        return count;
    }

    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
//        List<User>

        if (connection != null){
            ArrayList<Object> list = new ArrayList<Object>();
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*, r.roleName as roleName from smbms_user as u inner join smbms_role as r where u.userRole = r.id");

            if (!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName = ?");
                list.add("%" + userName + "%");
            }

            if (userRole > 0) {
                sql.append(" and r.id = ?");
                list.add(userRole);
            }

            if (currentPageNo > 0 && pageSize > 0){
                int beginIndex = currentPageNo * pageSize;
                sql.append(" order by u.creationDate desc limit ? , ?; ");
                list.add(beginIndex);
                list.add(pageSize);
            }

            System.out.println("sql: " + sql.toString());

            resultSet = BaseDao.execute(connection, statement, resultSet, sql.toString(), list.toArray());

            while (resultSet.next()) {
//                resultSet.getInt();
            }

            BaseDao.release(null, statement, resultSet);
        }

        return null;
    }
}
