package org.lmmarise.tomcat.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/8 11:40 上午
 */
public class MyCLassLoader extends ClassLoader {

    private String classPath;

    public MyCLassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }


    private byte[] loadClassData(String name) {
        name = name.replaceAll("\\.", "/");
        try (
                FileInputStream fis = new FileInputStream(classPath + name + ".class");
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            int b = 0;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
