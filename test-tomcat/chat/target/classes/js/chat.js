/**
 * 当连接建立、关闭和有新消息时，浏览器会负责调用这些回调方法
 */
var Chat = {};
Chat.socket = null;
Chat.connect = (function(host) {

    // 判断当前浏览器是否支持 WebSocket
    if ('WebSocket' in window) {
        // 如果支持则创建 WebSocket JS 类
        Chat.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Chat.socket = new MozWebSocket(host);
    } else {
        Console.log('WebSocket is not supported by this browser.');
        return;
    }

    // 回调函数，当和服务器的 WebSocket 连接建立起来后，浏览器会回调这个方法
    Chat.socket.onopen = function () {
        Console.log('Info: WebSocket connection opened.');
        document.getElementById('chat').onkeydown = function(event) {
            if (event.keyCode == 13) {
                Chat.sendMessage();
            }
        };
    };

    // 回调函数，当和服务器的 WebSocket 连接关闭后，浏览器会回调这个方法
    Chat.socket.onclose = function () {
        document.getElementById('chat').onkeydown = null;
        Console.log('Info: WebSocket closed.');
    };

    // 回调函数，当服务器有新消息发送到浏览器，浏览器会回调这个方法
    Chat.socket.onmessage = function (message) {
        Console.log(message.data);
    };
});