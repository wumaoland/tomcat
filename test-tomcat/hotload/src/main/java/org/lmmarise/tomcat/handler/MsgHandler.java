package org.lmmarise.tomcat.handler;

import org.lmmarise.tomcat.loader.BaseManager;
import org.lmmarise.tomcat.loader.MangerFactory;

/**
 * 后台启动线程不断刷新重新加载实现了热加载的类
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/8 2:54 下午
 */
public class MsgHandler implements Runnable {

    @Override
    public void run() {
        while (true) {
            BaseManager manager = MangerFactory.getManager(MangerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
