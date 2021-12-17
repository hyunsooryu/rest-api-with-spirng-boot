package me.hyunsoo.product.config;

import io.swagger.annotations.Api;
import io.swagger.models.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 설정 파일
 * spring boot 2.6.x 대에서는 Swagger 안되네;;
 *
 *
 * http://localhost:8080/v2/api-docs
 * http://localhost:8080/swagger-ui/index.html
 *
 * Swagger 다큐멘테이션 작성 방법
 *
 * curl -X GET "http://localhost:8080/users" -H "accept: application/json"
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
