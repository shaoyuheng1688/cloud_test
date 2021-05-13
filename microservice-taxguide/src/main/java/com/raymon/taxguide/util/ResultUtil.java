package com.raymon.taxguide.util;


import com.raymon.taxguide.vo.GsonObjResult;
import com.raymon.taxguide.web.exception.ApplicationException;

public class ResultUtil {

    public static  <T> T getResultData(GsonObjResult<T> resultData){
        if(null == resultData){
            throw new ApplicationException("对象为空！");
        }
        if(1 == resultData.getCode()){
            throw new ApplicationException(resultData.getMessage());
        }
        if(null == resultData.getData()){
            throw new ApplicationException("没返回结果为空！");
        }
        return resultData.getData();
    }
}
