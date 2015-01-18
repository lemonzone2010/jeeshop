package org.hum.framework.hawaii.orm.bean;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.orm.enumtype.ColumnType;
import org.hum.framework.hawaii.util.ObjectUtils;

public class ColumnDefinition {

	private Field field;
	private Boolean isPrimaryKey;
	private Boolean isIdentity;
	private String colName;
	private ColumnType columnType;
	private Integer length;

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Boolean getIsIdentity() {
		return isIdentity;
	}

	public void setIsIdentity(Boolean isIdentity) {
		this.isIdentity = isIdentity;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(colName).append(" ");
		builder.append(columnType).append((length != null && length > 0) ? ("(" + length + ")" ): "").append(" ");
		if (isPrimaryKey != null && isPrimaryKey) {
			builder.append("primary key").append(" ");
		}
		if (isIdentity != null && isIdentity) {
			builder.append("identity").append(" ");
		}
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ColumnDefinition)) {
			return false;
		} 
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ColumnDefinition column = (ColumnDefinition) obj;
		return ObjectUtils.equals(colName, column.getColName()) && ObjectUtils.equals(columnType, column.getColumnType())
		&& ObjectUtils.equals(length, column.getLength()) && ObjectUtils.equals(isPrimaryKey, column.getIsPrimaryKey())
		&& ObjectUtils.equals(isIdentity, column.getIsIdentity());
	}
	
	@Override
	public int hashCode() {
		StringBuilder sbuilder = new StringBuilder(this.getClass().getClass().getName());
		sbuilder.append(colName).append(columnType).append(length).append(isPrimaryKey).append(isIdentity);
		return sbuilder.toString().hashCode();
	}
}
