package org.lmmarise.ioc.java.beans.factory;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 2:00 下午
 */
public interface UserFactory extends DisposableBean, InitializingBean {

    default User createUser() {
        return User.createUser();
    }

    void init1();

    void doDestroy();

}
