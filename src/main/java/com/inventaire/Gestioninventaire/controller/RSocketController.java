package com.inventaire.Gestioninventaire.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RSocketController {

    @MessageMapping("greeting")
    public String greet(String name) {
        return "Hello, " + name;
    }
}
