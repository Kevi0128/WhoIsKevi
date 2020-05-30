package com.kevi.code.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kevi.code.Init.BaseCmdCode;
import com.kevi.code.Init.KeviWorkInfo;
import com.kevi.code.dao.KeviWorkInfoMapper;
import com.kevi.code.dao.query.KeviWorkInfoCustomQuery;
import com.kevi.code.entity.KeviWorkInfoExample;
import com.kevi.code.entity.ResultParam;
import com.kevi.code.service.CodeDealService;
import com.kevi.code.utils.KeviTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class CodeDealServiceImpl implements CodeDealService {

    private static final Logger logger = LoggerFactory.getLogger(CodeDealServiceImpl.class);

    @Resource
    private KeviWorkInfoMapper keviWorkInfoMapper;
    @Resource
    private KeviWorkInfoCustomQuery keviWorkInfoCustomQuery;

    //todo 返回JSON包装一下
    @Override
    public JSONObject dealCode(String code) throws Exception {
        BaseCmdCode baseCmdCode = BaseCmdCode.getInstance();
        JSONObject result = new JSONObject();
        //判断收到的是否是指令
        if (baseCmdCode.checkCode(code)){
            //是指令
            //判断是哪种指令
            int type = baseCmdCode.checkCodeType(code);
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
                    result = ResultParam.normalInfo(msg);
            }
        }else {
            //不是指令
            //构建返回信息
            //English
            String msg = "input code '"+ code +"' isn`t executable command, please use 'help' to get code list.";
            //中午
//            String msg = "输入指令'"+ code +"'不是可执行命令，请使用'help'获取可执行指令列表";
            result = ResultParam.normalInfo(msg);
        }
        return result;
    }

    private JSONObject activeCodeDeal(String code){
        BaseCmdCode baseCmdCode = BaseCmdCode.getInstance();
        JSONObject result = new JSONObject();
        //返回对应指令操作
        switch (code){
            case BaseCmdCode.Help:
                String msg = "<div class='inline'>this project not finish, just some code can use:&nbsp;</div><div class='info'>";
                String baseCodeList = "";
                for (String c : baseCmdCode.getHelpList()){
                    baseCodeList = baseCodeList + c + "</br>";
                }
                msg = msg + baseCodeList + "</div>";
                result = ResultParam.normalInfo(msg);
                break;
            case BaseCmdCode.DailyCode:
                String diary = "<p class='textInfo'>2020-05-30</p>" +
                        "<p class='textInfo'>最近一周开始复习设计模式</p>" +
                        "<p class='textInfo'>怎么说呢？</p>" +
                        "<p class='textInfo'>其实这不算是我主动想起了要复习，而是这段时间想换工作，拿到了一份面试邀请，被人问到了</p>" +
                        "<p class='textInfo'>能理解那种耻辱吗？</p>" +
                        "<p class='textInfo'>明明是习以为常的做法，考虑代码的时候也会那么去考虑，但是呢，应为太久没复习理论知识了，想说说不出来啊</p>" +
                        "<p class='textInfo'>\"我不知道\"</p>" +
                        "<p class='textInfo'>我居然只能这么回答...</p>" +
                        "<p class='textInfo'>复习呗，顺带手动写一下，明确下记忆</p>" +
                        "<p class='textInfo'>设计模式 <a href='https://github.com/Kevi0128/JavaDesignPatterns' target='_blank'>https://github.com/Kevi0128/JavaDesignPatterns</a></p>" +
                        "</br>" +
                        "<p class='textInfo'>Temp（一些临时的碎碎念）</p>" +
                        "<p class='textInfo'>这个类似日记的东西，我其实不应该放在动作指令列表的，但是文件列表暂时被我的简历占了，那么就临时放这吧（也有点希望我自己坚持下去的意思）</p>" +
                        "<p class='textInfo'>而且设计还有待优化啊</p>";
                result = ResultParam.normalInfo(diary,500);
                break;
        }
        return result;
    }
    
    private JSONObject fileCodeDeal(String code){
        JSONObject result = new JSONObject();
        //返回对应指令文件或文件地址
        //暂时代码写死信息
        switch (code){
            case BaseCmdCode.Kevi:
                //先放着代码预设默认值，预防数据库获取不到数据
                String kevi_info = KeviWorkInfo.KeviInfo;
                //mybatis逆向工程方法
                //方便开发，维护
                //但代码繁琐，性能不如自定义
                KeviWorkInfoExample example = new KeviWorkInfoExample();
                KeviWorkInfoExample.Criteria criteria = example.createCriteria();
                criteria.andKEqualTo(BaseCmdCode.Kevi);
                try {
                    List<com.kevi.code.entity.KeviWorkInfo> infoList = keviWorkInfoMapper.selectByExample(example);
                    if (!infoList.isEmpty()){
                        kevi_info = infoList.get(0).getV();
                    }
                }catch (Exception e){
                    logger.error("【简介信息】获取指令kevi对应信息失败", e);
                }
//                result.put("info", kevi_info);
//                result.put("move", 500);
                result = ResultParam.normalInfo(kevi_info, 500);
                break;
            case BaseCmdCode.Study:
                //先放着代码预设默认值，预防数据库获取不到数据
                String study_info = KeviWorkInfo.StudyInfo;
                //自编写mybatis方法
                //开发，维护麻烦
                //性能优异
                try {
                    String info = keviWorkInfoCustomQuery.getInfoByKey(BaseCmdCode.Study);
                    if (!KeviTool.isVoid(info)){
                        study_info = info;
                    }
                }catch (Exception e){
                    logger.error("【简介信息】获取指令study对应信息失败", e);
                }
//                result.put("info", study_info);
                result = ResultParam.normalInfo(study_info);
                break;
            case BaseCmdCode.Worked:
                //先放着代码预设默认值，预防数据库获取不到数据
                String worked_info = KeviWorkInfo.WorkedInfo;
                try {
                    String info = keviWorkInfoCustomQuery.getInfoByKey(BaseCmdCode.Worked);
                    if (!KeviTool.isVoid(info)){
                        worked_info = info;
                    }
                }catch (Exception e){
                    logger.error("【简介信息】获取指令worked对应信息失败", e);
                }
//                result.put("info", worked_info);
                result = ResultParam.normalInfo(worked_info);
                break;
            case BaseCmdCode.Project:
                //先放着代码预设默认值，预防数据库获取不到数据
                String project_info = KeviWorkInfo.ProjectInfo;
                try {
                    String info = keviWorkInfoCustomQuery.getInfoByKey(BaseCmdCode.Project);
                    if (!KeviTool.isVoid(info)){
                        project_info = info;
                    }
                }catch (Exception e){
                    logger.error("【简介信息】获取指令study对应信息失败", e);
                }
//                result.put("info", project_info);
                result = ResultParam.normalInfo(project_info);
                break;
            case BaseCmdCode.GetThisProjectInfo:
                String msg = "you can look or get this project source code in github: <a href='https://github.com/Kevi0128/WhoIsKevi' target='_blank'>https://github.com/Kevi0128/WhoIsKevi</a>";
//                result.put("info", msg);
                result = ResultParam.normalInfo(msg);
                break;
        }
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
//        result.put("info", img);
//        result.put("move", 2134);
        result = ResultParam.normalInfo(img, 2134);
        return result;
    }

}