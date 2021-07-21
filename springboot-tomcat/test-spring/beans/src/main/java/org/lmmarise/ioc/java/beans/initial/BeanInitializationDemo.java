package org.lmmarise.ioc.java.beans.initial;

import org.lmmarise.ioc.java.beans.factory.DefaultUserFactory;
import org.lmmarise.ioc.java.beans.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 2:53 下午
 */
@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        System.out.println("上下文启动");
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);    // 延时初始化是由依赖查找触发其初始化
        System.out.println(userFactory);

        applicationContext.close();
    }

    @Bean(initMethod = "init1")
    @Lazy(value = false)   // false在Spring应用上线文启动完成前被初始化；true在后初始化
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }



}
