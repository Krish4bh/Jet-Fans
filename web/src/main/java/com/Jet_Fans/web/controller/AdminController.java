package com.Jet_Fans.web.controller;


import com.Jet_Fans.web.entity.Admin;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.service.AdminService;
import com.Jet_Fans.web.service.ProductService;
import com.Jet_Fans.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("adminLogin", new Admin());
        return "admin-login";
    }

    @PostMapping("/admin/authentication")
    public String verifyCredentials(@RequestParam String email, @RequestParam String password, Model model) {

        if (adminService.verifyAdmin(email, password)) {
            return "redirect:/admin/admin-home";
        }

        model.addAttribute("loginError", "Invalid email or password");
        return "admin-login";
    }

    @GetMapping("/admin/admin-home")
    public String adminHome(Model model) {
        List<Admin> admins = adminService.getAll();
        List<User> users = userService.getAll();
        List<Product> products = productService.getAll();

        model.addAttribute("admins", admins);
        model.addAttribute("users", users);
        model.addAttribute("products", products);

        return "admin-home";
    }

    @PostMapping("/add/admin")
    public String addAdmin(Admin admin) {
        adminService.createAdmin(admin);
        System.out.println("************************************************************************");
        System.out.println("Admin role has been successfully assigned to " + admin.getName());
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }

    @PostMapping("/update/admin")
    public String editAdmin(Admin admin) {
        adminService.updateAdmin(admin);
        System.out.println("************************************************************************");
        System.out.println("Admin details for " + admin.getName() + " has been successfully updated.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }

    @PostMapping("/update/user")
    public String editUser(User user) {
        userService.updateUser(user);
        System.out.println("************************************************************************");
        System.out.println("User details for " + user.getName() + " has been successfully updated.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }

    @PostMapping("/delete/admin/{id}")
    public String removeAdmin(@PathVariable Long id) {
        adminService.deleteById(id);
        System.out.println("************************************************************************");
        System.out.println("Admin with id " + id + " has been successfully removed.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }
}

