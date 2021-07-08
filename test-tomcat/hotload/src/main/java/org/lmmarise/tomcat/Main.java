package org.lmmarise.tomcat;

import org.lmmarise.tomcat.handler.MsgHandler;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/8 11:39 上午
 */
public class Main {

    public static void main(String[] args) {
        new Thread(new MsgHandler()).start();
    }

}
