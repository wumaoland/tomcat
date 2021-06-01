package org.lmmarise.tomcat.servlet;

import org.lmmarise.tomcat.request.RequestType;
import org.lmmarise.tomcat.request.Request;
import org.lmmarise.tomcat.response.Response;

/**
 * Servlet本质是一个接口，Servlet接口是业务类与Servlet容器之间的接口；
 * Servlet和Servlet容器的一整套规范叫Servlet规范，该规范使得程序员只需要关注业务；
 * Servlet 规范还提供了两种扩展机制：Filter和Listener。
 */
public abstract class Servlet {
    public abstract void doGet(Request request, Response response);
    public abstract void doPost(Request request, Response response);

    /**
     * 具体的业务逻辑在这里完成
     */
    public void service(Request request, Response response) {
        RequestType method = request.getMethod();
        switch (method) {
            case GET: {
                doGet(request, response);
                break;
            }
            case POST: {
                doPost(request, response);
                break;
            }
        }
    }

}

