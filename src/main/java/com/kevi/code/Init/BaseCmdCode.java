package com.kevi.code.Init;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作指令集合
 */
public class BaseCmdCode {

    public static List<String> CodeList = null;             //指令列表
    public static List<String> HelpList = null;             //帮助列表（可用指令列表）
    private static List<String> ActionCodeList = null;      //动作指令列表
    private static List<String> FileCodeList = null;        //文件指令列表
    private static List<String> BonusSceneCodeList = null;  //彩蛋指令列表

    /********************************* 实际会使用的指令 *************************************/
    //动作指令
    public static final String Help = "help";                               //帮助
    //文件指令
    public static final String Kevi = "kevi";                               //个人信息
//    public static final String Power = "power";                             //个人能力
    public static final String Study = "study";                             //学业
    public static final String Worked = "worked";                           //过去的工作
    public static final String Project = "project";                         //过去的项目
    public static final String GetThisProjectInfo = "getThisProjectInfo";   //获取本项目信息
    //彩蛋指令
    public static final String CocaCola = "CocaCole";                       //可口可乐
    public static final String PepsiCola = "PepsiCole";                     //百事可乐

    /************************************* 指令类别 ****************************************/
    public static final int active_code = 1;           //动作指令
    public static final int file_code = 2;             //文件指令
    public static final int bonus_scene_code = 9;      //彩蛋指令

    /**
     * 装载命令列表
     * @return
     */
    public static synchronized void getInstance(){
        //指令列表装载
        if (CodeList == null || CodeList.isEmpty()){
            CodeList = new ArrayList<>();
            CodeList.add(Help);
            CodeList.add(Kevi);
            CodeList.add(Study);
            CodeList.add(Worked);
            CodeList.add(Project);
            CodeList.add(GetThisProjectInfo);
            //彩蛋指令隐藏一下
//            CodeList.add(CocaCola);
//            CodeList.add(PepsiCola);
        }
        //帮助列表装载
        if (HelpList == null || HelpList.isEmpty()){
            HelpList = new ArrayList<>();
            HelpList.add(Help+"  (帮助)");
            HelpList.add(Kevi+"  (个人信息)");
            HelpList.add(Study+"  (学业)");
            HelpList.add(Worked+"  (工作经历)");
            HelpList.add(Project+"  (项目经历)");
            HelpList.add(GetThisProjectInfo+"  (本项目信息)");
        }
        //动作列表装载
        if (ActionCodeList == null || ActionCodeList.isEmpty()){
            ActionCodeList = new ArrayList<>();
            ActionCodeList.add(Help);
        }
        //文件列表装载
        if (FileCodeList == null || FileCodeList.isEmpty()){
            FileCodeList = new ArrayList<>();
            FileCodeList.add(Kevi);
//            FileCodeList.add(Power);
            FileCodeList.add(Study);
            FileCodeList.add(Worked);
            FileCodeList.add(Project);
            FileCodeList.add(GetThisProjectInfo);
        }
        //彩蛋列表装载
        if (BonusSceneCodeList == null || BonusSceneCodeList.isEmpty()){
            BonusSceneCodeList = new ArrayList<>();
            BonusSceneCodeList.add(CocaCola);
            BonusSceneCodeList.add(PepsiCola);
        }
    }

    public static boolean checkCode(String code){
        //以防万一，先初始化一次
        getInstance();
        if (CodeList.contains(code) || BonusSceneCodeList.contains(code)){
            return true;
        }
        return false;
    }

    public static int checkCodeType(String code){
        //以防万一，先初始化一次
        getInstance();
        if (ActionCodeList.contains(code)){
            return active_code;
        }
        if (FileCodeList.contains(code)){
            return file_code;
        }
        if (BonusSceneCodeList.contains(code)){
            return bonus_scene_code;
        }
        //为判断出指令类型
        return -1;
    }

}
