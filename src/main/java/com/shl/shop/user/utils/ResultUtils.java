package com.shl.shop.user.utils;

import com.shl.shop.user.enums.ResultEnum;
import com.shl.shop.user.result.Result;

public class ResultUtils {

    public static Result wrapResult(ResultEnum resultEnum, Object data){
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getmsg());
        result.setData(data);
        return result;
    }
}
