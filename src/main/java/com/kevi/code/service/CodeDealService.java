package com.kevi.code.service;

import com.alibaba.fastjson.JSONObject;

public interface CodeDealService {

    public JSONObject dealCode(String code) throws Exception;

}
