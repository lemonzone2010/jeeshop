package org.hum.framework.hawaii.orm.core.mapperholder;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;

/**
 * 提供基本查询功能,对于客户端而言，只关心产出
 * 
 * @author bjhuming
 */
public interface MapperHolder {

	TableDefinition getTableDefinition(String keyName);

	/**
	 * 需要再想想怎么设计？
	 * @param keyName
	 * @return
	 */
	ColumnDefinition getColumnDefinition(String keyName);
}
