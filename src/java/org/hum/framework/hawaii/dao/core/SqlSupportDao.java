package org.hum.framework.hawaii.dao.core;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 支持原生SQL语句的DAO
 */
public interface SqlSupportDao {

	Object executeSQL(String sql) throws SQLException;

	Object queryForObject(String sql) throws SQLException;
	
	<T> T queryForMap(String sql, Class<T> classType) throws SQLException;

	Map<String, Object> queryForMap(String sql) throws SQLException;

	List<Map<String, Object>> queryForList(String sql) throws SQLException;
	
	<T> List<T> queryForList(String sql, Class<T> classType) throws SQLException;

	Object batchExecuteSQL(String[] sqlArray) throws SQLException;
}
