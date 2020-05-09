package com.vedeng.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>Description:</b><br> 自定义controller层日志注解
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.annotation
 * <br><b>ClassName:</b> SystemControllerLog
 * <br><b>Date:</b> 2017年9月7日 上午9:16:46
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)    
@Documented  
public @interface SystemControllerLog {
	//业务类型
	int type() default 0;
	//当前方法操作类型（查询：select，新增：add，编辑：edit，删除：delete，导入：import，导出：export）
	String operationType() default "select";
	//当前方法操作描述
	String desc()  default "";

}
