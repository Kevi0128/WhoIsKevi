package com.kevi.code.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 简单包装一下返回数据
 * 避免手滑写错返回数据key值
 */
public class ResultParam {

    public static JSONObject normalInfo(String info){
        JSONObject object = new JSONObject();
        object.put("info", info);
        return object;
    }

    public static JSONObject mapInfo(Map<Object, Object> map){
        JSONObject object = new JSONObject();
        object.put("info", map);
        return object;
    }

    public static JSONObject normalInfo(String info, int move){
        JSONObject object = new JSONObject();
        object.put("info", info);
        object.put("move", move);
        return object;
    }

}