package org.lmmarise.tomcat.springboot.container;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/19 4:11 下午
 */
public class Main {

    /**
     * {@link WebServer} 对内嵌式 Web 容器进行了抽象。
     * {@link ServletWebServerFactory} 用于创建 Web 容器的工厂，返回的对象就是上面提到的 WebServer。
     * {@link WebServerFactoryCustomizerBeanPostProcessor} 支持对内嵌式 Web 容器的定制化。
     * 在 postProcessBeforeInitialization 过程中去寻找 Spring 容器中 {@link WebServerFactoryCustomizer} Bean，并依次调用
     *  其 {@link WebServerFactoryCustomizer#customize(WebServerFactory)} 方法做一些定制化。
     *
     *  ## 内嵌式web容器的创建
     *      {@link ServletWebServerApplicationContext#onRefresh()} 通过该方法创建内嵌式的 Web 容器。
     */
    public static void main(String[] args) {

    }

}
