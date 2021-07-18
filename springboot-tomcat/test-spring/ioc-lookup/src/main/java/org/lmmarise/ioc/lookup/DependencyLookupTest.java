package org.lmmarise.ioc.lookup;

import org.lmmarise.ioc.lookup.annotation.Super;
import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 演示多种从 Spring IOC 中查找 Bean 的方法
 *
 * 【通过下面多种依赖查找的方法，我们发现这种操作很麻烦，下面且看✋🏻依赖注入】
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/18 5:02 下午
 */
public class DependencyLookupTest {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        lookupByType(beanFactory);
        lookupRealTime(beanFactory);
        lookupInLazy(beanFactory);
        lookupCollection(beanFactory);
        lookupByAnnotation(beanFactory);
    }

    /**
     * 除了常用的根据 bean id、名称和类型查找，我们还可以通过自定义注解来查找 bean
     */
    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansWithAnnotation = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println(beansWithAnnotation);
        }
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

    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("real:" + user);
    }

    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("lazy:" + user);
    }

    private static void lookupRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("real:" + user);
    }

}
