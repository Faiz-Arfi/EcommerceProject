package com.projects.ecommerce.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "Test successful!";
    }
    @GetMapping("/")
    public String home() {
        return "Welcome to Ecommerce Backend! The server is running: to view api documentation, please visit: https://documenter.getpostman.com/view/38381039/2sB2xEAoNg";
    }
}
