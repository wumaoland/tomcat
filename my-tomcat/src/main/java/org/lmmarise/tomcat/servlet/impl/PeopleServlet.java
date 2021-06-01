package org.lmmarise.tomcat.servlet.impl;

import org.lmmarise.tomcat.request.Request;
import org.lmmarise.tomcat.response.Response;
import org.lmmarise.tomcat.servlet.Servlet;

import java.io.IOException;

public class PeopleServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            response.write("get people");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            response.write("post people");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
