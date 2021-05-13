package com.raymon.taxguide.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZIPUtil
{
  private static final int BUFFER_SIZE = 2048;
  
  public static String decompress(String encdata, boolean codebyB64)
    throws IOException
  {
    if (encdata == null) {
      return null;
    }
    if (codebyB64) {
      return new String(decompress(Base64.decode(encdata.toCharArray())));
    }
    return new String(decompress(encdata.getBytes()));
  }
  
  public static String compress(String data, boolean codebyB64)
    throws IOException
  {
    if (data == null) {
      return null;
    }
    if (codebyB64) {
      return new String(Base64.encode(compress(data.getBytes())));
    }
    return new String(compress(data.getBytes()));
  }
  
  public static byte[] compress(byte[] data)
    throws IOException
  {
    ByteArrayOutputStream byteArrayOutputStream = null;
    DeflaterOutputStream deflaterOutputStream = null;
    try
    {
      byteArrayOutputStream = new ByteArrayOutputStream();
      deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
      
      deflaterOutputStream.write(data);
      deflaterOutputStream.close();
      return byteArrayOutputStream.toByteArray();
    }
    finally
    {
      if (deflaterOutputStream != null) {
        deflaterOutputStream.close();
      }
      if (byteArrayOutputStream != null) {
        byteArrayOutputStream.close();
      }
    }
  }
  
  public static byte[] decompress(byte[] encdata)
    throws IOException
  {
    if (encdata == null) {
      return null;
    }
    InputStream inputStream = null;
    InflaterInputStream inflaterInputStream = null;
    ByteArrayOutputStream byteArrayOutputStream = null;
    try
    {
      inputStream = new ByteArrayInputStream(encdata);
      inflaterInputStream = new InflaterInputStream(inputStream);
      byteArrayOutputStream = new ByteArrayOutputStream();
      
      byte[] data = new byte['?'];
      int count;
      while ((count = inflaterInputStream.read(data, 0, 2048)) != -1) {
        byteArrayOutputStream.write(data, 0, count);
      }
      return byteArrayOutputStream.toByteArray();
    }
    finally
    {
      if (inputStream != null) {
        inputStream.close();
      }
      if (inflaterInputStream != null) {
        inflaterInputStream.close();
      }
      if (byteArrayOutputStream != null) {
        byteArrayOutputStream.close();
      }
    }
  }
}
