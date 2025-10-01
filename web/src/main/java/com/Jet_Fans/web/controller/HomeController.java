package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/jet-fans/home")
    public String jetFansHome() {
        return "home";
    }

    @GetMapping("/home/shop-now")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "shop-now";
    }

    @GetMapping("/jet-fans/contact-us")
    public String getContactPage() {
        return "contact-us";
    }

}
