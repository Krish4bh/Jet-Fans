package com.Jet_Fans.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/jet-fans/home")
    public String jetFansHome() {
        return "home";
    }

    @GetMapping("/shop-now")
    public String shopNowPage() {
        return "shop-now";
    }

}
