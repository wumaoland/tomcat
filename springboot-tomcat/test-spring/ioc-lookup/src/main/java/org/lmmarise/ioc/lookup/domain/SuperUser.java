package org.lmmarise.ioc.lookup.domain;

import org.lmmarise.ioc.lookup.annotation.Super;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/18 6:14 下午
 */
@Super
public class SuperUser extends User {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
