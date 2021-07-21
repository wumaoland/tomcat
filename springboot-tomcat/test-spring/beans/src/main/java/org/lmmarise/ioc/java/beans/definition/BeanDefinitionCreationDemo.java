package org.lmmarise.ioc.java.beans.definition;

import org.lmmarise.ioc.lookup.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/7/20 10:39 下午
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.通过 BeanDefinition 来构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 对bean进行属性设置
        beanDefinitionBuilder.addPropertyValue("name", "坤坤");
        beanDefinitionBuilder.addPropertyValue("id", 12);
        // 获取BeanDefinition实例
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // beanDefinition并非bean的最终态，可以修改

        // 2.通过 AbstractBeanDefinition 以及派生类 设置 Bean 的类型
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        // 批量属性操作
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("name", "i坤坤").add("id", 13);
        genericBeanDefinition.setPropertyValues(propertyValues);
    }

    static Comparator<Object> hashComparator = Comparator.comparingInt(Object::hashCode);

}
