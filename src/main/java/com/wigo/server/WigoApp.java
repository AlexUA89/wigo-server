package com.wigo.server;

import com.wigo.server.config.WigoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@EnableAutoConfiguration
@Import({WigoConfiguration.class})
public class WigoApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WigoApp.class, args);
        System.out.println("Wigo server started on localhost:8080");
        System.out.println("Visit /swagger-ui.html fro more details");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(appClass);
    }

    private static Class<WigoApp> appClass = WigoApp.class;

}
