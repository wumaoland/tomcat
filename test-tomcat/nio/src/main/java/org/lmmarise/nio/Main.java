package org.lmmarise.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/2 9:13 下午
 */
public class Main {

    public final synchronized strictfp static void main(String[] args) throws IOException {
        // 服务端channel
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8088));
        server.configureBlocking(false);
        // 注册channel感兴趣的连接事件
        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);  // 连接事件
        // 建立连接处理事件
        while (true) {
            selector.select();  // 查询IO事件
            Iterator<SelectionKey> i = selector.selectedKeys().iterator();
            while (i.hasNext()) {
                SelectionKey key = i.next();
                i.remove();

                if (key.isAcceptable()) {
                    SocketChannel client = server.accept();     // 建立一个新连接
                    client.configureBlocking(false);

                    client.register(selector, SelectionKey.OP_READ);    // 告诉Selector现在对IO可读感兴趣
                }
            }
        }
    }

}
