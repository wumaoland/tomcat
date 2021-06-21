package org.lmmarise.spring.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tangshengbo@honezhi.com
 * @date 2021/6/21
 * @description
 */
@SuppressWarnings("all")
public class MultiClassLoaderDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader_1 classLoader_1 = new MyClassLoader_1(MultiClassLoaderDemo.class.getClassLoader());
        MyClassLoader_2 classLoader_2 = new MyClassLoader_2(classLoader_1);
        Class<?> classB = classLoader_2.loadClass("segmentfault.Question1010000017720845.ClassB");
        Object classBInstance = classB.newInstance();
        Method method = classB.getMethod("method");
        method.invoke(classBInstance);
    }

}

@SuppressWarnings("all")
class AbstractClassLoader extends ClassLoader {

    public AbstractClassLoader(ClassLoader parent) {
        super(parent);
    }

    protected byte[] loadClassData(String path, String name) {
        byte[] data = null;
        name = name.replace('.', '/');
        try (InputStream in = new FileInputStream(path + name + ".class1");
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int len;
            while (-1 != (len = in.read())) {
                out.write(len);
            }
            data = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public String toString() {
        return "AbstractClassLoader{}";
    }

}

/**
 * ClassLoaderDemo.java
 * 注意：ClassA,ClassB,ClassC三者不能出现在classpath下
 */
@SuppressWarnings("all")
class MyClassLoader_1 extends AbstractClassLoader {

    private final String path = "D:\\IdeaProjects\\lmmarise-tomcat\\springboot-tomcat\\test-spring\\run\\01temp\\";

    public MyClassLoader_1(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] classBytes = loadClassData(path, name);
        Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
        return clazz;
    }

    @Override
    public String toString() {
        return "MyClassLoader_1{}";
    }

}

/**
 * 加载ClassB和ClassC
 */
@SuppressWarnings("all")
class MyClassLoader_2 extends AbstractClassLoader {

    private final String path = "D:\\IdeaProjects\\lmmarise-tomcat\\springboot-tomcat\\test-spring\\run\\01temp\\";

    public MyClassLoader_2(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] classBytes = loadClassData(path, name);
        Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
        return clazz;
    }

    @Override
    public String toString() {
        return "MyClassLoader_2{}";
    }

}
