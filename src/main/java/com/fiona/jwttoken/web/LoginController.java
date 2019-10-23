package com.fiona.jwttoken.web;

import com.fiona.jwttoken.domain.BaseResponse;
import com.fiona.jwttoken.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 不拦截
     *
     * @param name
     * @return
     */
    @GetMapping("/login")
    public BaseResponse<String> login(@RequestParam("name") String name) {
        System.out.println("登录用户:" + name);
        return loginService.login(name, null);
    }

    /**
     * 会被拦截检验token
     *
     * @return
     */
    @GetMapping("/login2")
    public BaseResponse<String> login2() {
        System.out.println("测试");
        return new BaseResponse<String>();
    }
}
