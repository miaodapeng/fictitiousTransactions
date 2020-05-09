package com.vedeng.common.annotation;

import java.lang.annotation.*;

/**
 * 方法锁获取值
 * @author strange
 * @date $
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLockParam {
}
