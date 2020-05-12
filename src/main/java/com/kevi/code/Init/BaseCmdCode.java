package com.kevi.code.Init;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作指令集合
 */
public class BaseCmdCode {

    public static List<String> CodeList = null;
    private static List<String> ActionCodeList = null;
    private static List<String> FileCodeList = null;
    private static List<String> BonusSceneCodeList = null;

    /********************************* 实际会使用的指令 *************************************/
    //动作指令
    public static final String Help = "help";              //帮助
    //彩蛋指令
    public static final String CocaCola = "CocaCole";      //可口可乐
    public static final String PepsiCola = "PepsiCole";    //百事可乐

    /************************************* 指令类别 ****************************************/
    public static final int active_code = 1;           //动作指令
    public static final int file_code = 2;             //文件指令
    public static final int bonus_scene_code = 9;      //彩蛋指令

    /**
     * 装载命令列表
     * @return
     */
    public static synchronized void getInstance(){
        if (CodeList == null || CodeList.isEmpty()){
            CodeList = new ArrayList<>();
            CodeList.add(Help);
            CodeList.add(CocaCola);
            CodeList.add(PepsiCola);
        }
        if (ActionCodeList == null || ActionCodeList.isEmpty()){
            ActionCodeList = new ArrayList<>();
            ActionCodeList.add(Help);
        }
        if (FileCodeList == null || FileCodeList.isEmpty()){
            FileCodeList = new ArrayList<>();
        }
        if (BonusSceneCodeList == null || BonusSceneCodeList.isEmpty()){
            BonusSceneCodeList = new ArrayList<>();
            BonusSceneCodeList.add(CocaCola);
            BonusSceneCodeList.add(PepsiCola);
        }
    }

    public static boolean checkCode(String code){
        //判断一下有没有初始化命令列表
        if (CodeList == null || CodeList.isEmpty()){
            getInstance();
        }
        return CodeList.contains(code);
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
