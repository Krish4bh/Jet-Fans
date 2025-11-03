package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.CartItem;
import com.Jet_Fans.web.entity.Order;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.service.CartItemService;
import com.Jet_Fans.web.service.CartService;
import com.Jet_Fans.web.service.OrderService;
import com.Jet_Fans.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Show checkout page. Uses the same checkout-page.html you already provided.
     */
    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");



        // If user not logged in, redirect to login (you already use this pattern elsewhere)
        if (user == null) {
            return "redirect:/user/login?redirect=/checkout";
        }

        Cart cart = cartService.getCartByUserId(user.getId());
        if (cart == null) {
            model.addAttribute("cartItems", List.of());
            model.addAttribute("totalPrice", 0.0);
        } else {
            List<CartItem> items = cartItemService.findByCartId(cart.getId());
            model.addAttribute("cartItems", items);
            model.addAttribute("totalPrice", cart.getTotalPrice());
        }

        model.addAttribute("order", new Order());
        model.addAttribute("user", user);
        // orderConfirmed and orderId are used to show the modal; default false
        model.addAttribute("orderConfirmed", false);
        model.addAttribute("orderId", null);

        return "checkout-page";
    }

    @PostMapping("/confirm-order")
    public String confirmOrder(@RequestParam(required = false) String address,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/user/login?redirect=/checkout";
        }

        try {
            Order saved = orderService.placeOrder(user, address);
            model.addAttribute("orderConfirmed", true);
            model.addAttribute("orderId", saved.getId());

            // After order placed cart is cleared - show empty cart on page
            model.addAttribute("cartItems", List.of());
            model.addAttribute("totalPrice", 0.0);

        } catch (IllegalStateException | IllegalArgumentException ex) {
            // If cart empty or other problem, show message on checkout page
            model.addAttribute("error", ex.getMessage());
            Cart cart = cartService.getCartByUserId(user.getId());
            if (cart != null) {
                model.addAttribute("cartItems", cartItemService.findByCartId(cart.getId()));
                model.addAttribute("totalPrice", cart.getTotalPrice());
            } else {
                model.addAttribute("cartItems", List.of());
                model.addAttribute("totalPrice", 0.0);
            }
            model.addAttribute("orderConfirmed", false);
            model.addAttribute("orderId", null);
            return "checkout-page";
        }

        redirectAttributes.addFlashAttribute("orderConfirmed", true);

        model.addAttribute("order", new Order());
        model.addAttribute("user", user);
        return "checkout-page";
    }

}
