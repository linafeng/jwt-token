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

    /**
     * 不拦截
     */
    @GetMapping("/test")
    public String test() {
        System.out.println("url/test");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("返回");
        return "ssss";
    }
    /**
     * 不拦截
     */
    @GetMapping("/test2")
    public String test2() {
        System.out.println("url/test2");
        int count=1;
        while (true){
            System.out.println("小混混");
            if(count==0){
                System.out.println("count"+count);
                break;
            }

        }

        System.out.println("返回2");
        return "ssss";
    }
}
