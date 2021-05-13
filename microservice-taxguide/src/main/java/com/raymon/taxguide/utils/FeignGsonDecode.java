package com.raymon.taxguide.utils;

import com.raymon.taxguide.vo.GsonObjResult;
import com.raymon.taxguide.web.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignGsonDecode {

    private static Logger log = LoggerFactory.getLogger(FeignGsonDecode.class);

    public static <T> T getFeignData(GsonObjResult gsonObjResult) {
        if (gsonObjResult != null && gsonObjResult.getCode() == 0) {
            return (T) gsonObjResult.getData();
        } else {
            String message = "远程调用异常，请稍后再试！";
            if (gsonObjResult != null && gsonObjResult.getMessage() != null) {
                message = gsonObjResult.getMessage();
            }
            log.error(message);
            throw new ApplicationException(message);
        }
    }

    public static <T> T getFeignData(GsonObjResult gsonObjResult, boolean ignored) {
        if (gsonObjResult != null && gsonObjResult.getCode() == 0) {
            return (T) gsonObjResult.getData();
        } else {
            String message = "远程调用异常，请稍后再试！";
            if (gsonObjResult != null && gsonObjResult.getMessage() != null) {
                message = gsonObjResult.getMessage();
            }
            log.error(message);
            if(!ignored){
                throw new ApplicationException(message);
            }
        }
        return null;
    }
}
