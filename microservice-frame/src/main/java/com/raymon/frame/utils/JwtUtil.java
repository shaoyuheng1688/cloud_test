package com.raymon.frame.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

//    private static long expTime = 1;

    public static String createToken(String key ,Map<String,Object> param,long expTime){
        expTime = expTime*1000;
        //1.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts
                .builder()
                .setClaims(param)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        long nowTime = new Date().getTime();
        jwtBuilder.setExpiration(new Date(nowTime+expTime));
        //4.创建token
        String token = jwtBuilder.compact();
        return token;

    }


    /**
     * 解析Claims
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String key,String token) {
        Claims claims = null;
        claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String key,String token) {
        return getClaim(key,token).getExpiration();
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String key,String token) {
        try {
            final Date expiration = getExpiration(key, token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("xm","lzc");
        param.put("age","19");
        String token = createToken("onehee666",param,60);
        System.out.println(token);
        //Thread.sleep(2000);
     /*   Map<String,Object>  map = getClaim("onehee666",token);
        System.out.println(map.get("xm"));
        System.out.println(map.get("age"));*/
        System.out.println(isExpired("onehee666",token));

    }
}
