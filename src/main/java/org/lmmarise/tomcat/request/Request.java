package org.lmmarise.tomcat.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private final static Logger log = LoggerFactory.getLogger(Request.class);

    private String url;
    private RequestType method;

    /**
     * 对客户的Socket请求以HTTP方式进行解析与封装
     */
    public Request(InputStream ism) throws IOException {
        byte[] content = new byte[64];
        int readLen = ism.read(content);
        if (readLen > 0) {
            String s = new String(content, 0, readLen);
            String[] ss = s.split("\\s");
            url = ss[1];
            // 解析socket输入流从的HTTP报文，将请求方法封装到对象
            switch (ss[0]) {
                case "GET": {
                    method = RequestType.GET;
                    break;
                }
                case "POST": {
                    method = RequestType.POST;
                    break;
                }
            }
            log.info(this.toString());
        }
    }

    public String getUrl() {
        return url;
    }

    public RequestType getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method=" + method +
                '}';
    }

}
