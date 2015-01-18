package org.hum.framework.hawaii.util;

public class SQLUtils {

	/**
	 * TODO 还没有实现呢
	 * @param sql
	 * @return
	 */
	public static String filterSQL(String sql) {
		return sql.replaceAll("'", "''");
	}
}
