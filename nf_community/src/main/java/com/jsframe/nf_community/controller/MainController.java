package com.jsframe.nf_community.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log
public class MainController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/login.html")
    public String login(){
        return "login";
    }



}
