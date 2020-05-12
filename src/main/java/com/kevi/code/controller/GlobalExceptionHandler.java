package com.kevi.code.controller;

import com.kevi.code.entity.customexception.BaseException;
import com.kevi.code.entity.customexception.ErrrorResponse;
import com.kevi.code.entity.customexception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获及处理
 */
@ControllerAdvice //注解标记该类为全局异常处理类
//@ControllerAdvice(assignableTypes = {HelloController.class})  加注限定处理某指定类异常
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    ErrrorResponse illegalArgumentResponse = new ErrrorResponse(new IllegalArgumentException("参数错误！！！"));
//    ErrrorResponse resourceNotFoundResponse = new ErrrorResponse(new ResourceNotFoundException("sorry, not found this exception resource."));

    //AKHYPERGRYPHCOMGOAL
//    @ExceptionHandler(value = Exception.class) //拦截所有异常，该方法主要为了演示，一般指定某类异常拦截
//    public ResponseEntity<?> exceptionHandler(Exception e){
//        if (e instanceof IllegalArgumentException){
//            return ResponseEntity.status(400).body(illegalArgumentResponse);
//        }else if (e instanceof ResourceNotFoundException){
//            return ResponseEntity.status(404).body(resourceNotFoundResponse);
//        }
//        return null;
//    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> baseExceptionHandler(BaseException e, HttpServletRequest request){
        ErrrorResponse errrorResponse = new ErrrorResponse(e, request.getRequestURL().toString());
        return new ResponseEntity<>(errrorResponse, new HttpHeaders(), e.getError().getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(BaseException e, HttpServletRequest request){
        ErrrorResponse errrorResponse = new ErrrorResponse(e, request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> SystemNoActionError(Exception e, HttpServletRequest request){
        Map<String, Object> info = new HashMap<>();
        info.put("MoreInfo","系统出现异常，请联系系统管理人员进行异常排除");
        String url = request.getRequestURL().toString();
        ErrrorResponse errrorResponse = new ErrrorResponse(e, url, info);
        logger.error("源于{}系统异常记录:", url, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errrorResponse);
    }

}
