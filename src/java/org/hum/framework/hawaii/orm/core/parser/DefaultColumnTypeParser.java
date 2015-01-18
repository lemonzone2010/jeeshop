package org.hum.framework.hawaii.orm.core.parser;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import org.hum.framework.hawaii.orm.constant.HawaiiMapperConstant;
import org.hum.framework.hawaii.orm.enumtype.ColumnType;

public class DefaultColumnTypeParser implements ColumnTypeParser {

	@Override
	public int parseColumnLength(Field field) {
		if (String.class.isAssignableFrom(field.getType())) {
			return HawaiiMapperConstant.DEFAULT_VARCHAR_LENGTH;
		}
		return 0;
	}

	@Override
	public ColumnType parseColumnType(Field field) {
		if (Integer.class.isAssignableFrom(field.getType())) {
			return ColumnType.Int;
		}
		if (Short.class.isAssignableFrom(field.getType())) {
			return ColumnType.SmallInt;
		}
		if (Long.class.isAssignableFrom(field.getType())) {
			return ColumnType.BigInt;
		}
		if (String.class.isAssignableFrom(field.getType())) {
			return ColumnType.Varchar;
		}
		if (Float.class.isAssignableFrom(field.getType())) {
			return ColumnType.Float;
		}
		if (Double.class.isAssignableFrom(field.getType())) {
			return ColumnType.Double;
		}
		if (Date.class.isAssignableFrom(field.getType())) {
			return ColumnType.DateTime;
		}
		if (BigDecimal.class.isAssignableFrom(field.getType())) {
			return ColumnType.Decimal;
		}

		return ColumnType.Customer;
	}
}
