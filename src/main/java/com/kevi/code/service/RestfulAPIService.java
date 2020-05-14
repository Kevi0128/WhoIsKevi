package com.kevi.code.service;

import com.alibaba.fastjson.JSONObject;
import com.kevi.code.entity.requestparam.GameParam;

public interface RestfulAPIService {

    public JSONObject lookGame(String name) throws Exception;

    public JSONObject addGame(GameParam gameParam) throws Exception;

    public JSONObject editGame(GameParam gameParam) throws Exception;

    public JSONObject deleteGame(GameParam gameParam) throws Exception;

}
