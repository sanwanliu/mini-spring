package com.mooc.san.beans;

import com.mooc.san.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: firo
 * @date: 2019-09-11 18:58
 **/


public class BeanFactory {
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        while (toCreate.size() != 0) {
            int remainSize = toCreate.size();
            for (int i = 0; i < toCreate.size(); i++) {
                if (finishCreate(toCreate.get(i))) {
                    toCreate.remove(i);
                }
            }
            if (toCreate.size() == remainSize) {
                throw new Exception("circle dependency!");
            }

        }

    }

    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
            return true;
        }
        Object bean = cls.newInstance();
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoWired.class)) {
                Class<?> fieldType = field.getType();
                Object reliantBean = BeanFactory.getBean(fieldType);
                if (reliantBean == null) {
                    return false;
                }
                field.setAccessible(true);
                field.set(bean, reliantBean);
            }
        }
        classToBean.put(cls, bean);
        return true;

    }


}
