package org.hum.framework.hawaii.dao.core;

import java.sql.SQLException;

import org.hum.framework.hawaii.dao.bean.Pagin;
import org.hum.framework.hawaii.dao.bean.QueryWrapper;

/**
 * 支持分页的DAO
 */
public interface PaginDao extends BaseDao {

	<T> Pagin<T> pagin(T t, int pageNo, int pageSize) throws SQLException;

	<T> Pagin<T> pagin(QueryWrapper<T> queryWrapper, int pageNo, int pageSize) throws SQLException;

	<T> Pagin<T> pagin(QueryWrapper<T> queryWrapper, int pageNo, int pageSize, String[] queryColumns) throws SQLException;
}
