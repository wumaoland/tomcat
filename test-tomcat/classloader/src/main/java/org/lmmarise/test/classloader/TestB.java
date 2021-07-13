package org.lmmarise.test.classloader;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/13 3:23 下午
 */
public class TestB {

    public void hello() {
        System.out.println("TestB: " + this.getClass().getClassLoader());
    }

}
