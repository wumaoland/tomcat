package org.lmmarise.test.classloader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/13 3:26 下午
 */
public class MyClassLoaderParentFirst extends ClassLoader {

    private final Map<String, String> classPathMap = new HashMap<>();

    public MyClassLoaderParentFirst() {
        classPathMap.put(
                "org.lmmarise.test.classloader.TestA",
                System.getProperty("user.dir") + "/test-tomcat/classloader/target/classes/org/lmmarise/test/classloader/TestA.class"
        );
        classPathMap.put(
                "org.lmmarise.test.classloader.TestB",
                System.getProperty("user.dir") + "/test-tomcat/classloader/target/classes/org/lmmarise/test/classloader/TestB.class"
        );
    }

    // 重写了 findClass 方法
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = classPathMap.get(name);
        File file = new File(classPath);
        if (!file.exists()) {
            throw new ClassNotFoundException();
        }
        byte[] classBytes = getClassData(file);
        if (classBytes.length == 0) {
            throw new ClassNotFoundException();
        }
        return defineClass(classBytes, 0, classBytes.length);
    }

    private byte[] getClassData(File file) {
        try (InputStream ins = new FileInputStream(file); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

}
