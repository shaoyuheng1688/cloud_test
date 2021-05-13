package com.raymon.taxguide.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Convert {
	
	 public static String getBase64FromInputStream(InputStream in) {
	        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        byte[] data = null;
	        // 读取图片字节数组
	        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
	        try {
	            byte[] buff = new byte[100];
	            int rc = 0;
	            while ((rc = in.read(buff, 0, 100)) > 0) {
	                swapStream.write(buff, 0, rc);
	            }
	            data = swapStream.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                    swapStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return new String(Base64.encodeBase64(data));
	    }

	
}
