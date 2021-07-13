package org.lmmarise.test.classloader;

import java.lang.reflect.Method;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/13 3:30 下午
 */
public class MyTest {

    public static void main1(String[] args) throws Exception {
        MyClassLoaderParentFirst myClassLoaderParentFirst = new MyClassLoaderParentFirst();
        Class<?> testAClass = myClassLoaderParentFirst.findClass("org.lmmarise.test.classloader.TestA");
        Method mainMethod = testAClass.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, new Object[]{args});

        // output:
        // TestA: org.lmmarise.test.classloader.MyClassLoaderParentFirst@4617c264
        // TestB: jdk.internal.loader.ClassLoaders$AppClassLoader@7ad041f3

        // TestA 确实是 MyClassLoaderParentFirst 加载的，但是 TestB 还是 AppClassLoader 加载的。
        // 分析：
        //  JVM 在触发类加载时调用的是 ClassLoader.loadClass 方法。
        //      1.委托给父类加载器加载。
        //      2.父类查询不到再调用 findClass 加载。
        // 结论：
        //  JVM 确实使用了MyClassLoaderParentFirst 来加载 TestB，但是因为双亲委派的机制，
        //  TestB 被委托给了 MyClassLoaderParentFirst 的父加载器 AppClassLoader 进行加载，并且父加载器从 classpath 下加载成功了。
        // 原因：
        //   MyClassLoaderParentFirst 构造被默认设定为 AppClassLoader。
    }

    public static void main(String[] args) throws Exception {
        // 这里取AppClassLoader的父加载器也就是ExtClassLoader作为MyClassLoaderCustom的jdkClassLoader
        MyClassLoaderCustom myClassLoaderCustom = new MyClassLoaderCustom(Thread.currentThread().getContextClassLoader().getParent());
        Class<?> testAClass = myClassLoaderCustom.loadClass("org.lmmarise.test.classloader.TestA");
        Method mainMethod = testAClass.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, new Object[]{args});
        // output:
        // TestA: org.lmmarise.test.classloader.MyClassLoaderCustom@4617c264
        // TestB: org.lmmarise.test.classloader.MyClassLoaderCustom@4617c264
    }

    // 综上：
    // loadClass 是用来判断什么情况下调用 findClass 以完成类加载的。
    // 1.不想打破双亲委派机制，重写findClass方法以实现自定义的类加载器。
    // 2.想重写双亲委派机制重写loadClass。


}
