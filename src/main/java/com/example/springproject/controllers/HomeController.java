package com.example.springproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/protected")
    public String protectedPage() {
        return "protected";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
