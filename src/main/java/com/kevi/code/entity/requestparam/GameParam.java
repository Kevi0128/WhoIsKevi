package com.kevi.code.entity.requestparam;

import com.alibaba.fastjson.JSONObject;
import com.kevi.code.entity.customexception.ErrorMoreInfo;
import com.kevi.code.entity.customexception.RequestValidationFailedException;
import com.kevi.code.utils.KeviTool;
import lombok.Data;

@Data
public class GameParam extends BaseParam {

    //游戏名
    private String name;
    //游戏介绍
    private String about;

    @Override
    public void cleanParamSpace() {
        this.name = this.name.trim();
        this.about = this.about.trim();
    }

    public boolean checkKeyParam() throws Exception {
        if (KeviTool.isVoid(this.name)){
            throw new RequestValidationFailedException(ErrorMoreInfo.REQUEST_KEY_PARAM_EMPTY.getMoreInfo());
        }
        //参数检测合格
        return true;
    }

    public boolean checkEditParam() throws Exception{
        if (KeviTool.isVoid(this.name, this.about)){
            throw new RequestValidationFailedException(ErrorMoreInfo.REQUEST_PARAM_EMPTY.getMoreInfo());
        }
        //参数检测合格
        return true;
    }

}
