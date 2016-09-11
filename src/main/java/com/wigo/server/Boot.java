package com.wigo.server;

import com.wigo.server.config.WigoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import({WigoConfiguration.class})
public class Boot {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Boot.class, args);
        System.out.println("Wigo server started on localhost:8080");
        System.out.println("Visit /swagger-ui.html fro more details");
    }

}
