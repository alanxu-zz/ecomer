package me.alanx.ecomer.core.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import me.alanx.ecomer.core.model.filter.FilterValueType;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, TYPE })
@Retention(RUNTIME)
@Inherited
public @interface FilterCandidate {
	String key() default "";
	
	String by() default "";
	
	FilterValueType type() default FilterValueType.UNDEFINED;
}
