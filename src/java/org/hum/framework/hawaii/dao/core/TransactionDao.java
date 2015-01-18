package org.hum.framework.hawaii.dao.core;

import java.sql.SQLException;

/**
 * 支持事务的DAO
 */
public interface TransactionDao {

	void startTransaction() throws SQLException;

	void commitTransaction() throws SQLException;

	void endTransaction() throws SQLException;
}
