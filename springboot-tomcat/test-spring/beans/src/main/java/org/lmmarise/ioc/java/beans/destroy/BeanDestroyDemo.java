package org.lmmarise.ioc.java.beans.destroy;

import org.lmmarise.ioc.java.beans.initial.BeanInitializationDemo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试Bean销毁生命周期
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 4:32 下午
 */
public class BeanDestroyDemo {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanDestroyDemo.class);
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        applicationContext.close();
        Thread.sleep(5000L);
        System.gc();    // 建议触发GC
        Thread.sleep(5000L);
    }
}
