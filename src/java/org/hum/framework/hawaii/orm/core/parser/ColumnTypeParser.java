package org.hum.framework.hawaii.orm.core.parser;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.orm.enumtype.ColumnType;

/**
 * @author bjhuming
 * 这个类需要与Spring容器整合，因为很多地方都依赖于此
 */
public interface ColumnTypeParser {

	ColumnType parseColumnType(Field field);

	int parseColumnLength(Field field);
}
