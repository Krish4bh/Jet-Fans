package com.Jet_Fans.web.controller;


import com.Jet_Fans.web.entity.Admin;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.service.AdminService;
import com.Jet_Fans.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("adminLogin", new Admin());
        return "admin-login";
    }

    @PostMapping("/admin/verify-admin-credentials")
    public String verifyCredentials(@ModelAttribute("admin") Admin admin, Model model) {

        String adminEmail = admin.getEmail();
        String adminPassword = admin.getPassword();

        if (adminService.verifyAdmin(adminEmail, adminPassword)) {
            return "redirect:/admin/admin-home";
        }

        model.addAttribute("error", "Invalid email or password");
        return "admin-login";
    }

    @GetMapping("/admin/admin-home")
    public String adminHome(Model model) {
        List<Admin> admins = adminService.getAll();
        List<User> users = userService.getAll();

        model.addAttribute("admins", admins);
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

    @PostMapping("/delete/admin/{id}")
    public String removeAdmin(@PathVariable Long id) {
        adminService.deleteById(id);
        System.out.println("************************************************************************");
        System.out.println("Admin with id " + id + " has been successfully removed.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }
}

