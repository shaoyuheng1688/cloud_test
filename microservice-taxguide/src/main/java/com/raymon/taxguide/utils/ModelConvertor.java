package com.raymon.taxguide.utils;



import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pie
 */
public class ModelConvertor {

    public static Object convertObject(Object sourceObject,Class<?> targetClass){
        try {
            Object result = targetClass.newInstance();
            Field[] sourceFields = sourceObject.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();
            for (Field sourceField : sourceFields) {
            	for(Field targetField : targetFields){
            		if(targetField.getName().equals(sourceField.getName())
            				&& targetField.getType() == sourceField.getType()){
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(sourceField.getName(), targetClass);
                        Method method = propertyDescriptor.getWriteMethod();
                        if (null != method) {
                            sourceField.setAccessible(true);
                            method.invoke(result, new Object[]{sourceField.get(sourceObject)});
                        }
            		}
            	}
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //TODO
    public static List<Object> batchConvertObjects(List<?> sourceObjectList, Class<?> targetClass){
        if(null != sourceObjectList && 0 < sourceObjectList.size()){
            List<Object> resultList = new ArrayList<Object>();
            for(Object sourceObject : sourceObjectList){
                Object item = convertObject(sourceObject,targetClass);
                if(null != item){
                    resultList.add(item);
                }
            }
            return resultList;
        }
        return null;
    }

}
