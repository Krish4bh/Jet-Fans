package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @PostMapping("/add/product")
    public String addProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        System.out.println("************************************************************************");
        System.out.println("Product with id " + product.getId() + " has been successfully added.");
        System.out.println("************************************************************************");

        return "redirect:/admin/admin-home";
    }

    @PostMapping("/edit/product")
    public String editProduct(@ModelAttribute Product product) {
        productService.updateProduct(product);
        System.out.println("************************************************************************");
        System.out.println("Product with id " + product.getId() + " has been successfully updated.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }

    @PostMapping("/product/delete/{id}")
    public String removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        System.out.println("************************************************************************");
        System.out.println("Product with id " + id + " has been successfully updated.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }
}
