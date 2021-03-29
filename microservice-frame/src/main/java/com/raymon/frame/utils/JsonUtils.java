package com.raymon.frame.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.raymon.frame.pojo.JsonData;

import java.util.List;

public class JsonUtils {

    public static <T> T jsonStrToObject(String jsonStr, Class<T> tClass) {
        T t = null;
        try {
            JsonData<T> obj = JSON.parseObject(jsonStr, new TypeReference<JsonData<T>>() {
            });
            t = JSONObject.toJavaObject((JSONObject) obj.getData(), tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    public static <T> List<T> jsonStrToList(String jsonStr, Class<T> tClass) {
        List<T> list = null;
        try {
            JsonData<T> obj = JSON.parseObject(jsonStr, new TypeReference<JsonData<T>>() {
            });
            JSONArray jsonArray = (JSONArray) obj.getData();
            list = jsonArray.toJavaList(tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
