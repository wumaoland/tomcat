package org.lmmarise.ioc.injection.repository;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/18 10:18 下午
 */
public class UserRepository {
    private Collection<User> users;
    // 内建依赖，非Spring的Bean
    // 依赖分为：Bean、非Bean
    private BeanFactory beanFactory;    // 内建的非 Bean （依赖）
    private ObjectFactory<ApplicationContext> objectFactory;    // 从get方法获取到的：ClassPathXmlApplicationContext实例对象
//    private ObjectFactory<User> userObjectFactory;            // 从get方法获取到的：User实例对象

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }
}
