package com.zhao.filter;

import com.zhao.pojo.User;
import com.zhao.util.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);

        if (user == null){
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        } else {
            chain.doFilter(request, response);
        }

    }

    public void destroy() {

    }
}
