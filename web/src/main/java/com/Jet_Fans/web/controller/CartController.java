package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.service.CartItemService;
import com.Jet_Fans.web.service.CartService;
import com.Jet_Fans.web.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add/cart")
    public String addToCart(@RequestParam("id") Long productId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {


        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("loginMessage", "Please login to add items to your cart.");
            return "redirect:/user/login?redirect=/shop-now";
        }

        Cart cart = cartService.getCartByUserId(loggedInUser.getId());
        if (cart == null) {
            cart = cartService.createCartForUser(loggedInUser.getId());
        }

        Product product = productService.getById(productId);
        cartItemService.createCartItem(cart, product, 1);

        redirectAttributes.addFlashAttribute("success", "Product added to your cart!");
        return "redirect:/shop-now";
    }
}
