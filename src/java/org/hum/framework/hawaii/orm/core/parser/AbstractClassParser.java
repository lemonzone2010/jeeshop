package org.hum.framework.hawaii.orm.core.parser;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;
import org.hum.framework.hawaii.util.StringUtils;

public abstract class AbstractClassParser implements ClassParser {

	private ColumnTypeParser columnTypeParser = new DefaultColumnTypeParser();

	@Override
	public TableDefinition parse2TableDefinition(Class<?> clazz) {
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition = buildDefaultValueToTableDefinition(tableDefinition, clazz);
		// 交给子类，根据子类实现再次写入TableDefinition
		tableDefinition = reBuildTableDefinition(tableDefinition, clazz);
		return tableDefinition;
	}

	/*
	 * 根据Class<?>的定义，给tableDefinition赋予默认值
	 */
	private TableDefinition buildDefaultValueToTableDefinition(final TableDefinition tableDefinition, Class<?> clazz) {
		// 表名大小写转换(tableName -> table_name)
		tableDefinition.setTableName(StringUtils.upperCase2UnderLineAndLowCase(clazz.getSimpleName()));
		tableDefinition.setDbName("");
		tableDefinition.setClassType(clazz);
		
		// 解析表字段
		for (Field field : clazz.getDeclaredFields()) {
			ColumnDefinition columnDefinition = new ColumnDefinition();
			// 写入默认值
			buildDefaultValueToColumnDefinition(columnDefinition, field);
			// 交给子类处理
			columnDefinition = reBuildColumnDefinition(columnDefinition, field);

			if (columnDefinition != null) {
				// 这里思前想后，最后还是决定用field.getName好一些
				tableDefinition.getColumns().put(field.getName(), columnDefinition);
			}
		}

		return tableDefinition;
	}

	private boolean isNumberType(Class<?> clazz) {
		if (clazz != null) {
			if (clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(Long.class)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 根据Field的定义，给columnDefinition赋予默认值
	 */
	private ColumnDefinition buildDefaultValueToColumnDefinition(ColumnDefinition columnDefinition, Field field) {
		columnDefinition.setColName(StringUtils.upperCase2UnderLineAndLowCase(field.getName()));
		columnDefinition.setField(field);

		if ("id".equals(field.getName())) {
			columnDefinition.setIsPrimaryKey(Boolean.TRUE);
			if (isNumberType(field.getType())) {
				columnDefinition.setIsIdentity(Boolean.TRUE);
			}
		} else {
			columnDefinition.setIsIdentity(Boolean.FALSE);
			columnDefinition.setIsPrimaryKey(Boolean.FALSE);
		}

		columnDefinition.setColumnType(columnTypeParser.parseColumnType(field));
		columnDefinition.setLength(columnTypeParser.parseColumnLength(field));

		return columnDefinition;
	}

	public abstract TableDefinition reBuildTableDefinition(TableDefinition tableDefinition, Class<?> clazz);

	public abstract ColumnDefinition reBuildColumnDefinition(ColumnDefinition columnDefinition, Field field);

	public void setColumnTypeParser(ColumnTypeParser columnTypeParser) {
		this.columnTypeParser = columnTypeParser;
	}
}
