package com.kevi.code.entity.customexception;

import java.util.Map;

/**
 * 数据校验失败
 */
public class RequestValidationFailedException extends BaseException {

    public RequestValidationFailedException(Map<String, Object> data){
        super(ErrorCode.REQUEST_VALIDATION_FAILED, data);
    }

}
