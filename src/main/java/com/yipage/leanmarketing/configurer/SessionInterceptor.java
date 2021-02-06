package com.yipage.leanmarketing.configurer;

import com.alibaba.fastjson.JSON;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultCode;
import com.yipage.leanmarketing.model.Administrator;
import com.yipage.leanmarketing.utils.TokenManagerUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session拦截器
 */
public class SessionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //登录不做拦截
        if (request.getRequestURI().contains("/page/")) {
            Administrator administrator = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);
            if (administrator == null) {
                response.sendRedirect("/login.jsp");
            }
            return true;
        }

        // 判断登陆逻辑
        if (o instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) o;
            NeedLoginAnnotation rl = hm.getMethodAnnotation(NeedLoginAnnotation.class);
            if (rl != null) {

                if (!TokenManagerUtil.checkToken(request.getHeader("token"))) {

                    Result result = new Result();
                    result.setCode(ResultCode.NEEDLOGIN).setMessage("需要登录");
                    try {
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(JSON.toJSONString(result));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
