package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductService productService;

    public void addProductToCart(Long userId, Long productId) {
        // Get or create the cart
        Cart cart = cartRepo.findByUserId(userId);
        if (cart == null) {
            cart = createCartForUser(userId);
        }

        // Get the product
        var product = productService.getById(productId);

        // Check if the product already exists in cart
        var existingItem = cart.getCartItem().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            var newItem = new com.Jet_Fans.web.entity.CartItem();
            newItem.setProduct(product);
            newItem.setCart(cart);
            newItem.setQuantity(1);
            cart.getCartItem().add(newItem);
        }

        // Update total price
        double total = cart.getCartItem().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        cart.setTotalPrice(total);
        cartRepo.save(cart);
    }

    public Cart createCartForUser(Long userId) {
        Cart cart = new Cart();
        User user = new User();
        user.setId(userId);
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        return cartRepo.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepo.findByUserId(userId);

        if (cart != null) {
            cart.getCartItem().clear();
            cart.setTotalPrice(0.0);
            cartRepo.save(cart);
        }

    }
}
