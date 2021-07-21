package org.lmmarise.ioc.java.beans.factory;

import org.lmmarise.ioc.lookup.domain.User;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/21 2:00 下午
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }

    void init1();

}
