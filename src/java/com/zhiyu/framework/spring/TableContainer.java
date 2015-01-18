package com.zhiyu.framework.spring;

import java.lang.reflect.Field;

import org.hum.framework.hawaii.orm.aware.TableMapperHolderAware;
import org.hum.framework.hawaii.orm.core.mapperholder.MapperHolder;

public class TableContainer implements TableMapperHolderAware {

	private MapperHolder tableMapperHolder = null;

	@Override
	public MapperHolder getTableMapperHolder() {
		return this.tableMapperHolder;
	}

	@Override
	public void setTableMapperHolder(MapperHolder tableMapperHolder) {
		this.tableMapperHolder = tableMapperHolder;
	}

	public static void main(String[] args) {
		for (Field field : TableContainer.class.getDeclaredFields()) {
			System.out.println(field.hashCode());
		}
	}

}
