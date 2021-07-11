package org.lmmarise.tomcat.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个Web App中的URL与Servlet的一个映射关系配置
 */
public class ServletMappingConfig {
    private final Map<String, String> servletMaps = new HashMap<>();

    public void addServlet(String servletUrl, String servletClass){
        servletMaps.put(servletUrl, servletClass);
    }

    public String getServlet(String servletUrl) {
        return servletMaps.get(servletUrl);
    }

}
