package org.lmmarise.spring.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Map;

import static org.lmmarise.spring.scope.ThreadLocalScope.SCOPE_NAME;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/29 7:08 下午
 */
public class BeanScopeDemo implements DisposableBean {

    @Resource
    @Qualifier("user1")
    private User user1;
    @Resource
    @Qualifier("user1")
    private User user2;
    @Resource
    @Qualifier("user2")
    private User user3;
    @Resource
    @Qualifier("user2")
    private User user4;
    @Resource
    private Map<String, User> users;
    @Resource
    private ConfigurableListableBeanFactory beanFactory;

    @Bean(name = "user3")
    @Scope(SCOPE_NAME)
    public User user() {
        return new User("鸡你太美", 21);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemo.class);
        // 创建
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                System.out.printf("%s Bean 名称：%s 在初始化后回调。。。%n", bean.getClass().getName(), beanName);
                beanFactory.registerScope(SCOPE_NAME, new ThreadLocalScope());
                return bean;
            }
        }));
        applicationContext.refresh();
        // 得出结论 无论是依赖查找还是依赖注入 单例每次对象都一样  原型每次都生成新的对象
        scopedBeanByLookup(applicationContext);
        scopedBeanByInjection(applicationContext);
        applicationContext.close();
    }

    /**
     * 对Spring无法管理的原型Bean进行销毁
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("当前BeanScope Bean 正在销毁。。。");
        // 手动销毁原型Bean
//        this.user1.destroy();
//        this.user2.destroy();
//        this.user3.destroy();
//        this.user4.destroy();
        // 对上面进行改进
        for (Map.Entry<String, User> entry : this.users.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.isPrototype()) { // 当前Bean是原型作用域
                User user = entry.getValue();
                user.destroy();
            }
        }
    }

    static class User {
        private final String name;
        private final int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /**
         * 原型模式下每次创建都会初始化一次
         */
        @PostConstruct
        public void init() {
            System.out.println("用户对象初始化...");
        }

        /**
         * 使用中发现，只有单例模式创建的Bean执行了销毁
         */
        @PreDestroy
        public void destroy() {
            System.out.println("用户对象销毁...");
        }


        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", hashCode=" + hashCode() +
                    '}';
        }
    }

    @Bean
    @Scope(scopeName = "prototype")
    private static User user1() {
        return new User("zz", 12);
    }

    @Bean
    @Scope(scopeName = "singleton")
    private static User user2() {
        return new User("qq", 13);
    }

    /**
     * 测试依赖查找
     */
    private static void scopedBeanByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            User user1 = applicationContext.getBean("user1", User.class);
            System.out.println("user1==>" + user1);
            User user2 = applicationContext.getBean("user2", User.class);
            System.out.println("user2==>" + user2);
            User user3 = applicationContext.getBean("user3", User.class);
            System.out.println("user3==>" + user3);
        }
    }

    /**
     * 测试依赖注入
     */
    private static void scopedBeanByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println(beanScopeDemo.user1);
        System.out.println(beanScopeDemo.user2);
        System.out.println(beanScopeDemo.user3);
        System.out.println(beanScopeDemo.user4);
        System.out.println(beanScopeDemo.users);
    }

}
