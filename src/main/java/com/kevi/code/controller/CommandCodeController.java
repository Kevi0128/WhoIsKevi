package com.kevi.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kevi.code.service.CodeDealService;
import com.kevi.code.utils.ControllerParamsCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cmd")
public class CommandCodeController {

    private static final Logger logger = LoggerFactory.getLogger(CommandCodeController.class);

    @Autowired
    private CodeDealService codeDealService;

    /**
     * 解析前端传来指令
     * @param code
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<JSONObject> dealCode(@RequestParam("code") String code) throws Exception {
        //参数检测
        ControllerParamsCheck.StringParamNormalCheck(code);
        //Service处理
        JSONObject info = codeDealService.dealCode(code);
        return ResponseEntity.ok(info);
    }

}
