package org.lmmarise.spring.test;

/**
 * @author tangshengbo@honezhi.com
 * @date 2021/6/21
 * @description
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        String path = "D:\\IdeaProjects\\lmmarise-tomcat\\springboot-tomcat\\test-spring\\run\\target\\classes\\";

        MyClassLoader loader = new MyClassLoader(null, "ClassLoaderTest", path);
        Object obj = loader.loadClass("org.lmmarise.spring.test.ClassLoaderTest").newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);
    }

}
