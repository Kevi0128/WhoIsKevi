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

    //todo 返回JSON包装一下
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
                String msg = "<div class='inline'>this project not finish, just some code can use:&nbsp;</div><div class='info'>";
                String baseCodeList = "";
                for (String c : BaseCmdCode.HelpList){
                    baseCodeList = baseCodeList + c + "</br>";
                }
                msg = msg + baseCodeList + "</div>";
                result.put("info", msg);
                break;
        }
        return result;
    }

    //todo 具体信息从数据库获取
    private JSONObject fileCodeDeal(String code){
        JSONObject result = new JSONObject();
        //返回对应指令文件或文件地址
        //暂时代码写死信息
        switch (code){
            case BaseCmdCode.Kevi:
//                String kevi_info = "<div class='textTop'>你好，我是张栖豪（kevi）</div>" +
//                        "<div>95年生的一名程序猿，这里正如你所见，是我还没完全完成的个人网站，预计之后我会放上一些个人感兴趣的东西，比如一直想写的mud游戏，一些新技术的测试，收集的图片，偶尔的日志博客什么的。</div>" +
//                        "<div>但现在嘛，更多的是承担着我的个人简历的工作。</div>" +
//                        "<div>既然现在是我的个人简历，那么请让我认真的介绍一下我自己：</div>" +
//                        "<div style='text-indent:2em'>我离校工作了3年多，在工作中踩过不少的坑，自然也有了相应的收获，现在的我可以独立负责小型服务器，在中大型服务器的工作中承担应有的职责，依照设计需求开发对应的功能，维护代码，迭代代码。" +
//                        "熟悉spring系列，mybatis，mysql，redis，现在的spring微服务架构我只能说了解过，自学过，写过demo，但很遗憾的未在工作中实际运用过。专注于后端的我也并不擅长html前端，不然也不会取巧的使用" +
//                        "这样的仿dos界面了，而更多的就请先让我暂且藏拙吧。面对未来可能需要的新技术我表示真心的欢迎，不能自我学习的程序猿又怎么成长呢？</div>";
                String kevi_info = "<p class='textInfo'>你好，我是张栖豪（kevi）</p>" +
                        "<p class='textInfo'>95年生的一名程序猿，这里正如你所见，是我还没完全完成的个人网站，预计之后我会放上一些个人感兴趣的东西，比如一直想写的mud游戏，一些新技术的测试，收集的图片，偶尔的日志博客什么的。</p>" +
                        "<p class='textInfo'>但现在嘛，更多的是承担着我的个人简历的工作。</p>" +
                        "<p class='textInfo'>既然现在是我的个人简历，那么请让我认真的介绍一下我自己：</p>" +
                        "<p class='textInfo'>我离校工作了3年多，在工作中踩过不少的坑，自然也有了相应的收获，现在的我可以独立负责小型服务器，在中大型服务器的工作中承担应有的职责，依照设计需求开发对应的功能，维护代码，迭代代码。" +
                        "熟悉spring系列，mybatis，mysql，redis，现在的spring微服务架构我只能说了解过，自学过，写过demo，但很遗憾的未在工作中实际运用过。专注于后端的我也并不擅长html前端，不然也不会取巧的使用" +
                        "这样的仿dos界面了，而更多的就请先让我暂且藏拙吧。面对未来可能需要的新技术我表示真心的欢迎，不能自我学习的程序猿又怎么成长呢？</p>";
                result.put("info", kevi_info);
                result.put("move", 500);
                break;
            case BaseCmdCode.Study:
                String study_info = "<p class='textInfo'>我于2017年6月毕业于重庆电力高等专科学校软件技术专业，是一名专科生</p>";
                result.put("info", study_info);
                break;
            case BaseCmdCode.Worked:
                String worked_info = "<p>2018.06-至今 在重庆掌屏网络科技有限公司担任Java服务器(开发)一职。</p>" +
                        "<p class='textInfo'>在该公司我主要负责公司的自有期权服务器和合作期权app服务器(SSM架构)的开发与维护</p>" +
                        "<p>2017.03-2018.04 在重庆大渡口洋昊网络科技有限公司担任Java服务器(开发)一职</p>" +
                        "<p class='textInfo'>在该公司我主要负责编写SSM架构的服务器管理后台和（netty+spring mvc+mybatis架构）棋牌游戏服务器的游戏逻辑编写</p>" +
                        "<p>2016.11-2017.03 在重庆君胜任网络科技有限公司担任Java管理后台一职</p>" +
                        "<p class='textInfo'>在该公司我主要负责编写SSM架构的服务器管理后台</p>";
                result.put("info", worked_info);
                break;
            case BaseCmdCode.Project:
                String project_info = "<p>2018.10-至今</p>" +
                        "<p>东石/掌屏期权app服务器(重庆掌屏网络科技有限公司)</p>" +
                        "<p class='textInfo'>按照已有app接口标准重新编写app服务器并对接东石软件，提供app需求数据和传输用户委托数据至东石软件，从东石软件获取成交等信息</p>" +
                        "<p class='textInfo'>在编程并维护该app服务器时，理解了期权交易规则，用户实际操作时可能出现的问题，为后续公司自有的期权服务器打下了基础。该合作服务器系统编号到达160+，最高峰时多套系统交易量合计占交易所交易量三分之一</p>" +
                        "<p>2019.08-2020.04</p>" +
                        "<p>掌屏期权服务器(重庆掌屏网络科技有限公司)</p>" +
                        "<p class='textInfo'>开发公司自有期权服务器，判断和处理用户提交的委托数据（控制用户下单频率，避免自成交委托，交易所对冲持仓处理等），通过中间件ice使用同事对接的py接口向券商下单，盘中系统故障时临时处理问题保障系统运行，" +
                        "找到故障出现原因并解决，统一在收盘后更新。在收盘后整理用户委托，成交，持仓数据。</p>" +
                        "<p class='textInfo'>按照公司需求独立设计并完成了该服务器，并在公司运营中随着需求更新/变动/添加，迭代服务器，满足需求</p>" +
                        "<p>2018.07-2018.10</p>" +
                        "<p>中智美汇会员系统(重庆掌屏网络科技有限公司)</p>" +
                        "<p class='textInfo'>接手前任同事的工作，完成和维护该会员系统，该系统为微信商城后端和代理商返佣部分</p>" +
                        "<p>2018.01-2018.02</p>" +
                        "<p>汇友棋牌(重庆大渡口洋昊网络科技有限公司)</p>" +
                        "<p class='textInfo'>棋牌游戏的炸金花和百人金花的服务器实现</p>" +
                        "<p>2017.07-2017.12</p>" +
                        "<p>武汉花游戏/沪闽十三水(重庆大渡口洋昊网络科技有限公司)</p>" +
                        "<p class='textInfo'>棋牌游戏十三水和双扣的武汉/上海规则的服务器实现</p>" +
                        "<p>2017.03-2018-01</p>" +
                        "<p>游戏管理后台(重庆大渡口洋昊网络科技有限公司)</p>" +
                        "<p class='textInfo'>在完成服务器工作之外完成各个游戏的网页管理后台</p>";
                result.put("info", project_info);
                break;
            case BaseCmdCode.GetThisProjectInfo:
                String msg = "you can look or get this project source code in github: https://github.com/Kevi0128/WhoIsKevi";
                result.put("info", msg);
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
        result.put("info", img);
        result.put("move", 2134);
        return result;
    }

}