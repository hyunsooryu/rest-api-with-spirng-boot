package me.hyunsoo.product.authorization;

import me.hyunsoo.product.user.exception.NotLoginException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("loginYn".equals(cookie.getName())) {
                    if ("Y".equals(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        throw new NotLoginException("로그인 하지 않았습니다.");
    }
}
