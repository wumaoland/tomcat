package org.lmmarise.ioc.injection;

import org.lmmarise.ioc.injection.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/18 9:08 下午
 */
public class DependencyInjectionTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一：自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository.getBeanFactory() == applicationContext);  // false

        // 依赖来源二：依赖注入（内建的依赖）
        System.out.println(userRepository.getBeanFactory());

        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
        ApplicationContext object = objectFactory.getObject();
        System.out.println(object);
        // 说明 ObjectFactory 帮我们注入一个 ApplicationContext
        System.out.println(object == applicationContext);      // true

        // 依赖来源三：容器内建的Bean，非业务构建的Bean
        Environment env = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 的 Bean：" + env);
    }

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {
        // 依赖查找 (错误)
        // System.out.println(beanFactory.getBean(BeanFactory.class));

        // ApplicationContext is BeanFactory
    }

}
