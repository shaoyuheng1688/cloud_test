package com.raymon.taxguide.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

@Configuration
@Slf4j
public class FeignResponseDecoder implements Decoder {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();;

    @Override
    public Object decode(Response response, Type type) {
        Object obj = null;
        try {
            String resultStr = Util.toString(response.body().asReader(Util.UTF_8));
            obj = gson.fromJson(resultStr, type);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return obj;
    }
}
