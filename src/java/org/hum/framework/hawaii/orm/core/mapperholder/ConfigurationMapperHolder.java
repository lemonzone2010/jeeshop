package org.hum.framework.hawaii.orm.core.mapperholder;

import org.hum.framework.hawaii.orm.bean.ColumnDefinition;
import org.hum.framework.hawaii.orm.bean.TableDefinition;

/**
 * 可配置容器,基于基础容器可增删改功能
 * 
 * @author bjhuming
 */
public interface ConfigurationMapperHolder extends MapperHolder {

	boolean addTableDefinition(TableDefinition tableDefinition);

	boolean addColumnDefinition(ColumnDefinition columnDefinition);

	boolean deleteTableDefinition(String definitionName);

	boolean deleteColumnDefinition(String definitionName);
}
