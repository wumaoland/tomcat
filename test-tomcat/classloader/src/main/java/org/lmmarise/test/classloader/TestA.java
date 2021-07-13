package org.lmmarise.test.classloader;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/13 3:23 下午
 */
public class TestA {

    public static void main(String[] args) {
        TestA testA = new TestA();
        testA.hello();
        // output:
        // TestA: jdk.internal.loader.ClassLoaders$AppClassLoader@7ad041f3
        // TestB: jdk.internal.loader.ClassLoaders$AppClassLoader@7ad041f3
    }

    public void hello() {
        System.out.println("TestA: " + this.getClass().getClassLoader());
        TestB testB = new TestB();
        testB.hello();
    }
}
