package com.vedeng.common.validation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class ForbiddenValidator implements ConstraintValidator<Forbidden, String> {

    private String[] forbiddenWords = {"admin"};//验证规则（可以为多个正则表达式）

    @Override
    public void initialize(Forbidden constraintAnnotation) {
        //初始化，得到注解数据
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value)) {
            return true;
        }

        for(String word : forbiddenWords) {
            if(value.contains(word)) {
                return false;//验证失败
            }
        }
        return true;
    }

}
