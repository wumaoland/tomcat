package org.lmmarise.tomcat.loader;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/8 2:04 下午
 *
 * 封装加载类的信息
 */
public class LoadInfo {
    private MyCLassLoader cLassLoader;  // 自定义的类加载器
    private long loadTime;              // 记录类加载时间
    private BaseManager manager;

    public LoadInfo(MyCLassLoader cLassLoader, long loadTime) {
        super();
        this.cLassLoader = cLassLoader;
        this.loadTime = loadTime;
    }

    public void setCLassLoader(MyCLassLoader cLassLoader) {
        this.cLassLoader = cLassLoader;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public void setManager(BaseManager manager) {
        this.manager = manager;
    }

    public MyCLassLoader getCLassLoader() {
        return cLassLoader;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public BaseManager getManager() {
        return manager;
    }
}
