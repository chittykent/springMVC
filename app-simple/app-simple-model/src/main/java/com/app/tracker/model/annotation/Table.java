package com.app.tracker.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.app.tracker.model.common.NullValue;

/** 
 *  类说明 
 */

@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
@Inherited
public @interface Table {

	String name() default "";
	
	/**
	 * (Optional) The database group to store the table.
	 */
	String dbGroup() default "" ;
	
	/**
	 * (Optional) The policy to split the table.
	 */
	Class shadow() default NullValue.class ;

	/**
	 * (Optional) dynamic update? Default false.
	 */
	boolean dynamicUpdate() default false ;

}
