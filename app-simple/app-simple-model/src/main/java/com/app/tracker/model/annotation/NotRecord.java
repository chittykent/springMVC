package com.app.tracker.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @author Kabruce E-mail: 770141401@qq.com
 * @version 创建时间：2013-3-24 下午11:16:55 
 *  类说明 
 */

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
@Inherited
public @interface NotRecord {

}
