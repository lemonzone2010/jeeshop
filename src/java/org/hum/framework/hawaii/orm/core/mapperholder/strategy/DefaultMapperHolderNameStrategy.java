package org.hum.framework.hawaii.orm.core.mapperholder.strategy;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;

public class DefaultMapperHolderNameStrategy  implements MapperHolderNameStrategy {
	
	@Override
	public String tableDefinitionKeyName(TableDefinition tableDefinition) {
		return tableDefinition.getClassType().getName();
	}

	@Override
	public String columnDefinitionKeyName(ColumnDefinition columnDefinition) {
		return columnDefinition.getField().getName() + columnDefinition.getField().hashCode();
	}
}
