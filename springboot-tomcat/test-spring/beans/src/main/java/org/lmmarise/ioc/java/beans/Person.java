package org.lmmarise.ioc.java.beans;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/18 3:30 下午
 */
public class Person {
    private String name;
    // Integer to String
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
