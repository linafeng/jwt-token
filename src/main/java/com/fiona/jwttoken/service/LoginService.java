package com.fiona.jwttoken.service;

import com.alibaba.fastjson.JSON;
import com.fiona.jwttoken.domain.BaseResponse;
import com.fiona.jwttoken.security.TokenService;
import com.fiona.jwttoken.security.TokenSubject;
import com.fiona.jwttoken.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    TokenService tokenService;

    public BaseResponse<String> login(String username, String password) {
        TokenSubject subject = new TokenSubject()
                .setUid("1")
                .setUname(username);
        String cacheId = UUID.randomUUID().toString();
        String token = JwtUtil.createJWT(JSON.toJSONString(subject), cacheId, 10000000);
        tokenService.cacheToken(cacheId, token);
        return new BaseResponse<String>().withContent(token);
    }
}
