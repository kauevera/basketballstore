package com.basketballstore.basketballstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/test")
    public String saudacao() {
        return "programa ok";
    }    
}