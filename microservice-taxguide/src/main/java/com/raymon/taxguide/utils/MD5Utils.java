package com.raymon.taxguide.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class MD5Utils
{
  public static final String CHARSET_DEFAULT = "GB18030";
  public static final String ALGORITHM_MD = "MD5";

  
  public static String toHexString(String prefix, byte[] b)
   {
     if (b != null)
     {
       int len = b.length;
       StringBuffer sb;
       if (prefix != null) {
         sb = new StringBuffer(prefix.length() + len * 2);
         sb.append(prefix);
       } else {
         sb = new StringBuffer(len * 2);
       }
  
       for (int i = 0; i < len; i++) {
         String hex = Integer.toHexString(0xFF & b[i]);
         if (hex.length() < 2) {
           sb.append('0');
         }
         sb.append(hex);
       }
       return sb.toString();
     }
     return null;
   }
  
   public static String toHexString(byte[] b)
   {
     return toHexString(null, b);
   }
  

  
  
  public static byte[] getMD5(byte[] input)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      return digest.digest(input); } catch (NoSuchAlgorithmException e) {
    }
	return input;

  }

  public static String getMD5Hex(byte[] input)
  {
	return null;

  }

  public static byte[] getMD5(String input, String charsetName)
  {
    try
    {
      return getMD5(input.getBytes(charsetName)); } catch (UnsupportedEncodingException e) {
    }
	return null;

  }

  public static String getMD5Hex(String input, String charsetName)
  {
    return toHexString(getMD5(input, charsetName));
  }

  public static byte[] getMD5(String input)
  {
    return getMD5(input, "GB18030");
  }

  public static String getMD5Hex(String input)
  {
    return getMD5Hex(input, "GB18030");
  }

  public static String passwordMD5(String userName, String password, Date pwdDate)
  {
	  if(pwdDate!=null){
		  SimpleDateFormat fmtDate = new SimpleDateFormat(
				  "yyyy-MM-dd HH:mm:ss.SSS");
		  String dateStr = fmtDate.format(pwdDate);
		  StringBuffer sb = new StringBuffer(60);
		  sb.append(userName).append(dateStr).append(password);
		  return getMD5Hex(sb.toString());
	  }else{
		  StringBuffer sb = new StringBuffer(60);
		  sb.append(userName).append("raymon123").append(password);
		  return getMD5Hex(sb.toString());
	  }
  }
  
}
