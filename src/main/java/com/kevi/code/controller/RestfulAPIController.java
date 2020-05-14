package com.kevi.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.kevi.code.entity.requestparam.GameParam;
import com.kevi.code.service.RestfulAPIService;
import com.kevi.code.utils.ControllerParamsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Restful API simple test
 */
@RestController
@RequestMapping("rest")
public class RestfulAPIController {

    @Autowired
    private RestfulAPIService restfulAPIService;

    /**
     * 查看全部游戏信息
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "game", method = RequestMethod.GET)
    @GetMapping("game")
    public ResponseEntity<JSONObject> lookGameInfo() throws Exception{
        JSONObject result = restfulAPIService.lookGame(null);
        return ResponseEntity.ok(result);
    }

    /**
     * 查看指定游戏信息
     * @param name 游戏名称
     * @return
     * @throws Exception
     */
    @GetMapping("game/{name}")
    public ResponseEntity<JSONObject> lookGameInfo(@PathVariable("name") String name) throws Exception {
        //检测参数
        ControllerParamsCheck.StringParamNormalCheck(name);
        //查询指定游戏信息
        JSONObject result = restfulAPIService.lookGame(name);
        return ResponseEntity.ok(result);
    }

    /**
     * 添加游戏信息
     * @param gameParam
     * @return
     * @throws Exception
     */
    @PostMapping("game")
    public ResponseEntity<JSONObject> addGameInfo(@RequestBody GameParam gameParam) throws Exception {
        //参数实体自检测
        gameParam.cleanParamSpace();
        gameParam.checkKeyParam();
        //参数检测
        ControllerParamsCheck.StringParamNormalCheck(gameParam.getName(), gameParam.getAbout());
        //添加游戏信息
        JSONObject result = restfulAPIService.addGame(gameParam);
        return ResponseEntity.ok(result);
    }

    /**
     * 编辑游戏信息
     * @param gameParam
     * @return
     * @throws Exception
     */
    @PutMapping("game")
    public ResponseEntity<JSONObject> editGameInfo(@RequestBody GameParam gameParam) throws Exception {
        //参数实体自检测
        gameParam.cleanParamSpace();
        gameParam.checkEditParam();
        //参数检测
        ControllerParamsCheck.StringParamNormalCheck(gameParam.getName(), gameParam.getAbout());
        //修改游戏信息
        JSONObject result = restfulAPIService.editGame(gameParam);
        return ResponseEntity.ok(result);
    }

    /**
     * 删除游戏信息
     * @param gameParam
     * @return
     * @throws Exception
     */
    @DeleteMapping("game")
    public ResponseEntity<JSONObject> deleteGameInfo(@RequestBody GameParam gameParam) throws Exception {
        //参数实体自检测
        gameParam.cleanParamSpace();
        gameParam.checkKeyParam();
        //参数检测
        ControllerParamsCheck.StringParamNormalCheck(gameParam.getName());
        //添加游戏信息
        JSONObject result = restfulAPIService.deleteGame(gameParam);
        return ResponseEntity.ok(result);
    }
}
