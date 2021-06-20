package org.lmmarise.spring.test.circular;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 9:06 下午
 */
@Component
public class CircularDependencyA implements ApplicationContextAware, InitializingBean {

    private CircularDependencyB circB;

    // A比B先创建，A不能在构造中获取比他后创建的也在构造或setter中依赖它的Bean
//    @Autowired
//    public CircularDependencyA(CircularDependencyB circB) {
//        this.circB = circB;
//    }

    // 循环依赖解决方法三：@PostConstruct
    @PostConstruct
    public void init() {
        circB.setCircA(this);
    }

    // 解决循环依赖方法二：使用setter注入
    @Autowired
    public void setCircB(CircularDependencyB circB) {
        this.circB = circB;
    }

    public CircularDependencyB getCircB() {
        return circB;
    }

    // 循环依赖解决方法四：使用InitializingBean注入
    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        circB = context.getBean(CircularDependencyB.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}

