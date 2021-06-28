package org.lmmarise.tomcat.spring.test.dao;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 8:05 下午
 */
public class StudentDao implements IStudentDao {
    @Override
    public void save() {
        System.out.println("StudentDao--save");
    }

    @Override
    public void save1() {
        System.out.println("StudentDao--save1");
    }
}
