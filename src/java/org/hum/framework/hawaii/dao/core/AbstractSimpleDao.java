package org.hum.framework.hawaii.dao.core;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hum.framework.hawaii.dao.bean.Pagin;
import org.hum.framework.hawaii.dao.bean.QueryWrapper;
import org.hum.framework.hawaii.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


/**
 * <pre>
 * 简单DAO实现，对于CRUD操作流程做了仅仅做了基本限定: 
 * 	1. 生成SQL语句 
 * 	2. 执行SQL
 * 如果客户端需要高级功能，例如在分库分表、加缓存等，则需要另行实现
 * </pre>
 * 
 * @author bjhuming
 */
public abstract class AbstractSimpleDao extends AbstractCRUDDao {

	private static final Logger log = LoggerFactory.getLogger(AbstractSimpleDao.class);

	@Override
	public <T> void add(T t) throws SQLException {
		String insertSQL = getInsertSQL(t);
		executeSQL(insertSQL);
	}

	@Override
	public <T> void deleteByPK(Class<T> classType, Object pkValue) throws SQLException {
		String deleteSQL = getDeleteSQL(classType, pkValue);
		executeSQL(deleteSQL);
	}

	@Override
	public <T> T get(Class<T> classType, Object pkValue) throws SQLException {
		String detailSQL = getDetailSQL(classType, pkValue);
		return queryForMap(detailSQL, classType);
	}

	@Override
	public <T> int count(QueryWrapper<T> queryWrapper) throws SQLException {
		String countSQL = getCountSQL(queryWrapper);
		return Integer.parseInt(queryForObject(countSQL).toString());
	}

	@Override
	public <T> void updateByPK(T t) throws SQLException {
		String updateSQL = getUpdateSQL(t);
		executeSQL(updateSQL);
	}

	@Override
	public <T> List<T> list(T t, int pageNo, int pageSize) throws SQLException {
//		return list(new QueryWrapper<T>(t.getClass()), pageNo, pageSize);
		return null;
	}

	@Override
	public <T> List<T> list(QueryWrapper<T> queryWrapper, int pageNo, int pageSize) throws SQLException {
		return list(queryWrapper, pageNo, pageSize, null);
	}

	@Override
	public <T> List<T> list(QueryWrapper<T> queryWrapper, int pageNo, int pageSize, String[] queryColumns) throws SQLException {
		Assert.isTrue(pageNo > 0, "pageNo must lagger than zero");
		Assert.isTrue(pageSize > 0, "pageSize must lagger than zero");
		
		String listSQL = getListSQL(queryWrapper, (pageNo - 1) * pageSize, pageSize, queryColumns);
		try {
			List<Map<String, Object>> resultList = queryForList(listSQL);
			return (List<T>) ReflectUtils.mapList2beanList(queryWrapper.getClassType(), resultList);
		} catch (SQLException ce) {
			log.error(listSQL);
			throw ce;
		}
	}

	@Override
	public <T> Pagin<T> pagin(T t, int pageNo, int pageSize) throws SQLException {
//		return pagin(queryWrapper, pageNo, pageSize, null);

		return null;
	}

	@Override
	public <T> Pagin<T> pagin(QueryWrapper<T> queryWrapper, int pageNo, int pageSize) throws SQLException {
		return pagin(queryWrapper, pageNo, pageSize, null);
	}

	@Override
	public <T> Pagin<T> pagin(QueryWrapper<T> queryWrapper, int pageNo, int pageSize, String[] queryColumns) throws SQLException {
		Assert.isTrue(pageNo <= 0, "pageNo must lagger than zero");
		Assert.isTrue(pageSize <= 0, "pageSize must lagger than zero");
		
		int count = count(queryWrapper);
		if (count == 0) {
			return Pagin.emptyPagin(pageNo, pageSize);
		}
		Pagin<T> pagin = new Pagin<T>();
		pagin.setPageNo(pageNo);
		pagin.setPageSize(pageSize);
		pagin.setTotalItem(count);
		pagin.setDataList(list(queryWrapper, pageNo, pageSize, queryColumns));
		return pagin;
	}
}
