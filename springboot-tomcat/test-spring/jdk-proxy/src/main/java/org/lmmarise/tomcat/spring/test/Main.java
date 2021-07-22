package org.lmmarise.tomcat.spring.test;

import org.lmmarise.tomcat.spring.test.dao.IStudentDao;
import org.lmmarise.tomcat.spring.test.dao.IStudentDao1;
import org.lmmarise.tomcat.spring.test.dao.StudentDao;
import org.lmmarise.tomcat.spring.test.handler.MyInvocationHandler;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 8:02 下午
 */
public class Main {

    public static void main(String[] args) {
        // 创建目标对象 StudentDao
        IStudentDao stuDAO = new StudentDao();
        MyInvocationHandler handler = new MyInvocationHandler(stuDAO);
        MyInvocationHandler handler1 = new MyInvocationHandler(stuDAO);
        // 使用 Proxy.newProxyInstance 动态的创建代理对象 stuProxy
        // 动态代理会在磁盘生成class文件，然后加载进jvm变成class对象，类加载完成之后再删除磁盘class文件
        // 原因：实现InvocationHandler接口是内部匿名类，JVM会为每个匿名类创建一个 Proxy$1….class文件
        // 参数1：能加载到IStudentDao的接口
        // 参数2：IStudentDao实现的接口
        // 参数3：组合了IStudentDao的实例，且实现了InvocationHandler接口。
        // 作用：最终对IStudentDao的实例的方法的调用（被参数二包含的方法），都会被传给参数3的实现去进行调用，最终实现JDK动态代理
        IStudentDao1 studentProxy = (IStudentDao1)
                Proxy.newProxyInstance(Main.class.getClassLoader(), stuDAO.getClass().getInterfaces(), handler);
        IStudentDao1 studentProxy2 = (IStudentDao1)
                Proxy.newProxyInstance(Main.class.getClassLoader(), stuDAO.getClass().getInterfaces(), handler1);

        // 将动态代理生成的对象保存到磁盘
        saveClassFile(studentProxy.getClass(), "studentProxy");

        // 调用动态代理生成的对象
        ((IStudentDao) studentProxy).save();    // stuDAO.getClass().getInterfaces() 找到了所有的接口，并为其生成了代理实现
        studentProxy.save1();

        System.out.println(studentProxy == studentProxy2);
        studentProxy2.save1();
    }


    /**
     * 保存class文件
     *
     * @param clazz     class对象
     * @param proxyName 代理类的名字，可随意指定
     */
    public static void saveClassFile(Class<?> clazz, String proxyName) {
        // 生成class的字节数组，此处生成的class与proxy.newProxyInstance中生成的class除了代理类的名字不同，其它内容完全一致
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String path = System.getProperty("user.dir") + "/springboot-tomcat/test-spring/jdk-proxy/src/main/动态代理生成的class文件/";
        File file = new File(path);
        if (!file.exists()) {
           file.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(path + proxyName + ".class")) {
            fos.write(classFile);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
