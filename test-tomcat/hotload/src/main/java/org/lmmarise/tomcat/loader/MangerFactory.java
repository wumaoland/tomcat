package org.lmmarise.tomcat.loader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载Manager的工厂
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/8 2:10 下午
 */
public class MangerFactory {
    // 记录热加载类的信息
    private static final Map<String, LoadInfo> loadTimeMap = new HashMap<>();
    // 要加载的类的classpath
    public static final String CLASS_PATH = System.getProperty("user.dir") + "/test-tomcat/hotload/impl_module/target/";
    // 实现热加载类的全限类名
    public static final String MY_MANAGER = "org.lmmarise.tomcat.loader.MyManager";

    public static BaseManager getManager(String className) {
        File loadFile = new File(CLASS_PATH + className.replaceAll("\\.", "/") + ".class");
        long lastModified = loadFile.lastModified();
        // 类没被加载 或 类变化
        if (loadTimeMap.get(className) == null || (loadTimeMap.get(className).getLoadTime() != lastModified)) {
            load(className, lastModified);
        }
        return loadTimeMap.get(className).getManager();
    }

    private static void load(String className, long lastModified) {
        MyCLassLoader classLoader = new MyCLassLoader(CLASS_PATH);
        Class<?> loadClass = classLoader.findClass(className);
        BaseManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(classLoader, lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className, loadInfo);
    }

    private static BaseManager newInstance(Class<?> loadClass) {
        try {
            return (BaseManager) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
