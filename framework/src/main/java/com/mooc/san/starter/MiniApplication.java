package com.mooc.san.starter;

import com.mooc.san.beans.BeanFactory;
import com.mooc.san.core.ClassScanner;
import com.mooc.san.web.handler.HandlerManager;
import com.mooc.san.web.server.TomcatServer;

import java.util.Arrays;
import java.util.List;

public class MiniApplication {
    public static void run(Class<?> cls, String[] args) {
    System.out.println("Hello mini-spring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classes = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classes);
            HandlerManager.resolveMappingHandler(classes);
            classes.forEach(it -> System.out.println(it.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
