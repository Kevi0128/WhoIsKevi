package com.kevi.code.utils;

import com.kevi.code.entity.customexception.ErrorMoreInfo;
import com.kevi.code.entity.customexception.RequestValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制层请求参数检测
 */
public class ControllerParamsCheck {

    private static final Logger logger = LoggerFactory.getLogger(ControllerParamsCheck.class);

    /**
     * 字符参数通常检测
     * @param params 待检测参数
     */
    public static void StringParamNormalCheck(String... params) throws Exception{
        for (int i=0; i<params.length; i++){
            String param = params[i];
            if (KeviTool.isVoid(param)){
                throw new RequestValidationFailedException(ErrorMoreInfo.REQUEST_PARAM_EMPTY.getMoreInfo());
            }
            if (KeviTool.SQLCheck(param)){
                throw new RequestValidationFailedException(ErrorMoreInfo.REQUSET_PARAM_SQL_CHECK.getMoreInfo());
            }
        }
    }

}
