package com.mooc.san.web.handler;

import com.mooc.san.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: firo
 * @date: 2019-09-11 16:30
 **/


public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String requestUri = ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(requestUri)) {
            return false;
        }
        Object[] parameters = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            parameters[i] = req.getParameter(args[i]);
        }
//        Object ctl = controller.newInstance();
        Object ctl = BeanFactory.getBean(controller);
        Object response = method.invoke(ctl, parameters);
        res.getWriter().println(response.toString());
        return true;
    }
}
