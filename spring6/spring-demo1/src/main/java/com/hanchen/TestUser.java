package com.hanchen;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;

public class TestUser {

    /**
     * 日志对象
     */
    Logger logger = LoggerFactory.getLogger(TestUser.class);

    /**
     * spring 获取类
     */
    @Test
    public void testSpring() {

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
            User user = (User) context.getBean("userq");
            user.add();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 反射创建类
     */
    @Test
    public void testReflex() throws Exception {
        Class<?> clazz = Class.forName("com.hanchen.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        User user = (User) constructor.newInstance();
        user.add();
    }
}
