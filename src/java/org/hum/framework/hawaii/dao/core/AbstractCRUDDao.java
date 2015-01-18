package org.hum.framework.hawaii.dao.core;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hum.framework.hawaii.dao.bean.QueryCondition;
import org.hum.framework.hawaii.dao.bean.QueryWrapper;
import org.hum.framework.hawaii.dao.enumtype.Op;
import org.hum.framework.hawaii.dao.enumtype.Sort;
import org.hum.framework.hawaii.dao.exception.ParseSqlException;
import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;
import org.hum.framework.hawaii.orm.core.mapperholder.MapperHolder;
import org.hum.framework.hawaii.orm.exception.HawaiiMapperException;
import org.hum.framework.hawaii.orm.util.NameUtils;
import org.hum.framework.hawaii.util.ReflectUtils;
import org.hum.framework.hawaii.util.StringUtils;

/**
 * 限定一套基本的CRUD模板，并将之与orm层结合一起
 * 
 * @author bjhuming
 */
public abstract class AbstractCRUDDao extends AbstractDao {

	private static final String INSERT_SQL = "insert into {0}({1}) values ({2})";
	private static final String DELETE_SQL = "delete from {0} where {1}";
	private static final String UPDATE_SQL = "update {0} set {1} where {2}";
	private static final String COUNT_SQL = "select count(*) from {0} where 1=1 {1}";
	private static final String LIST_SQL = "select {0} from {1} where 1=1 {2} {3} {4}";
	private static final String DETAIL_SQL = "select * from {0} where {1}";

	private MapperHolder tableMapperHolder;

	protected String getInsertSQL(Object insertObject) {
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		// TODO
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(insertObject));
		for (ColumnDefinition columnDefinition : tableDefinition.getColumns().values()) {
			// 如果是标识列，则不拼装SQL语句
			if (!columnDefinition.getIsIdentity()) {
				columnBuilder.append(columnDefinition.getColName()).append(",");
				valueBuilder.append(getValSql(ReflectUtils.fetchValue(insertObject, columnDefinition.getField().getName()))).append(",");
			}
		}
		columnBuilder = new StringBuilder(StringUtils.kickLastComma(columnBuilder.toString()));
		valueBuilder = new StringBuilder(StringUtils.kickLastComma(valueBuilder.toString()));
		String sql = MessageFormat.format(INSERT_SQL, tableDefinition.getTableName(), columnBuilder.toString(), valueBuilder.toString());
		return sql;
	}

	protected <T> String getDetailSQL(Class<T> classType, Object pkValue) {
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(classType));
		ColumnDefinition pkColumn = tableDefinition.getPkColumn();
		if (pkColumn == null) {
			throw new HawaiiMapperException("can't find primary key in " + classType.getName());
		}
		String conditionSQL = pkColumn.getColName() + "=" + getValSql(pkValue);
		return MessageFormat.format(DETAIL_SQL, tableDefinition.getTableName(), conditionSQL);
	}

	protected <T> String getDeleteSQL(Class<T> classType, Object pkValue) {
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(classType));
		// generate update sql->where : "id=1"
		ColumnDefinition pkColumn = tableDefinition.getPkColumn();
		if (pkColumn == null) {
			throw new HawaiiMapperException("can't find primary key in " + classType.getName());
		}
		String conditionSQL = pkColumn.getColName() + "=" + getValSql(pkValue);
		return MessageFormat.format(DELETE_SQL, tableDefinition.getTableName(), conditionSQL);
	}

	protected String getUpdateSQL(Object updateObject) {
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(updateObject));
		ColumnDefinition pkColumn = tableDefinition.getPkColumn();
		Map<String, Object> valuMap = ReflectUtils.fetchNotNullValue(updateObject);
		StringBuilder sqlBuilder = new StringBuilder();
		for (Entry<String, Object> entry : valuMap.entrySet()) {
			ColumnDefinition columnDefinition = tableDefinition.getColumns().get(entry.getKey());
			// don't append PK value when generate 'set SQL'
			if (!columnDefinition.getIsPrimaryKey()) {
				sqlBuilder.append(columnDefinition.getColName()).append("=").append(getValSql(entry.getValue())).append(",");
			}
		}
		// generate update sql->set : "name='hm',age=25"
		String setValueSQL = StringUtils.kickLastComma(sqlBuilder.toString());
		// generate update sql->where : "id=1"
		if (pkColumn == null) {
			throw new HawaiiMapperException("can't find primary key in " + updateObject.getClass().getName());
		}
		String conditionSQL = pkColumn.getColName() + "=" + getValSql(ReflectUtils.fetchValue(updateObject, pkColumn.getField().getName()));
		return MessageFormat.format(UPDATE_SQL, tableDefinition.getTableName(), setValueSQL, conditionSQL);
	}

	protected <T> String getCountSQL(QueryWrapper<T> queryWrapper) {
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(queryWrapper.getClassType()));
		String sql = MessageFormat.format(COUNT_SQL, tableDefinition.getTableName(), conditionSQL(tableDefinition, queryWrapper));
		return sql;
	}

	protected <T> String getListSQL(QueryWrapper<T> queryWrapper, int offset, int pageSize, String[] queryColumns) {
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(queryWrapper.getClassType()));
		String sqlColumn = queryColumns == null || queryColumns.length == 0 ? "*" : Arrays.toString(queryColumns).replaceAll("\\[|\\]", "");
		String sqlLimit = pageSize == 0 ? "" : ("limit " + offset + "," + pageSize);
		String sqlCondition = conditionSQL(tableDefinition, queryWrapper);
		String sortColName = queryWrapper.getSortField() != null ? tableDefinition.getColumns().get(queryWrapper.getSortField().getName()).getColName() : null;
		String sqlSort = (queryWrapper.getSort() != null && sortColName != null) ? "order by " + sortColName + " " + queryWrapper.getSort().getValue() : "";
		String sql = MessageFormat.format(LIST_SQL, sqlColumn, tableDefinition.getTableName(), sqlCondition, sqlSort, sqlLimit);
		return sql;
	}

	@Deprecated
	protected String getListSQL(Object listObject, int _offset, int _pageSize, String _sortFieldName, Sort _sort, String[] queryColumns, String... conditionSort) {
		TableDefinition tableDefinition = tableMapperHolder.getTableDefinition(NameUtils.getTableDefinitionkeyName(listObject));
		String sqlColumn = queryColumns == null || queryColumns.length == 0 ? "*" : Arrays.toString(queryColumns).replaceAll("\\[|\\]", "");
		String sqlLimit = _pageSize == 0 ? "" : ("limit " + _offset + "," + _pageSize);
		String sqlCondition = conditionSQL(tableDefinition, listObject, conditionSort);
		String sqlSort = (StringUtils.isBlank(_sortFieldName) || _sort == null) ? "" : "order by " + _sortFieldName + " " + _sort.getValue();
		String sql = MessageFormat.format(LIST_SQL, sqlColumn, tableDefinition.getTableName(), sqlCondition, sqlSort, sqlLimit);
		return sql;
	}

	private static <T> String conditionSQL(TableDefinition tableDefinition, QueryWrapper<T> queryWrapper) {
		StringBuilder sqlBuilder = new StringBuilder();

		for (QueryCondition condition : queryWrapper.getConditions()) {
			ColumnDefinition column = tableDefinition.getColumns().get(condition.getField().getName());
			if (Op.IN == condition.getOp()) {
				sqlBuilder.append(" and ").append(column.getColName()).append(" " + condition.getOp().getOperator() + " ").append(getValSql(condition.getValue()));
			} else {
				sqlBuilder.append(" and ").append(column.getColName()).append(" " + condition.getOp().getOperator() + " ").append(getValSql(condition.getValue()));
			}
		}

		return sqlBuilder.toString();
	}

	@Deprecated
	private static String conditionSQL(TableDefinition tableDefinition, Object countObject, String... conditionSort) {
		StringBuilder sqlBuilder = new StringBuilder();
		Map<String, Object> valueMap = ReflectUtils.fetchNotNullValue(countObject);
		Map<String, Object> conditionMap = new LinkedHashMap<String, Object>();

		// 根据查询条件准备出要拼装SQL的Map
		if (conditionSort != null && conditionSort.length > 0) {
			for (String condition : conditionSort) {
				if (!valueMap.containsKey(condition)) {
					throw new ParseSqlException("condition[" + condition + "] not contains");
				}
				conditionMap.put(condition, valueMap.get(condition));
				// sqlBuilder.append(" and ").append(condition).append("=").append(getValSql(valueMap.get(condition)));
			}
		} else {
			for (Entry<String, Object> entry : valueMap.entrySet()) {
				conditionMap.put(entry.getKey(), entry.getValue());
				// sqlBuilder.append(" and ").append(entry.getKey()).append("=").append(getValSql(entry.getValue()));
			}
		}

		// 拼出条件
		for (Entry<String, Object> entry : conditionMap.entrySet()) {
			ColumnDefinition column = tableDefinition.getColumns().get(entry.getKey());
			// TODO 这个"="号，怎么交给客户端?
			sqlBuilder.append(" and ").append(column.getColName()).append("=").append(getValSql(entry.getValue()));
		}

		return sqlBuilder.toString();
	}

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 这里怎么跟AnnotationBeanParse中的parseColumnType整合到一起呢？
	 * 还有ColumnType枚举也是如此，增加一种类型，需要改动3个类，太零散了……
	 */
	@SuppressWarnings("rawtypes")
	private static String getValSql(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			return "'" + obj.toString().replaceAll("'", "''") + "'";
		}
		if (obj instanceof Date) {
			return "'" + dateFormat.format((Date) obj) + "'";
		}
		if (obj instanceof Integer[] || obj instanceof Short[] || obj instanceof Long[]) {
			String valArr = Arrays.toString((Integer[]) obj);
			valArr = "(" + valArr.substring(1, valArr.length() - 1) + ")";
			return valArr;
		}
		if (obj instanceof Iterable) {
			Iterator iter = ((Iterable) obj).iterator();
			StringBuilder sbuilder = new StringBuilder();
			while (iter.hasNext()) {
				sbuilder.append(getValSql(iter.next()) + ",");
			}
			if (sbuilder.length() > 0) {
				sbuilder = new StringBuilder(sbuilder.substring(0, sbuilder.length() - 1));
			}
			return "(" + sbuilder.toString() + ")";
		}
		if (obj instanceof String[]) {
			StringBuilder value = new StringBuilder();
			String[] valArr = (String[]) obj;
			for (String val : valArr) {
				value.append("'" + val.replaceAll("'", "''") + "',");
			}
			if (value.length() > 0) {
				value = new StringBuilder(value.substring(0, value.length() - 1));
			}
			return value.toString();
		}
		return obj.toString();
	}

	public void setTableMapperHolder(MapperHolder tableMapperHolder) {
		this.tableMapperHolder = tableMapperHolder;
	}
}
