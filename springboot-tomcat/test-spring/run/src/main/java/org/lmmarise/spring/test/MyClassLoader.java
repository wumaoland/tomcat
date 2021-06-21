package org.lmmarise.spring.test;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
    // 默认加载路径
    private String path;
    // 类加载器名称
    private final String name;

    /**
     * @param name 用于设置类加载别名
     * @param path 用于设置类加载路径
     */
    public MyClassLoader(String name, String path) {
        super();
        this.name = name;
        this.path = path;
    }

    public MyClassLoader(ClassLoader parent, String name, String path) {
        super(parent);
        this.name = name;
        this.path = path;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {
        byte[] data = null;
        name = name.replace('.', '/');
        try (InputStream in = new FileInputStream(path + name + ".class");
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MyClassLoader{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}