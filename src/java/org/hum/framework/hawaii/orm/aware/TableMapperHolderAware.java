package org.hum.framework.hawaii.orm.aware;

import org.hum.framework.hawaii.orm.core.mapperholder.MapperHolder;

public interface TableMapperHolderAware {

	void setTableMapperHolder(MapperHolder tableMapperHolder);

	MapperHolder getTableMapperHolder();
}
