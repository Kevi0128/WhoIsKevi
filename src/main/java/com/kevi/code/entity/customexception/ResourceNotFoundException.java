package com.kevi.code.entity.customexception;

import java.util.Map;

/**
 * 未找到对应资源
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(Map<String, Object> data){
        super(ErrorCode.RESOURCE_NOT_FOUND, data);
    }

}
