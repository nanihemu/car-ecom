package com.cardealership.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }
    
    @GetMapping("/cars")
    public String cars() {
        return "forward:/cars.html";
    }
    
    @GetMapping("/about")
    public String about() {
        return "forward:/about.html";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "forward:/contact.html";
    }
    
    @GetMapping("/car-details")
    public String carDetails() {
        return "forward:/car-details.html";
    }
}