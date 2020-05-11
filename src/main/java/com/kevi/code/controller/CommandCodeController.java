package com.kevi.code.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cmd")
public class CommandCodeController {

    @PostMapping("code")
    public ResponseEntity<JSONObject> dealCode(@RequestParam("code") String code){
        JSONObject info = new JSONObject();
        info.put("code", "send code:" + code);
        return ResponseEntity.ok(info);
    }

}
