package org.hum.framework.hawaii.dao.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hum.framework.hawaii.orm.exception.HawaiiMapperException;
import org.hum.framework.hawaii.util.ReflectUtils;

/**
 * DAO层的第一个抽象类， 
 */
public abstract class AbstractDao implements BaseDao, PaginDao, SqlSupportDao, TransactionDao {

	public abstract DataSource getDataSource();

	@Override
	public <T> List<T> queryForList(String sql, Class<T> classType) throws SQLException {
		List<Map<String, Object>> queryForList = queryForList(sql);
		if (queryForList == null || queryForList.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> resultList = new ArrayList<T>();
		for (Map<String, Object> map : queryForList) {
			try {
				resultList.add(ReflectUtils.map2Bean(classType, map));
			} catch (Exception ce) {
				throw new HawaiiMapperException(map + " cast to " + classType + " failed", ce);
			}
		}
		return resultList;
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	public <T> T queryForMap(String sql, Class<T> classType) throws SQLException {
		Object queryForObject = queryForMap(sql);
		if (queryForObject instanceof Map) {
			try {
				if (queryForObject == null || ((Map)queryForObject).isEmpty()) {
					return null;
				}
				return (T) ReflectUtils.map2Bean(classType, (Map) queryForObject);
			} catch (Exception ce) {
				throw new HawaiiMapperException(queryForObject + " cast to " + classType + " failed", ce);
			}
		} else {
			return (T) queryForObject;
		}
	}
}
