package org.lmmarise.spring.scope;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Bean作用域
 *
 * @author lmmarise.j@gmail.com
 * @date 2021/7/30 3:08 下午
 */
public class ThreadLocalScope implements Scope {

    public static final String SCOPE_NAME = "thread-local";

    private final NamedThreadLocal<Map<String, Object>> threadLocal = new NamedThreadLocal<Map<String, Object>>("thread-local-scope") {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    @NotNull
    private Map<String, Object> getContext() {
        return threadLocal.get();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> context = getContext();
        Object object = context.get(name);
        if (object == null) {
             object = objectFactory.getObject();
            context.put(name, object);
        }

        return object;
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> context = getContext();
        return context.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        Thread thread = Thread.currentThread();
        return String.valueOf(thread.getId());
    }

}
