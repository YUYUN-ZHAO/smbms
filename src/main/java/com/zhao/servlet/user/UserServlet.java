package com.zhao.servlet.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.zhao.pojo.User;
import com.zhao.service.user.UserService;
import com.zhao.service.user.UserServiceImpl;
import com.zhao.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")) {
            updatePwd(req, resp);
        } else if (method.equals("pwdmodify")) {
            modifyPwd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从Session中获取用户
        Object obj = req.getSession().getAttribute(Constant.USER_SESSION);
        String newPassword = req.getParameter("newpassword");

        boolean flag = false;
        if (obj != null && !StringUtils.isNullOrEmpty(newPassword)){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePsw(((User)obj).getId(), newPassword);

            if (flag) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                // 密码修改成功后，删除当前Session
                req.getSession().removeAttribute(Constant.USER_SESSION);
            } else {
                req.setAttribute("message", "密码修改失败");
            }
        } else {
            req.setAttribute("message", "新密码有问题");
        }

        // 转发到本页
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }


    public void modifyPwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        Object obj = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldPassword = req.getParameter("oldpassword");

        Map<String, String> resultMap = new HashMap<String, String>();

        System.out.println(((User)obj).getUserPassword());
        System.out.println(oldPassword);

        // Session过期失效
        if (obj == null ) {
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldPassword)) {
            resultMap.put("result", "error");
        } else {
            if (oldPassword.equals(((User)obj).getUserPassword())) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();

    }

}
