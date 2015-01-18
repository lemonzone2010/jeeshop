package org.hum.framework.hawaii.orm.util;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;

public class NameUtils {

	public static String getTableDefinitionkeyName(Object object) {
		if (object instanceof TableDefinition) {
			TableDefinition tableDefinition = (TableDefinition) object;
			return tableDefinition.getClassType().getName();
		}
		if (object instanceof Class<?>) {
			Class<?> classType = (Class<?>) object;
			return classType.getName();
		}

		return object.getClass().getName();
	}

	public static String getColumnDefinitionkeyName(Object object) {
		if (object instanceof ColumnDefinition) {
			ColumnDefinition columnDefinition = (ColumnDefinition) object;
			return columnDefinition.getField().getName() + columnDefinition.getField().hashCode();
		} else if (object instanceof Field) {
			Field field = (Field) object;
			return field.getName() + field.hashCode();
		}
		throw new IllegalArgumentException("argument " + object + " can't parse");
	}
}
