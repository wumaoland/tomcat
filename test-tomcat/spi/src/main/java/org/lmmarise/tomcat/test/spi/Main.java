package org.lmmarise.tomcat.test.spi;

import org.lmmarise.tomcat.test.spi.provider.Service;
//import sun.misc.Launcher;

import java.util.ServiceLoader;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/5 5:48 下午
 */
public class Main {

    public static void main(String[] args) {
        printSystemProperty();
        System.out.println("\r\n========================我是分割线========================\r\n");
        serviceSPI();
    }

    /**
     * 通过SPI获取接口{@link Service}在classpath下所有的实现类<p/>
     * <ul>
     * <li>难点：ServiceLoader处于rt.jar包中，rt.jar由BootstrapClassLoader加载的，用加载ServiceLoader的类加载器无法加载classpath。<br/>
     *      实现了接口提供者的接口的类，一般是第三方类，是在 classpath 下的，BootStrapClassLoader 不能加载到 classpath 下的类。<br/>
     * <li>原理：最终currentThreadContextClassLoader被传到了{@link ServiceLoader.LazyIterator#nextService()}方法中。<br/>
     * <li>用法：<code>c = Class.forName(cn, false, loader);</code>传入loader，反射使用AppClassLoader去加载classpath下的类。<br/>
     * </ul>
     */
    public static void serviceSPI() {
        // Java核心API--ServiceLoader(rt.jar里面提供)用来扫描服务实现类，服务实现者提供的jar
        // ServiceLoader通过ContextClassLoader破坏Java类加载委托机制，加载classpath下Service的实现类

        // 如果放开下行代码，ServiceLoader使用的类加载器将从AppClassLoader切换到ExtClassLoader，从从classpath目录加载变成从加载ext目录加载
        // setCurrentThreadClassLoaderAsExtClassLoader();  // 通过改变ThreadLocal中的类加载器，以改变接下来的ServiceLoader使用的类加载器

        ServiceLoader<Service> services = ServiceLoader.load(Service.class);    // 不仅加载类，还调用类的无参构造创建示例
//        Class.forName()   使用调用者的类加载器，来加载指定类。--JVM的类加载传导规则
        for (Service service : services) {
            service.doSomething();
        }
    }

    /**
     * {@link Main}类位于classpath下，所有默认的currentThreadClassLoader是AppClassloader，这里将线程上下文中的类加载器设置为
     * {@link Launcher.ExtClassLoader}，就将加载目录指向了ext，如果用来加载classpath下的类将失败。
     * <p/>
     * 原理参考：{@link ThreadLocal}
     */
    public static void setCurrentThreadClassLoaderAsExtClassLoader() {
        Thread.currentThread().setContextClassLoader(Main.class.getClassLoader().getParent());
    }

    /**
     * 打印3个JVM提供的类加载器将会加载的资源列表
     */
    public static void printSystemProperty() {
        String path1 = System.getProperty("sun.boot.class.path");
        System.out.println("BootStrapClassLoader 加载的资源:" + path1);

        String path2 = System.getProperty("java.ext.dirs");
        System.out.println("ExtClassLoader 加载的资源：" + path2);

        String path3 = System.getProperty("java.class.path");
        System.out.println("AppClassLoader 加载的资源:" + path3);
    }

}
