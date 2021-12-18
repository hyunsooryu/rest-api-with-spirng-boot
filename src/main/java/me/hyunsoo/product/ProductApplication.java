package me.hyunsoo.product;

import me.hyunsoo.product.authorization.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 *  1. Java Persistence API
 *  자바 ORM 기술에 대한 API 표준 명세
 *  자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스
 *  EntityManager를 통해 CRUD 처리
 *
 *  2. Hibernate
 *  JPA의 구현체, 인터페이스를 직접 구현한 라이브러리
 *  생산성, 유지보수, 비종속성
 *
 *  3. Spring Data JPA
 *  Spring Module
 *  JPA를 추상화환 Repository 인터페이스를 제공
 *
 *  JDBC -> Hibernate -> JPA -> SPRING DATA JPA
 *
 *
 */





@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Configuration
    public static class WebConfig implements WebMvcConfigurer {
        /**
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            InterceptorRegistration reg = registry.addInterceptor(new LoginInterceptor());
            reg.addPathPatterns("/**").excludePathPatterns("/set-cookie");
        }
        **/
    }

    /**
     *
     * 로케일 리졸버
     * @return
     */

    @Bean
    public LocaleResolver localResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

}
