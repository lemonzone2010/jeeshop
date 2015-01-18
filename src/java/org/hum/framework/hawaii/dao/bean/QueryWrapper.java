package org.hum.framework.hawaii.dao.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hum.framework.hawaii.dao.enumtype.Op;
import org.hum.framework.hawaii.dao.enumtype.Sort;
import org.hum.framework.hawaii.util.ReflectUtils;

public class QueryWrapper<T> {

	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();
	private Field sortField;
	private Sort sort;
	private Class<?> classType;
	private T bean;

	public QueryWrapper(Class<T> classType) {
		this.classType = classType;
	}

	public QueryWrapper(T bean) {
		this.bean = bean;
		this.classType = bean.getClass();

		Map<String, Object> values = ReflectUtils.fetchNotNullValue(bean);
		for (Entry<String, Object> entry : values.entrySet()) {
			Field field;
			try {
				field = ReflectUtils.getFieldByName(classType, entry.getKey());
				conditions.add(new QueryCondition(field, Op.EQUAL, entry.getValue()));
			} catch (Exception e) {
				// TODO 这里该抛什么异常好呢？
				e.printStackTrace();
				return ;
			}
		}

	}

	public QueryWrapper<T> buildCondition(String fieldName, Op op, Object value) throws NoSuchFieldException {
		try {
			Field field = ReflectUtils.getFieldByName(classType, fieldName);
			conditions.add(new QueryCondition(field, op, value));
			return this;
		} catch (Exception e) {
			throw new NoSuchFieldException("field [" + fieldName + "] can't be find!");
		}
	}

	public QueryWrapper<T> buildSort(String fieldName, Sort sort) throws NoSuchFieldException {
		try {
			this.sortField = ReflectUtils.getFieldByName(classType, fieldName);
			this.sort = sort;
			return this;
		} catch (Exception e) {
			throw new NoSuchFieldException();
		}
	}

	public Field getSortField() {
		return sortField;
	}

	public Sort getSort() {
		return sort;
	}

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public Object getBean() {
		return bean;
	}
}
