package com.fiona.jwttoken.security;

import com.fiona.jwttoken.util.AESUtils;
import com.fiona.jwttoken.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${aes.key}")
    public String aesKey;
    @Value("${aes.iv}")
    public String aesIv;

    public boolean isValidToken(String token) {
        System.out.println("验证token-" + token);
        if (StringUtils.isAnyEmpty(token)) {
            return false;
        }
        try {
            Claims claims = JwtUtil.parseJWT(token);
            if (claims.getExpiration().before(new Date())) {
                return false;
            }
            String secretToken = TokenManager.getTokenMap().get(claims.getId());
            String realToken = AESUtils.bulrCbcDecrypt(aesKey, aesIv, secretToken);
            return StringUtils.equals(token, realToken);
        } catch (Exception e) {
            return false;
        }
    }

    public void cacheToken(String cacheID, String token) {
        TokenManager.putToken(cacheID, AESUtils.bulrCbcEncrypt(aesKey, aesIv, token));
    }


}
