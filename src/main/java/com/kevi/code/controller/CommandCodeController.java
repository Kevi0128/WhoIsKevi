package com.kevi.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cmd")
public class CommandCodeController {

    @PostMapping("code")
    public ResponseEntity<String> dealCode(@RequestParam("code") String code){
        return ResponseEntity.ok("send code:" + code);
    }

}
