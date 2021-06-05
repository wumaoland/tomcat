package org.lmmarise.tomcat;

import org.lmmarise.tomcat.request.Request;
import org.lmmarise.tomcat.response.Response;
import org.lmmarise.tomcat.servlet.Servlet;
import org.lmmarise.tomcat.servlet.mapping.ServletMapping;
import org.lmmarise.tomcat.servlet.mapping.config.ServletMappingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的Tomcat Web服务器
 */
public class Tomcat {
    private static final Logger log = LoggerFactory.getLogger(Tomcat.class);

    private final int port;
    private ServletMappingConfig servletConfig;

    public Tomcat(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat(8080);
        tomcat.start();
    }



    /**
     * URL与Servlet的映射绑定
     */
    public void initUrlServlet() {
        servletConfig = new ServletMappingConfig();

        // 假设这里是通过注解@RequestMapping获取到的
        List<ServletMapping> servletMaps = new ArrayList<>();
        servletMaps.add(new ServletMapping("user", "/user", "org.lmmarise.tomcat.servlet.impl.UserServlet"));
        servletMaps.add(new ServletMapping("people", "/people", "org.lmmarise.tomcat.servlet.impl.PeopleServlet"));

        for (ServletMapping servlet : servletMaps) {
            // 将解析注解获取到的URL与Servlet配置到Tomcat，URL与Servlet映射
            servletConfig.addServlet(servlet.getUrl(), servlet.getClazz());
            log.info("http://127.0.0.1:{}{}", port, servlet.getUrl());
        }
    }

    /**
     * Tomcat服务器启动，监听Socket请求
     */
    public void start() {
        initUrlServlet();

        ServerSocket server;
        try {
            server = new ServerSocket(port);
            log.info("Server on port {} is running...", port);
            while (true) {
                Socket client = server.accept();
                // 会阻塞
                dispatch(new Request(client.getInputStream()), new Response(client.getOutputStream()));
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Socket请求分发给对应的Servlet的service方法来处理
     */
    public void dispatch(Request request, Response response) {
        String className = servletConfig.getServlet(request.getUrl());
        if (className != null) {
            try {
                Class<?> cls = Class.forName(className);
                Servlet servlet = (Servlet) cls.newInstance();
                servlet.service(request, response);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
