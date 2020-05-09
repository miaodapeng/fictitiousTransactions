package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;

/**
 * 校验器接口
 */
public interface Validator<T> {

    public ValidatorResult validator(T requestData);

}
