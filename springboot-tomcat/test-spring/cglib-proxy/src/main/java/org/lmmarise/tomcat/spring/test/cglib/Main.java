package org.lmmarise.tomcat.spring.test.cglib;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 8:38 下午
 */
public class Main {

    public static void main(String[] args) {
        MyCglibProxy proxy = new MyCglibProxy();
        SayHello sayHello = (SayHello) proxy.getProxy(SayHello.class);
       // sayHello.say();
        sayHello.say1();
    }

}
