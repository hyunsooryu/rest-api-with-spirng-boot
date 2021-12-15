package me.hyunsoo.product.helloworld;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * Spring Boot 동작 원리
 * application.yml or application.properties
 * logging.level.org.springframework=debug
 *
 * HttpMessageConvertersAutoConfiguration -> HttpMessageConverter 관련 자동 설멍 Bean 등록
 * Spring4 부터 @RestController 지원
 * @Controller + @ResponseBody
 *
 * view를 갖지 않은 Rest Data(JSON/XML)을 반환하게 됩니다.
 *
 */

@RestController
public class HelloWorldController {

    @GetMapping(path= "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean/{name}")
    public HelloWorldBean helloWorldBean(String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }


}
