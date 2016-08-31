package com.wigo.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akhomutenko on 8/31/2016.
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Леха, это все уже готовый сервак, который ты можешь конфигурить, как хочешь. Если тебе хочется изобретать велосипед, то я просто возьму пачку попкорна)";
    }

}
