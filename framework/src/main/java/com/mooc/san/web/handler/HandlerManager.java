package com.mooc.san.web.handler;

import com.mooc.san.web.mvc.Controller;
import com.mooc.san.web.mvc.RequestMapping;
import com.mooc.san.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: firo
 * @date: 2019-09-11 16:35
 **/


public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList) {
        for (Class<?> cls : classList) {
            if (cls.isAnnotationPresent(Controller.class)) {
                parseHandlerFromController(cls);
            }
        }
    }

    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> parameterNameList = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    parameterNameList.add(parameter.getAnnotation(RequestParam.class).value());
                }
            }
            String[] params = parameterNameList.toArray(new String[parameterNameList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
            mappingHandlerList.add(mappingHandler);
        }
    }
}
