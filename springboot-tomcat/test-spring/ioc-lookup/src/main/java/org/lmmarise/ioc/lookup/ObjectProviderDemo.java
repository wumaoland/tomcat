package org.lmmarise.ioc.lookup;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 8:37 下午
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContextParent = new AnnotationConfigApplicationContext();
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);

        lookupByObjectProvider(applicationContext);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.setParentBeanFactory(applicationContextParent);
        // 获取父容器
        System.out.println(beanFactory.getParentBeanFactory());

        applicationContext.refresh();
        applicationContext.close();
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
    }

    @Bean
    public String helloWorld() {    // 方法名就是Bean的名称
        return "hello world";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
    }

}
