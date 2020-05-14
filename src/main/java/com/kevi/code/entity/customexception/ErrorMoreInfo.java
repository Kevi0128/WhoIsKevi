package com.kevi.code.entity.customexception;

import java.util.HashMap;
import java.util.Map;

public enum ErrorMoreInfo {

    REQUEST_PARAM_EMPTY("请求参数发现空值"),
    REQUEST_KEY_PARAM_EMPTY("请求参数(关键参数)发现空值"),
    REQUEST_PARAM_ALL_EMPTY("请求参数全部为空"),
    REQUSET_PARAM_SQL_CHECK("请求参数SQL检测失败"),
    ANOTHER_REQUEST_ERROR("重定向请求失败，请稍后再试"),
    ANOTHER_REQUEST_FAILED("重定向请求被拒，请稍后再试"),
    ANOTHER_REQUEST_RESULT_DEAL_FAILED("重定向请求返回信息解析失败，请稍后再试"),
    NO_FILE_IN_HERE("上传文件为空");

    private final Map<String, Object> moreInfo;

    ErrorMoreInfo(String info){
        this.moreInfo = new HashMap<>();
        moreInfo.put("MoreInfo", info);
    }

    public Map<String, Object> getMoreInfo(){
        return moreInfo;
    }
}
