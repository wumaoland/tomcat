package org.lmmarise.ioc.container;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 这个容器将具有解析注解的能力 {@link org.springframework.context.ApplicationContext} 作为 IoC 容器示例
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/19 12:17 上午
 */
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);  // 将当前类作为配置类，以取代xml配置
        // 属性配置注入，BeanFactory准备，BeanFactory后置处理器调用，注册beanPostProcessor，初始化国际化及事件注册器，
        // 完成BeanFactory，完成refresh方法
        // 启动
        applicationContext.refresh();   // ioc、aop都是在此中完成
        lookupCollection(applicationContext);
        // 停止
        applicationContext.close();
    }

    @Bean   // 将 Java 代码变成配置
    public User user() {
        User user = new User();
        user.setId(100L);
        user.setName("蔡徐坤");
        return user;
    }

    private static void lookupCollection(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            // id作为key，实例作为value的map
            // 查找到的所有User的实例
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(users);
        }
    }

}
