package org.hum.framework.hawaii.orm.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableDefinition {

	private String dbName;
	private String tableName;
	private Class<?> classType;
	private Map<String, ColumnDefinition> columns = new LinkedHashMap<String, ColumnDefinition>();
	private ColumnDefinition pkColumn;

	public TableDefinition() {
	}

	public TableDefinition(String dbName, String tableName) {
		super();
		this.dbName = dbName;
		this.tableName = tableName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, ColumnDefinition> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ColumnDefinition> columns) {
		this.columns = columns;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
	}

	public void setPkColumn(ColumnDefinition pkColumn) {
		this.pkColumn = pkColumn;
	}

	public ColumnDefinition getPkColumn() {
		if (pkColumn != null) {
			return pkColumn;
		}
		if (columns == null || columns.isEmpty()) {
			return pkColumn = null;
		}
		for (ColumnDefinition columnDefinition : columns.values()) {
			if (columnDefinition.getIsPrimaryKey() != null && columnDefinition.getIsPrimaryKey()) {
				pkColumn = columnDefinition;
				return pkColumn;
			}
		}
		return pkColumn = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableDefinition [classType=");
		builder.append(classType);
		builder.append(", columns=");
		builder.append(columns);
		builder.append(", dbName=");
		builder.append(dbName);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append("]");
		return builder.toString();
	}
}
