package com.zhao.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {

    private static String url;
    private static String username;
    private static String password;

    // 静态语句块，在类创建时首先加载MySQL驱动
    static {
        // 加载流文件
        try {
            // BaseDao.class：获得对象; getClassLoader()类加载器; getResourceAsStream("db.properties")：加载资源文件放到输入流中
            InputStream inputStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

            // 创建Properties类型对象
            Properties p = new Properties();
            p.load(inputStream);
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");

            // 加载MySQL驱动
            Class.forName(p.getProperty("driver"));
            System.out.println("驱动加载成功");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("驱动加载失败");
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }



    // 查询
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++){
            preparedStatement.setObject(i+1, params[i]);
        }

        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    // 增删改
    public static int execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++){
            preparedStatement.setObject(i+1, params[i]);
        }

        return preparedStatement.executeUpdate();
    }

    // 释放资源
    public static boolean release(Connection conn, Statement statement, ResultSet result) {
        boolean flag = true;

        if (result != null) {
            try {
                result.close();
                result = null;
            } catch (SQLException e) {
                flag = false;
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
                statement = null;
            } catch (SQLException e) {
                flag = false;
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }

}
