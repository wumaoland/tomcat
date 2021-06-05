package org.lmmarise.tomcat.test.nio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/5 9:09 下午
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Nio2Server> {

    /**
     * 具体处理连接请求的就是 completed 方法
     *
     * @param asc        异步通道
     * @param attachment 上面传入的 NioServer 对象
     */
    @Override
    public void completed(AsynchronousSocketChannel asc, Nio2Server attachment) {
        // 调用 accept 方法继续接收其他客户端的请求
//        attachment.asc.accept(attachment, this);
        //1. 先分配好 Buffer，告诉内核，数据拷贝到哪里去
//        ByteBuffer buf = ByteBuffer.allocate(1024);
        //2. 调用 read 函数读取数据，除了把 buf 作为参数传入，还传入读回调类
//        channel.read(buf, buf, new ReadHandler(asc));
    }

    @Override
    public void failed(Throwable exc, Nio2Server attachment) {
    }

}
