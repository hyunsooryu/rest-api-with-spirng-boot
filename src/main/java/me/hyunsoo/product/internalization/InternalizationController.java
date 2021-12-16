package me.hyunsoo.product.internalization;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/international-hello")
public class InternalizationController {

    private final MessageSource messageSource;

    //Locale 정보는 RequestHeader에 Accept-Language를 바라본다.
    @GetMapping(path = "")
    public String hello(Locale locale){
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
