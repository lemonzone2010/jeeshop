package org.hum.framework.hawaii.dao.bean;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.dao.enumtype.Op;

public class QueryCondition {

	private Field field;
	private Op op;
	private Object value;

	public QueryCondition() {
	}

	public QueryCondition(Field field, Op op, Object value) {
		super();
		this.field = field;
		this.op = op;
		this.value = value;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Op getOp() {
		return op;
	}

	public void setOp(Op op) {
		this.op = op;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
