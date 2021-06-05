package org.lmmarise.tomcat.test.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/5 9:07 下午
 */
public class Nio2Server {

    void listen() throws IOException {
        //1. 创建一个线程池，这个线程池用来执行来自内核的回调请求
        ExecutorService es = Executors.newCachedThreadPool();
        //2. 创建异步通道群组，并绑定一个线程池
        AsynchronousChannelGroup tg = AsynchronousChannelGroup.withCachedThreadPool(es, 1);
        //3. 创建服务端异步通道，并绑定到 AsynchronousChannelGroup
        AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open(tg);
        //4. 绑定监听端口
        assc.bind(new InetSocketAddress(8080));
        //5. 监听连接，传入回调类处理连接请求
        assc.accept(this, new AcceptHandler());   // Nio2Server对象本身
    }

}
