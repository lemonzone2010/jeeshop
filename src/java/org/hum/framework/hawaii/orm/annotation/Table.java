package org.hum.framework.hawaii.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
//AnnotationMetadataReadingVisitor
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
@Component
public @interface Table {
	String dbName() default "";
	String tableName() default "";
}
