package com.wigo.server;

import com.wigo.server.config.WigoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Arrays;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@Import({WigoConfiguration.class})
public class WigoApp {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WigoApp.class, args);
        System.out.println("Wigo server started on localhost:8080");
        System.out.println("Visit /swagger-ui.html fro more details");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
//фывфывфы
        };
    }

}
