package org.hum.framework.hawaii.common.spring;

import org.hum.framework.hawaii.dao.core.AbstractDao;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class HawaiiDaoBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext context) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(AbstractDao.class);

		context.getRegistry().registerBeanDefinition(element.getAttribute("id"), beanDefinition);
		return beanDefinition;
	}
}
