package com.kevi.code.entity.customexception;

import java.util.Map;

/**
 * 数据校验失败
 */
public class ServiceParamValidationFailedException extends BaseException {

    public ServiceParamValidationFailedException(Map<String, Object> data){
        super(ErrorCode.SERVICE_PARAM_EXPECTATION_FAILED, data);
    }

}
