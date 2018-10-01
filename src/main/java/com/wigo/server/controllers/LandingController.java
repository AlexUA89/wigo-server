package com.wigo.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

}
