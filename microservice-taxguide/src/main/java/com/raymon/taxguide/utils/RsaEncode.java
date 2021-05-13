package com.raymon.taxguide.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class RsaEncode {

    //加密算法
    public static final String KEY_ALGORITHM = "RSA";
    //位数
    public static final int KEY_SIZE = 512;

    public static void main(String[] args) throws Exception {
        //待加密的密文
        //String str = "{'Data':{'account':{'accId':1,'accName':'超级管理员','muId':0,'foreignUid':0,'jobId':0}";
        String str = "{'sfzh':'440106199606085632','cardType':'201'}";

        //公钥
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAK+0iLErzypA548V7Yr0ebdv1+6e4Yex5otdc4IFuAyoeuLpDoKAo8PQIp6MtJ4Q8adA7H8EOlopyZ3Eua2M7dcCAwEAAQ==";
        byte[] publicKeyCode = Base64.decodeBase64(publicKey);

        //加密
        byte[] code = encryptByPublicKey(str.getBytes(), publicKeyCode);
        System.out.println("密文：" + Base64.encodeBase64String(code));

    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }


}
