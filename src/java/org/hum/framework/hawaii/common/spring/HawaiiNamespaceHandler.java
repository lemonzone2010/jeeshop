package org.hum.framework.hawaii.common.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class HawaiiNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		 registerBeanDefinitionParser("orm", new HawaiiOrmBeanDefinitionParser());
		 registerBeanDefinitionParser("dao", new HawaiiDaoBeanDefinitionParser());
	}
}
