package com.fiona.jwttoken.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;


/**
 * @author fiona fung
 */
public class JwtUtil {
    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 版本
    public static final String TOKEN_VERSION = "1";
    // 设置发行人
    public static final String ISSUER = "fiona";
    // 设置抽象主题

    //HS256加密
    private static SignatureAlgorithm sa = SignatureAlgorithm.HS256;
    public static Key signingKey = new SecretKeySpec(Base64.decodeBase64(Consts.secretkey), sa.getJcaName());

    public static String createJWT(String subject, String cacheId, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(cacheId)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS256, signingKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /*private static String createJWT(String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS256, signingKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }*/

    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwt).getBody();
        return claims;

    }

    private static void testparseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }

    public static void main(String[] args) {
        /*String jwt = createJWT("fsdas", 10000000);
        System.out.println(jwt);
        Claims claims1 = parseJWT(jwt);
        System.out.println(JSON.toJSONString(claims1));*/
    }
/*
    public static String createJWT(String name, String userId, String role,
                                   String audience, String issuer, long TTLMillis, String                                                                   base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥 就是一个base64加密后的字符串？
        byte[] apiKeySecretBytes =                                                                                                         DatatypeConverter.parseBase64Binary (base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,                                                       signatureAlgorithm. getJcaName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", name);
        jsonObject.put("userLoginName", userId);
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuedAt(now) //创建时间
                .setSubject(jsonObject.toString()) //主题，也差不多是个人的一些信息
                .setIssuer(issuer) //发送谁
                .setAudience(audience) //个人签名
                .signWith(signatureAlgorithm, signingKey); //估计是第三段密钥
        //添加Token过期时间
        if (TTLMillis >= 0) {
            //过期时间
            long expMillis = nowMillis + TTLMillis;
            //现在是什么时间
            Date exp = new Date(expMillis);
            //系统时间之前的token都是不可以被承认的
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }

}*/
}
