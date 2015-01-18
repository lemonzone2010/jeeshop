package org.hum.framework.hawaii.orm.core.parser;

import org.hum.framework.hawaii.orm.bean.TableDefinition;

public interface ClassParser {
	
	TableDefinition parse2TableDefinition(Class<?> clazz);
}
