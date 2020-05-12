package com.kevi.code.entity.customexception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RESOURCE_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "未找到对应资源"),
    REQUEST_VALIDATION_FAILED(1002, HttpStatus.BAD_REQUEST, "请求数据验证失败"),
    MESSAGE_SEND_FAILED(1003, HttpStatus.BAD_REQUEST, "发送信息失败"),
    SERVICE_PARAM_EXPECTATION_FAILED(1004, HttpStatus.EXPECTATION_FAILED, "请求数据处理期望错误"),
    LOCAL_HTTP_REQUEST_FAILED(1005, HttpStatus.PROXY_AUTHENTICATION_REQUIRED, "重定向处理数据错误");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(Integer code, HttpStatus status, String message){
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }


    @Override
    public String toString() {
        return "ErrorCode{"+
                "code=" + code + ", " +
                "status=" + status + ", " +
                "message=" + message + "}";
    }
}
