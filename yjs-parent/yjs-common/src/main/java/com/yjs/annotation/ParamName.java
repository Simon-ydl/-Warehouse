package com.yjs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识特殊参数名称
 * 注意：必须将要标记的参数放在参数最前面
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {
	String value();
	/**
	 * 用于统一命名数据源id参数名称
	 */
	public static final String DATASOURCE_ID = "DS_ID";
}
