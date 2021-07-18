package org.lmmarise.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * {@link BeanInfo} 使用Java自带的Bean工具反射获取对象信息
 * @author lmmarise.j@gmail.com
 * @date 2021/7/18 3:43 下午
 */
public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {
        // 把父类加到stopClass，避免Object.getClass导致误以为有个class属性
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    // System.out.println(propertyDescriptor);
                    // propertyDescriptor 允许添加属性编辑器
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    System.out.println(propertyType);
                    String propertyName = propertyDescriptor.getName();
                    if ("age".equals(propertyName)) {   // 为age字段增加propertyEditor
                        propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditorClass.class);
                        // propertyDescriptor.createPropertyEditor();
                    }
                });
    }

    static class StringToIntegerPropertyEditorClass extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer integer = Integer.valueOf(text);
            setValue(integer);
        }
    }

}
