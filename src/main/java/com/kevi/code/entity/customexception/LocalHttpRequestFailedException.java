package com.kevi.code.entity.customexception;

import java.util.Map;

/**
 * 数据校验失败
 */
public class LocalHttpRequestFailedException extends BaseException {

    public LocalHttpRequestFailedException(Map<String, Object> data){
        super(ErrorCode.LOCAL_HTTP_REQUEST_FAILED, data);
    }

}
