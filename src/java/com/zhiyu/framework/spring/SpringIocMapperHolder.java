package com.zhiyu.framework.spring;

import org.hum.framework.hawaii.orm.annotation.Table;
import org.hum.framework.hawaii.orm.aware.TableMapperHolderAware;
import org.hum.framework.hawaii.orm.core.mapperholder.DefaultMapperHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * spring插件，用于将bean解析成tableDefinition
 */
public class SpringIocMapperHolder extends DefaultMapperHolder implements BeanPostProcessor {

	public SpringIocMapperHolder() {
		beanParser = new AnnotationClassParser();
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		Table annotation = clazz.getAnnotation(Table.class);
		if (annotation != null) {
			addTableDefinition(beanParser.parse2TableDefinition(clazz));
			System.out.println(beanName + " has parse to table!");
		}
		// 注入TableMapperHolderAware
		for (Class<?> interFace : bean.getClass().getInterfaces()) {
			if (interFace.isAssignableFrom(TableMapperHolderAware.class)) {
				TableMapperHolderAware aware = (TableMapperHolderAware)bean;
				aware.setTableMapperHolder(this);
			}
		}
		return bean;
	}
}
