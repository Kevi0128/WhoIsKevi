package com.kevi.code.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kevi.code.Init.BaseCmdCode;
import com.kevi.code.service.CodeDealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class CodeDealServiceImpl implements CodeDealService {

    private static final Logger logger = LoggerFactory.getLogger(CodeDealServiceImpl.class);

    @Override
    public JSONObject dealCode(String code) throws Exception {
        JSONObject result = new JSONObject();
        //判断收到的是否是指令
        if (BaseCmdCode.checkCode(code)){
            //是指令
            //判断是哪种指令
            int type = BaseCmdCode.checkCodeType(code);
            //跳转至对应具体处理办法中
            switch (type){
                case BaseCmdCode.active_code:
                    result = activeCodeDeal(code);
                    break;
                case BaseCmdCode.file_code:
                    result = fileCodeDeal(code);
                    break;
                case BaseCmdCode.bonus_scene_code:
                    result = bonusSceneCodeDeal(code);
                    break;
                default:
                    logger.info("【CodeType】不明类型指令:{}", code);
                    String msg = "input code '"+ code +"' isn`t executable command, please use 'help' to get code list.";
                    result.put("info", msg);
            }
        }else {
            //不是指令
            //构建返回信息
            //English
            String msg = "input code '"+ code +"' isn`t executable command, please use 'help' to get code list.";
            //中午
//            String msg = "输入指令'"+ code +"'不是可执行命令，请使用'help'获取可执行指令列表";
            result.put("info", msg);
        }
        return result;
    }

    private JSONObject activeCodeDeal(String code){
        JSONObject result = new JSONObject();
        //返回对应指令操作
        switch (code){
            case BaseCmdCode.Help:
                String msg = "<div class='info'>this project not finish, just some code can use:&nbsp;</div><div class='info'>";
                String baseCodeList = "";
                for (String c : BaseCmdCode.CodeList){
                    baseCodeList = baseCodeList + c + "</br>";
                }
                msg = msg + baseCodeList + "</div>";
                result.put("info", msg);
                break;
        }
        return result;
    }

    private JSONObject fileCodeDeal(String code){
        JSONObject result = new JSONObject();

        return result;
    }

    /**
     * 彩蛋指令处理
     * @param code
     * @return
     */
    private JSONObject bonusSceneCodeDeal(String code){
        JSONObject result = new JSONObject();
        //todo 更加详细的彩蛋指令判断
        //暂时默认彩蛋指令为输出图片
        String img = "<img src='";
        switch (code){
            case BaseCmdCode.CocaCola:
                img = img + "img/CocaCola.png' alt='"+ BaseCmdCode.CocaCola +"'>";
                break;
            case BaseCmdCode.PepsiCola:
                img = img + "img/PepsiCola.png' alt='"+ BaseCmdCode.PepsiCola +"'>";
                break;
        }
        result.put("info", img);
        result.put("move", 2134);
        return result;
    }

}