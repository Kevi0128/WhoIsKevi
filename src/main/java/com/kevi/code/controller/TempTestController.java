package com.kevi.code.controller;

import com.kevi.code.redis.RedisUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 本地测试用
 * 正式发布时记得注释
 */
@RestController
@RequestMapping("temp/test")
public class TempTestController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("a")
    public ResponseEntity<String> testA(){
        long a = redisUtil.listSet("a", new Date());
        return ResponseEntity.ok(""+a);
    }

    @RequestMapping("b")
    public ResponseEntity<String> testB(){
        List<Object> dateList = redisUtil.listGet("a", 0,-1);
        return ResponseEntity.ok(dateList.toString());
    }

}
