package org.hum.framework.hawaii.orm.core.mapperholder;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;
import org.hum.framework.hawaii.orm.util.NameUtils;

public class DefaultMapperHolder extends AbstractMapperHolder {

	@Override
	public boolean addColumnDefinition(ColumnDefinition columnDefinition) {
		String definitionName = NameUtils.getColumnDefinitionkeyName(columnDefinition);
		return super.addColumnDefinition(definitionName, columnDefinition);
	}

	@Override
	public boolean addTableDefinition(TableDefinition tableDefinition) {
		String definitionName = NameUtils.getTableDefinitionkeyName(tableDefinition);
		return super.addTableDefinition(definitionName, tableDefinition);
	}

}