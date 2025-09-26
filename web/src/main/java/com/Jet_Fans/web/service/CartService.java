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
