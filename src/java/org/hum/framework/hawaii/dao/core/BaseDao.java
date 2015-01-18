package org.hum.framework.hawaii.dao.core;

import java.sql.SQLException;
import java.util.List;

import org.hum.framework.hawaii.dao.bean.QueryWrapper;

/**
 * 基础DAO的接口
 * 
 * 这里仅定义了[增删改查]4项的最基本操作
 */
public interface BaseDao {

	<T> void add(T t) throws SQLException;

	<T> void deleteByPK(Class<T> classType, Object pkValue) throws SQLException;

	<T> void updateByPK(T t) throws SQLException;

	<T> T get(Class<T> classType, Object pkValue) throws SQLException;

	<T> int count(QueryWrapper<T> queryWrapper) throws SQLException;

	<T> List<T> list(T t, int pageNo, int pageSize) throws SQLException;

	<T> List<T> list(QueryWrapper<T> queryWrapper, int pageNo, int pageSize) throws SQLException;

	<T> List<T> list(QueryWrapper<T> queryWrapper, int pageNo, int pageSize, String[] queryColumns) throws SQLException;
}
