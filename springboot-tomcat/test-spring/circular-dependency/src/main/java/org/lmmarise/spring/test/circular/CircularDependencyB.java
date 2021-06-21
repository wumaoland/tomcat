package org.lmmarise.spring.test.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/20 9:06 下午
 */
@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    @Autowired
    // 解决循环依赖方法一：加lazy
//    public CircularDependencyB(@Lazy CircularDependencyA circA) {
    public CircularDependencyB(CircularDependencyA circA) {
        this.circA = circA;
    }

    @Autowired
    public void setCircA(CircularDependencyA circA) {
        this.circA = circA;
    }

    public CircularDependencyA getCircB() {
        return circA;
    }

}