package org.lmmarise.tomcat.test.spi.impl;

import org.lmmarise.tomcat.test.spi.provider.Service;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/5 5:50 下午
 */
public class ServiceB implements Service {

    @Override
    public void doSomething() {
        System.out.println("Hello,MyService B!");
    }

}
