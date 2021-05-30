package org.lmmarise.tomcat.servlet;

import org.lmmarise.tomcat.request.RequestType;
import org.lmmarise.tomcat.request.Request;
import org.lmmarise.tomcat.response.Response;

public abstract class Servlet {
    public abstract void doGet(Request request, Response response);
    public abstract void doPost(Request request, Response response);

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
