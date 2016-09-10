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
        return "ssssss";
    }

}
