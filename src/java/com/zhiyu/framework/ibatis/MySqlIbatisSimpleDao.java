package com.zhiyu.framework.ibatis;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hum.hawaii.dao.core.AbstractSimpleDao;

import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("unchecked")
public class MySqlIbatisSimpleDao extends AbstractSimpleDao {

	protected SqlMapClient sqlMapClient;

	@Override
	public Object batchExecuteSQL(String[] sqlArray) {
		throw new UnsupportedOperationException("sorry, DefaultIbatisDao unsupport original sql!");
	}

	@Override
	public Object executeSQL(String sql) throws SQLException {
		return sqlMapClient.update("MySQL.executeSQL", sql);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws SQLException {
		return sqlMapClient.queryForList("MySQL.queryForList", sql);
	}

	@Override
	public Object queryForObject(String sql) throws SQLException {
		return sqlMapClient.queryForObject("MySQL.queryForObject", sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> queryForMap(String sql) throws SQLException {
		List queryForList = sqlMapClient.queryForList("MySQL.queryForList", sql);
		if (queryForList != null) {
			return (Map<String, Object>) sqlMapClient.queryForList("MySQL.queryForList", sql).get(0);
		} else {
			return Collections.emptyMap();
		}
	}

	@Override
	public void commitTransaction() throws SQLException {
		throw new UnsupportedOperationException("sorry,DefaultIbatisDao unsupport transaction!");
	}

	@Override
	public void endTransaction() throws SQLException {
		throw new UnsupportedOperationException("sorry,DefaultIbatisDao unsupport transaction!");
	}

	@Override
	public void startTransaction() throws SQLException {
		throw new UnsupportedOperationException("sorry,DefaultIbatisDao unsupport transaction!");
	}

	@Override
	public DataSource getDataSource() {
		if (sqlMapClient != null) {
			return sqlMapClient.getDataSource();
		} else {
			return null;
		}
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
}