package me.hyunsoo.product.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppRunner implements ApplicationRunner {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=======system log========");
        System.out.println(serverPort);

    }
}
