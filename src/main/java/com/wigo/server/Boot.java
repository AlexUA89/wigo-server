package com.wigo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by akhomutenko on 8/31/2016.
 */
@SpringBootApplication
public class Boot {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Boot.class, args);

        System.out.println("после запуска этого просто зайди на localhost:8080");
    }


}
