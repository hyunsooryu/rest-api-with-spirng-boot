package me.hyunsoo.product.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security 설정
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationManagerBuilder auth;

    public SecurityConfig(AuthenticationManagerBuilder auth) throws Exception {
        this.auth = auth;
        this.auth.inMemoryAuthentication()
                .withUser("hyunsoo")
                .password("{noop}test1234")
                .roles("USER");
    }


}
