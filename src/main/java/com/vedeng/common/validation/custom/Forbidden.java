package com.vedeng.common.validation.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { ForbiddenValidator.class})
public @interface Forbidden {

	    //默认错误消息
	    String message() default "{forbidden.word}";

	    //分组
	    Class<?>[] groups() default { };

	    //负载
	    Class<? extends Payload>[] payload() default { };

	    //指定多个时使用
	    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	    @Retention(RUNTIME)
	    @Documented
	    @interface List {
	        Forbidden[] value();
	    }

}
