package com.raymon.frame.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaDecode {

    //加密算法
    public static final String KEY_ALGORITHM = "RSA";

    public static void main(String[] args) throws Exception {
        //已加密的密文
        String str = "PgoIs89WaqCEyPfhBnlmzV4dZkRr02xOb2FCxeyrDNtE1CzA9m+Y7CwuEAi2Jqkk1JG/tooyUpmuC2B3Fl0Heg==";
        byte[] code = Base64.decodeBase64(str);

        //私钥
        String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAr7SIsSvPKkDnjxXtivR5t2/X7p7hh7Hmi11zggW4DKh64ukOgoCjw9Ainoy0nhDxp0DsfwQ6WinJncS5rYzt1wIDAQABAkBJAauWakyfEu7kPklAZoq4lKlVRgER3/KaT88x5GxwHVO3euVnqmCG15hH00NhOFFBMfHW5zfvPqITYrfU1BxRAiEA77roAnJm1m1hakrC2J9trGezdirx4SsEsK/p8B7xTv8CIQC7oUDJjfk3XFEAjTbkC/14F/24RzhK4+TA6pNbKi+5KQIgMqXUWp/85hSZ3jEJVWbpbuFB4xdTnvTNut6PmDGRXdUCIG7bUk0DnghQeedqh9DrKKIJArYbFi5hQWmd7txBK5spAiEAxtu2AeSRwMpCyV0D2C4iL6gHZ/5bWWV0PNMiJfODdCg=";
        byte[] privateKeyCode = Base64.decodeBase64(privateKey);

        //公钥
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAK+0iLErzypA548V7Yr0ebdv1+6e4Yex5otdc4IFuAyoeuLpDoKAo8PQIp6MtJ4Q8adA7H8EOlopyZ3Eua2M7dcCAwEAAQ==";
        byte[] publicKeyCode = Base64.decodeBase64(publicKey);

        //解密
        //byte[] decode = decryptByPrivateKey(code, privateKeyCode);
        byte[] decode = decryptByPublicKey(code, privateKeyCode);
        System.out.println("解密后: " + new String(decode));


    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }


}
