package org.lmmarise.tomcat.response;

import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private final OutputStream osm;

    /**
     * Socket的输出流封装到Response
     */
    public Response(OutputStream osm) {
        this.osm = osm;
    }

    /**
     * 将内容写入到HTTP的响应体中
     */
    public void write(String content) throws IOException {
        String rn = "\r\n";
        String msg = "HTTP/1.1 200 " + rn +
                "Content-Type:text/html" + rn +
                rn +
                "<html><body>" +
                content +
                "</body></html>";
        osm.write(msg.getBytes());
    }

    public void flush() throws IOException {
        osm.flush();
    }

    /**
     * 关闭面向客户端的Socket的输出流
     */
    public void stop() throws IOException {
        osm.flush();
        osm.close();
    }

}
