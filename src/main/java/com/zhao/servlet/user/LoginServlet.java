package com.zhao.servlet.user;

import com.zhao.pojo.User;
import com.zhao.service.user.UserService;
import com.zhao.service.user.UserServiceImpl;
import com.zhao.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    // Servlet：控制层，调用业务层代码

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("LoginServlet Start ...........");

        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 和数据库中的密码进行对比
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if (user != null) {
            req.getSession().setAttribute(Constant.USER_SESSION, user);

            // 跳转到主页
            resp.sendRedirect(req.getContextPath() + "/jsp/frame.jsp");
        } else {
            // 转发回登录页面，提示用户名或密码错误
            req.setAttribute("error", "用户名或密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
