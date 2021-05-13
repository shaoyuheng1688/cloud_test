package com.raymon.taxguide.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 *
 * @author 张梦想
 * @datetime 2019年12月10日 下午9:30:35
 * @editnote
 * 
 */
public class Base64Util {
	/**
	 * 图片转为Base64字节码
	 * 
	 * @param path 图片路径
	 * @return 返回base64字节码
	 */
	public static String imageToBase64(String path) {
		if (null == path) {
			return null;
		}
		byte[] data = null;
		String[] strArray = path.split("\\.");
		int suffixIndex = strArray.length - 1;
		String suffix = strArray[suffixIndex];
		if (suffix.equals("png")) {
			suffix = "data:image/png;base64,";
		} else if (suffix.equals("jpg")) {
			suffix = "data:image/jpg;base64,";
		} else if (suffix.equals("bmp")) {
			suffix = "data:image/bmp;base64,";
		} else if (suffix.equals("jpeg")) {
			suffix = "data:image/jpeg;base64,";
		}

		try {
			InputStream in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Base64 base64 = new Base64();
		return suffix + base64.encodeToString(data);
	}

	/**
	 * Base64字节码转图片
	 * 
	 * @param base64Str 字节码存储路径
	 * @param path      文件存储路径
	 * @return 返回true或者false
	 */
	public static boolean base64ToImage(String base64Str, String path, String fileName) {
		
		if (base64Str == null) {
			return false;
		}
		if (base64Str.contains("base64,")) {
			base64Str=base64Str.split("base64,")[1];
		}

		byte[] bytes = Base64.decodeBase64(base64Str);
		for (int i = 0; i < bytes.length; ++i) {
			if (bytes[i] < 0) {
				bytes[i] += 256;
			}
		}

		File img = new File(path + "/" + fileName);
		if (!img.getParentFile().exists()) {
			img.getParentFile().mkdirs();
		}

		try {
			img.createNewFile();
			OutputStream out = new FileOutputStream(path + "/" + fileName);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
}
