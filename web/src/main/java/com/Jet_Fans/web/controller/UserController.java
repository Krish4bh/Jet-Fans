package com.Jet_Fans.web.controller;


import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    private String userLogin(@RequestParam String email, @RequestParam String password, Model model) {
        if (userService.verifyUser(email, password)) {
            return "redirect:/jet-fans/home";
        }
        model.addAttribute("error", "Invalid email or password");
        return "user-login";
    }

    @PostMapping("/user/register")
    private String userRegister(@ModelAttribute("user") User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/jet-fans/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user-login";
        }
    }

    @GetMapping("/user/profile/{id}")
    private String userProfile(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("userDetails", user);
        return "user-profile";
    }

    @PostMapping("/user/update-user")
    private String editUser(User user) {
        userService.updateUser(user);
        System.out.println("************************************************************************");
        System.out.println(user.getName() + " user details has been successfully updated");
        System.out.println("************************************************************************");
        return "redirect:/user/user-profile";
    }

    @PostMapping("/user/delete/{id}")
    private String removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
        System.out.println("************************************************************************");
        System.out.println("User account with id " + id + " has been successfully deleted.");
        System.out.println("************************************************************************");
        return "redirect:/admin/admin-home";
    }

}
