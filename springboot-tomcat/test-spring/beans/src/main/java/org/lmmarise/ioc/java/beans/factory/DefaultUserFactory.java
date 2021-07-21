package org.lmmarise.ioc.java.beans.factory;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * Bean 工厂
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 1:53 下午
 */
public class DefaultUserFactory implements UserFactory, InitializingBean {

    // 1.基于注解的Bean初始化
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct DefaultUserFactory 初始化中。。。");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }

    // @Bean(initMethod = "init1")
    public void init1() {
        System.out.println("initMethod");
    }

}
