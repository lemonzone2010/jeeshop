package org.hum.framework.hawaii.orm.core.mapperholder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;
import org.hum.framework.hawaii.orm.core.parser.ClassParser;

/**
 * 这里我只希望tableDefinitionMap返回的数据正确，不会被线程安全困扰
 */
public abstract class AbstractMapperHolder implements ConfigurationMapperHolder {

	protected final Map<String, TableDefinition> tableDefinitionMap = new ConcurrentHashMap<String, TableDefinition>();
	protected final Map<String, ColumnDefinition> columnDefinitionMap = new ConcurrentHashMap<String, ColumnDefinition>();

	protected ClassParser beanParser;

	protected boolean addTableDefinition(String definitionName, TableDefinition tableDefinition) {
		tableDefinitionMap.put(definitionName, tableDefinition);

		// 这里到底有没有意义？
		for (ColumnDefinition columnDefinition : tableDefinition.getColumns().values()) {
			addColumnDefinition(columnDefinition);
		}
		return true;
	}

	protected boolean addColumnDefinition(String definitionName, ColumnDefinition columnDefinition) {
		columnDefinitionMap.put(definitionName, columnDefinition);
		return true;
	}

	@Override
	public boolean deleteTableDefinition(String definitionName) {
		tableDefinitionMap.remove(definitionName);
		return true;
	}

	@Override
	public TableDefinition getTableDefinition(String definitionName) {
		return tableDefinitionMap.get(definitionName);
	}

	@Override
	public boolean deleteColumnDefinition(String definitionName) {
		columnDefinitionMap.remove(definitionName);
		return true;
	}

	@Override
	public ColumnDefinition getColumnDefinition(String definitionName) {
		return columnDefinitionMap.get(definitionName);
	}

	protected TableDefinition getTableDefinitionNoSync(String definitionName) {
		return tableDefinitionMap.get(definitionName);
	}

	public void setBeanParser(ClassParser beanParser) {
		this.beanParser = beanParser;
	}
}
