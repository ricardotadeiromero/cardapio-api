package com.example.cardapio_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("hello")
public class HelloWorldController {

    @GetMapping("/world")
    public String helloWorld() {

        return "Hello World";
    }

}
