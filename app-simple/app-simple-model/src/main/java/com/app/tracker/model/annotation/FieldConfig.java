package com.app.tracker.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @author 陆卫东 E-mail: 770141401@qq.com
 * @version 创建时间：2013-6-5 
 *  类说明 
 */

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
@Inherited
public @interface FieldConfig {
	public abstract int idx();
	public abstract String method();
}
