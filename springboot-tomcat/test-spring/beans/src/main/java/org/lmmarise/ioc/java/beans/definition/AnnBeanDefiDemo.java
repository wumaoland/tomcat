package org.lmmarise.ioc.java.beans.definition;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 演示 Bean 的定义
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 11:29 上午
 */
@Import(AnnBeanDefiDemo.Config.class)   // 3. @Import 导入
public class AnnBeanDefiDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annContext = new AnnotationConfigApplicationContext();
        // 给容器注册配置类
        annContext.register(Config.class);
        annContext.refresh();
        // 取出配置类中定义的Bean
        Map<String, Config> configBeans = annContext.getBeansOfType(Config.class);
        Map<String, User> userBeans = annContext.getBeansOfType(User.class);
        System.out.println(configBeans);
        System.out.println(userBeans);
        annContext.close();
    }

    // 2. @Component 定义
    @Component  // 将当前类定义为Spring的组件
    public static class Config {
        // 1. @Bean 定义
        @Bean(name = {"ikun", "kunkun"})
        public User user() {
            User user = new User();
            user.setId(7L);
            user.setName("全世界最好的坤坤");
            return user;
        }
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 32)
                .addPropertyValue("name", "小哥哥");
        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getRawBeanDefinition(), registry);
        }
    }

}
