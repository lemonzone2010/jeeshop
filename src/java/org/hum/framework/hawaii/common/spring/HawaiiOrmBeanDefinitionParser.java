package org.hum.framework.hawaii.common.spring;

import java.util.List;

import org.hum.framework.hawaii.orm.core.mapperholder.AbstractMapperHolder;
import org.hum.framework.hawaii.orm.core.parser.ClassParser;
import org.hum.framework.hawaii.orm.exception.HawaiiMapperException;
import org.hum.framework.hawaii.util.ReflectUtils;
import org.hum.framework.hawaii.util.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.zhiyu.framework.spring.AnnotationClassParser;
import com.zhiyu.framework.spring.SpringIocMapperHolder;

public class HawaiiOrmBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext context) {
		AbstractMapperHolder mapperFactoryInstance = null;
		ClassParser beanParser = null;
		
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		String className = element.getAttribute("class");
		
		try {
			if (StringUtils.isNotBlank(className)) {
				mapperFactoryInstance = ReflectUtils.forName(className, AbstractMapperHolder.class);
			} else {
				mapperFactoryInstance = new SpringIocMapperHolder();
			}
		} catch (ClassNotFoundException e) {
			throw new HawaiiMapperException("init AbstractMapperFactory exception: class \"" + className + "\" not found", e);
		} catch (InstantiationException e) {
			throw new HawaiiMapperException("init AbstractMapperFactory exception: class \"" + className + "\" instaniation exception", e);
		} catch (IllegalAccessException e) {
			throw new HawaiiMapperException("init AbstractMapperFactory exception: class \"" + className + "\" illegal access exception", e);
		}

		// load "parser" from element
		List<Element> childElements = DomUtils.getChildElements(element);
		for (Element childElement : childElements) {
			try {
				if ("parser".equals(childElement.getLocalName())) {
					beanParser = ReflectUtils.forName(childElement.getAttribute("class"), ClassParser.class);
				}
			} catch (ClassNotFoundException e) {
				throw new HawaiiMapperException("init AbstractMapperFactory exception: class \"" + childElement.getAttribute("class") + "\" not found");
			} catch (InstantiationException e) {
				throw new HawaiiMapperException("init AbstractMapperFactory exception: class \"" + childElement.getAttribute("class") + "\" instaniation exception");
			} catch (IllegalAccessException e) {
				throw new HawaiiMapperException("init AbstractMapperFactory exception: class \"" + childElement.getAttribute("class") + "\" illegal access exception");
			}
		}

		// if no "parser" , determine to default
		if (beanParser == null) {
			beanParser = new AnnotationClassParser();
		}

		beanDefinition.setBeanClass(mapperFactoryInstance.getClass());
		beanDefinition.getPropertyValues().addPropertyValue("beanParser", beanParser);
		context.getRegistry().registerBeanDefinition(element.getAttribute("id"), beanDefinition);
		return beanDefinition;
	}
}