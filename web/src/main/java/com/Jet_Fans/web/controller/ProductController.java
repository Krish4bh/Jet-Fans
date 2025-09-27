package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/home/shop-now")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "shop-now";
    }

    @PostMapping("/add/product")
    public String addProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/admin/admin-home";
    }

    
}
