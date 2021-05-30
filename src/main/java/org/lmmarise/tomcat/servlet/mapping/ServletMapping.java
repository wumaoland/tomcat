package org.lmmarise.tomcat.servlet.mapping;

/**
 * 封装一个Servlet的基本信息
 */
public class ServletMapping {
    private final String url;
    private final String servletName;
    private final String clazz;

    public ServletMapping(String servletName, String url, String clazz) {
        this.servletName = servletName;
        this.url = url;
        this.clazz = clazz;
    }

    public String getUrl() {
        return url;
    }

    public String getServletName() {
        return servletName;
    }

    public String getClazz() {
        return clazz;
    }

}
