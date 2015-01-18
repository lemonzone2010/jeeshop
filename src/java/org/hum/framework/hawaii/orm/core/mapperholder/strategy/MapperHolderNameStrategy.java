package org.hum.framework.hawaii.orm.core.mapperholder.strategy;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;

public interface MapperHolderNameStrategy {
	String columnDefinitionKeyName(ColumnDefinition columnDefinition);

	String tableDefinitionKeyName(TableDefinition tableDefinition);
}
