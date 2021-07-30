package org.lmmarise.ioc.java.beans.initial;

import org.lmmarise.ioc.java.beans.factory.DefaultUserFactory;
import org.lmmarise.ioc.java.beans.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 2:53 下午
 */
@PropertySource(value = "classpath:/META-INF/default.properties", encoding = "UTF-8")   // 外部化配置作为依赖
@Configuration
public class BeanInitializationDemo {

    @Autowired
    private String value;

    @Value("${user.id:-1}") // 和  @Autowired 一样， 同时在 AutowiredAnnotationBeanPostProcessor 中注入
    private Long id;
    @Value("${user.resource:classpath://default.properties}")
    private String resource;

    @PostConstruct
    public void init() {
        System.out.println(value);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        // 只能用于依赖注入 不能用于依赖查找
        // 只能用来类型注入 不能用于名称注入
        applicationContext.addBeanFactoryPostProcessor(bf -> bf.registerResolvableDependency(String.class, "Hello World!"));
        applicationContext.refresh();

        BeanInitializationDemo beanInitializationDemo = applicationContext.getBean(BeanInitializationDemo.class);
        System.out.println("beanInitializationDemo.id=" + beanInitializationDemo.id);
        System.out.println("beanInitializationDemo.resource=" + beanInitializationDemo.resource);

        System.out.println("上下文启动");
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);    // 延时初始化是由依赖查找触发其初始化
        System.out.println(userFactory);

        // 创建一个外部 UserFactory 对象
        DefaultUserFactory userFactory1 = new DefaultUserFactory();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("userFactory1", userFactory1);
        // 依赖查找
        UserFactory userFactory1ByLookup = beanFactory.getBean("userFactory1", UserFactory.class);
        System.out.println("userFactory1 == userFactory1ByLookup: " + (userFactory1 == userFactory1ByLookup));


        applicationContext.close();
    }

    @Bean(initMethod = "init1", destroyMethod = "doDestroy")
    @Lazy(value = false)   // false在Spring应用上线文启动完成前被初始化；true在后初始化
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }


}
