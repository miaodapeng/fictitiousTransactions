package com.vedeng.common.annotation;

import java.lang.annotation.*;

/**
 *方法锁注解
 * @author strange
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLock {
    /**
    *如果需要使用基本数据类型包装类作为key,则需在参数前加 @MethodLockParam 注解,作用是定位准确的入参key
    * @Author:strange
    * @Date:16:50 2020-01-07
    */
    //指定字段 例子:saleorderNo
    String field() default "";
    //锁的超时时间 默认5分钟
    int time() default 300000;
    //指定类
    Class className();

}
