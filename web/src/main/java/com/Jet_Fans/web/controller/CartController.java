package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.CartItem;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.service.CartItemService;
import com.Jet_Fans.web.service.CartService;
import com.Jet_Fans.web.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        return "redirect:/home/shop-now";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("cartItems", List.of());
            model.addAttribute("totalPrice", 0.0);
            return "fragments :: cartFragment";
        }

        Cart cart = cartService.getCartByUserId(user.getId());

        if (cart != null && cart.getCartItem() != null && !cart.getCartItem().isEmpty()) {
            model.addAttribute("cartItems", cart.getCartItem());
            double total = cart.getCartItem()
                    .stream()
                    .mapToDouble(CartItem::getItemTotalPrice)
                    .sum();
            model.addAttribute("totalPrice", total);
        } else {
            model.addAttribute("cartItems", List.of());
            model.addAttribute("totalPrice", 0.0);
        }

        return "fragments :: cartFragment";
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCartItem(@RequestParam Long itemId, @RequestParam int quantity, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/user/login";
        }

        try {
            if (quantity <= 0) {
                cartItemService.deleteCartItem(itemId);
            } else {
                cartItemService.updateQuantity(itemId, quantity);
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
