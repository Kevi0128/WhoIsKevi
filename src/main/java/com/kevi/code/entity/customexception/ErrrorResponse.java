package com.kevi.code.entity.customexception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息实体类
 * 异常信息包装
 */
@Getter
@Setter
@ToString
public class ErrrorResponse {
    private int code;
    private int status;
    private String message;
    private String path;
    private Instant timestamp;
    private HashMap<String, Object> data = new HashMap<>();

    public ErrrorResponse(){}

    public ErrrorResponse(BaseException e, String path){
        this(e.getError().getCode(), e.getError().getStatus().value(), e.getError().getMessage(), path, e.getData());
    }

    public ErrrorResponse(Exception e, String path, Map<String, Object> tip){
        this(500, 500, e.toString(), path, tip);
    }

    public ErrrorResponse(int code, int status, String message, String path, Map<String, Object> data){
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if (!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }

}
