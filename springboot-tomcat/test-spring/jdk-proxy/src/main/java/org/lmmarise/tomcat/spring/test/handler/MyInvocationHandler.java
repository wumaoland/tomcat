package org.lmmarise.tomcat.spring.test.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 8:02 下午
 * 定义一个 InvocationHandler 类，将需要扩展的逻辑集中放到这个类中
 */
public class MyInvocationHandler implements InvocationHandler {

    private int count = 0;
    private final Object obj;

    public MyInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始事物" + ++count);
        Object result = method.invoke(obj, args);
        System.out.println("结束事物" + ++count);

        return result;
    }

    @Override
    public String toString() {
        return "MyInvocationHandler{" +
                "count=" + count +
                ", obj=" + obj +
                '}';
    }
}
