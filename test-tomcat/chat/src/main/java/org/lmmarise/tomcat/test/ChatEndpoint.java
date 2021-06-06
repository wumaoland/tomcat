package org.lmmarise.tomcat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java WebSocket 应用程序由一系列的 WebSocket Endpoint 组成。
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/6/6 3:49 下午
 */
@ServerEndpoint(value = "/websocket/chat")
public class ChatEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ChatEndpoint.class);

    private static final String GUEST_PREFIX = "Guest";
    // 记录当前有多少个用户加入到了聊天室，它是 static 全局变量。为了多线程安全使用原子变量 AtomicInteger
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    // 每个用户用一个 CharAnnotation 实例来维护，请你注意它是一个全局的 static 变量，所以用到了线程安全的 CopyOnWriteArraySet
    // 当某个 ChatEndpoint 实例收到来自浏览器的消息时，ChatEndpoint 向集合中其他 ChatEndpoint 实例背后的 WebSocket 连接推送消息。
    private static final Set<ChatEndpoint> connections = new CopyOnWriteArraySet<>();
    private final String nickname;
    // 当浏览器连接到一个 Endpoint 时，Tomcat 会给这个连接创建一个唯一的 Session（javax.websocket.Session）。
    private Session session;    // 本质是对 Socket 的封装，Endpoint 通过它与浏览器通信

    public ChatEndpoint() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }

    // 新连接到达时，Tomcat 会创建一个 Session，并回调这个函数
    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.add(this);
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(message);
    }

    // 浏览器关闭连接时，Tomcat 会回调这个函数
    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("* %s %s", nickname, "has disconnected.");
        broadcast(message);
    }

    // 浏览器发送消息到服务器时，Tomcat 会回调这个函数
    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        String filteredMessage = String.format("%s: %s", nickname, message);
        broadcast(filteredMessage);
    }

    //Websocket 连接出错时，Tomcat 会回调这个函数
    @OnError
    public void onError(Throwable t) {
        log.error("Chat Error: " + t.toString(), t);
    }

    // 向聊天室中的每个用户广播消息
    private static void broadcast(String msg) {
        for (ChatEndpoint client : connections) {
            synchronized (client) {
                try {
                    client.session.getBasicRemote().sendText(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
