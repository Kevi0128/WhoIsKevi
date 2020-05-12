package com.kevi.code.entity.customexception;

import java.util.Map;

/**
 * 未找到对应资源
 */
public class MessageSendFailedException extends BaseException {

    public MessageSendFailedException(Map<String, Object> data){
        super(ErrorCode.MESSAGE_SEND_FAILED, data);
    }

}
