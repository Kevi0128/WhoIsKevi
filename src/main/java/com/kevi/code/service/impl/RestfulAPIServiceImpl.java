package com.kevi.code.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kevi.code.entity.ResultParam;
import com.kevi.code.entity.requestparam.GameParam;
import com.kevi.code.redis.RedisKey;
import com.kevi.code.redis.RedisUtil;
import com.kevi.code.service.RestfulAPIService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 简单处理数据
 * 直接增删改查
 */
@Service
public class RestfulAPIServiceImpl implements RestfulAPIService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public JSONObject lookGame(String name) throws Exception {
        Map<Object, Object> gameMap = redisUtil.hashMapGet(RedisKey.Game);
//        return ResultParam.normalInfo(JSON.toJSONString(gameMap));
        return ResultParam.mapInfo(gameMap);
    }

    @Override
    public JSONObject addGame(GameParam gameParam) throws Exception {
        boolean check = redisUtil.hashSet(RedisKey.Game, gameParam.getName(), gameParam.getAbout());
        if (check) {
            return ResultParam.normalInfo("success");
        }else {
            return ResultParam.normalInfo("failed");
        }
    }

    @Override
    public JSONObject editGame(GameParam gameParam) throws Exception {
        if (redisUtil.hashSet(RedisKey.Game, gameParam.getName(), gameParam.getAbout())){
            return ResultParam.normalInfo("success");
        }else {
            return ResultParam.normalInfo("failed");
        }
    }

    @Override
    public JSONObject deleteGame(GameParam gameParam) throws Exception {
        redisUtil.hashDelete(RedisKey.Game, gameParam.getName());
        return ResultParam.normalInfo("deal");
    }
}