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
