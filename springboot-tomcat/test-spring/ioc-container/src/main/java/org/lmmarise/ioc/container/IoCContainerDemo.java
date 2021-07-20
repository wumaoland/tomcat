package org.lmmarise.ioc.container;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/19 12:07 上午
 */
public class IoCContainerDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 加载配置
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 加载资源
        int beanDefinitions = reader.loadBeanDefinitions("classpath:/META-INF/dependency-injection-context.xml");
        System.out.println("Bean 加载的数量：" + beanDefinitions);
        // 根据 Bean.class 从 IoC 容器中查出其实例
        lookupCollection(beanFactory);
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
