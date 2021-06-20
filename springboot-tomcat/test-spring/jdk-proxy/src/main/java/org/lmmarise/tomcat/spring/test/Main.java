package org.lmmarise.tomcat.spring.test;

import org.lmmarise.tomcat.spring.test.dao.IStudentDao;
import org.lmmarise.tomcat.spring.test.dao.StudentDao;
import org.lmmarise.tomcat.spring.test.handler.MyInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 8:02 下午
 */
public class Main {

    public static void main(String[] args) {
        // 创建目标对象 StudentDao
        IStudentDao stuDAO = new StudentDao();
        MyInvocationHandler handler = new MyInvocationHandler(stuDAO);
        // 使用 Proxy.newProxyInstance 动态的创建代理对象 stuProxy
        IStudentDao studentProxy = (IStudentDao)
                Proxy.newProxyInstance(Main.class.getClassLoader(), stuDAO.getClass().getInterfaces(), handler);
        studentProxy.save();
    }

}
