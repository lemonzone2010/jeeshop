package org.hum.framework.hawaii.dao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class DefaultSimpleDao extends AbstractSimpleDao {

	private DataSource dataSource;

	@Override
	public Object batchExecuteSQL(String[] sqlArray) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (String sql : sqlArray) {
				statement.addBatch(sql);
			}
			int[] executeBatch = statement.executeBatch();
			connection.commit();
			return executeBatch;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public Object executeSQL(String sql) throws SQLException {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = dataSource.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			return prepareStatement.executeUpdate();
		} catch (SQLException ce) {
			// TODO logger.error
			System.err.println(sql);
			throw ce;
		} finally {
			if (prepareStatement != null) {
				prepareStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws SQLException {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();
			if (resultSet != null) {
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				while (resultSet.next()) {
					result.add(resultSet2Map(resultSet));
				}
				return result;
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (prepareStatement != null) {
				prepareStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return Collections.emptyList();
	}

	/*
	 * 将Result转成Map
	 */
	private Map<String, Object> resultSet2Map(ResultSet resultSet) throws SQLException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			String keyName = resultSet.getMetaData().getColumnName(i);
			dataMap.put(keyName, resultSet.getObject(i));
		}
		return dataMap;
	}

	@Override
	public Map<String, Object> queryForMap(String sql) throws SQLException {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();
			if (resultSet != null) {
				if (resultSet.next()) {
					return resultSet2Map(resultSet);
				}
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (prepareStatement != null) {
				prepareStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return Collections.emptyMap();
	}

	@Override
	public Object queryForObject(String sql) throws SQLException {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();
			if (resultSet != null) {
				if (resultSet.next()) {
					return resultSet.getObject(1);
				}
			}
		} finally {
			if (prepareStatement != null) {
				prepareStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return null;
	}

	@Override
	public void commitTransaction() throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void endTransaction() throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void startTransaction() throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
