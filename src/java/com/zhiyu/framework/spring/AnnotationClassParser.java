package com.zhiyu.framework.spring;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.orm.annotation.Column;
import org.hum.framework.hawaii.orm.annotation.Identity;
import org.hum.framework.hawaii.orm.annotation.PrimaryKey;
import org.hum.framework.hawaii.orm.annotation.Table;
import org.hum.framework.hawaii.orm.annotation.UnMapperToColumn;
import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;
import org.hum.framework.hawaii.orm.core.parser.AbstractClassParser;
import org.hum.framework.hawaii.util.StringUtils;

public class AnnotationClassParser extends AbstractClassParser {

	@Override
	public TableDefinition reBuildTableDefinition(TableDefinition tableDefinition, Class<?> clazz) {
		Table tableAnno = clazz.getAnnotation(Table.class);
		if (StringUtils.isNotBlank(tableAnno.dbName())) {
			tableDefinition.setDbName(tableAnno.dbName());
		}
		if (StringUtils.isNotBlank(tableAnno.tableName())) {
			tableDefinition.setTableName(tableAnno.tableName());
		}

		return tableDefinition;
	}

	@Override
	public ColumnDefinition reBuildColumnDefinition(ColumnDefinition columnDefinition, Field field) {
		if (field.getAnnotation(UnMapperToColumn.class) != null) {
			return null;
		}

		if (field.getAnnotation(PrimaryKey.class) != null) {
			columnDefinition.setIsPrimaryKey(Boolean.TRUE);
		}
		if (field.getAnnotation(Identity.class) != null && field.getAnnotation(Identity.class).value() == true) {
			columnDefinition.setIsIdentity(Boolean.TRUE);
		}

		Column columnAnno = field.getAnnotation(Column.class);
		if (columnAnno != null) {
			if (StringUtils.isNotBlank(columnAnno.colName())) {
				columnDefinition.setColName(columnAnno.colName());
			}
			if (columnAnno.colType() != null) {
				columnDefinition.setColumnType(columnAnno.colType());
			}
			if (columnAnno.length() > 0) {
				columnDefinition.setLength(columnAnno.length());
			}
		}

		return columnDefinition;
	}
}
