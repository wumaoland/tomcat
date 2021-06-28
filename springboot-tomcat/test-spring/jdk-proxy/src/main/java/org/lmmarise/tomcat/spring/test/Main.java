package org.lmmarise.tomcat.spring.test;

import org.lmmarise.tomcat.spring.test.dao.IStudentDao;
import org.lmmarise.tomcat.spring.test.dao.IStudentDao1;
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
        IStudentDao1 studentProxy = (IStudentDao1)
                Proxy.newProxyInstance(Main.class.getClassLoader(), stuDAO.getClass().getInterfaces(), handler);
        ((IStudentDao) studentProxy).save();    // stuDAO.getClass().getInterfaces() 找到了所有的接口，并为其生成了代理实现
        studentProxy.save1();
    }

}
