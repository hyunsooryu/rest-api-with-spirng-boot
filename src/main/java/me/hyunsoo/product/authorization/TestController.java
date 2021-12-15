package me.hyunsoo.product.authorization;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {
    @GetMapping("/set-cookie")
    public String setCookie(HttpServletResponse response){
        Cookie loginCookie = new Cookie("loginYn","Y");
        loginCookie.setMaxAge(60 * 60 * 24 * 365);
        loginCookie.setHttpOnly(true); //script에서 쿠키값 사용할 수 없고,
       // loginCookie.setSecure(true); //https 에서만 쿠키를 보냄 - 네트워크 단에서 꺼내볼 수 없음.암호화 되어서
        response.addCookie(loginCookie);
        return loginCookie.getValue();
    }

    @GetMapping("/info")
    public String test(){
       return "success";
    }
}
