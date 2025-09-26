package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.CartItem;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.repository.CartItemRepo;
import com.Jet_Fans.web.repository.CartRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;

    
    public CartItem createCartItem(Cart cart, Product product, int quantity) {

        CartItem itemFromDb = cart.getCartItem().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (itemFromDb != null) {
            itemFromDb.setQuantity(itemFromDb.getQuantity() + quantity);
            itemFromDb.setItemTotalPrice(itemFromDb.getProduct().getPrice() * itemFromDb.getQuantity());

            cart.setTotalPrice(
                    cart.getCartItem().stream().mapToDouble(CartItem::getItemTotalPrice).sum()
            );

            cartRepo.save(cart);
            return cartItemRepo.save(itemFromDb);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setItemTotalPrice(product.getPrice() * quantity);

            cart.getCartItem().add(newItem);
            cart.setTotalPrice(
                    cart.getCartItem().stream().mapToDouble(CartItem::getItemTotalPrice).sum()
            );
            cartRepo.save(cart);

            return cartItemRepo.save(newItem);
        }
    }

    @Transactional
    public CartItem updateQuantity(Long cartItemId, int quantity) {
        CartItem itemFromDb = cartItemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("No item in cart found..."));

        itemFromDb.setQuantity(quantity);
        itemFromDb.setItemTotalPrice(itemFromDb.getProduct().getPrice() * quantity);

        Cart cart = itemFromDb.getCart();
        cart.setTotalPrice(
                cart.getCartItem().stream().mapToDouble(CartItem::getItemTotalPrice).sum()
        );
        cartRepo.save(cart);

        return cartItemRepo.save(itemFromDb);
    }

    @Transactional
    public void deleteCartItem(long cartItemId) {
        CartItem itemFromDb = cartItemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("No item in cart found..."));

        Cart cart = itemFromDb.getCart();
        cart.getCartItem().remove(itemFromDb);

        cartItemRepo.delete(itemFromDb);
        cart.setTotalPrice(
                cart.getCartItem().stream().mapToDouble(CartItem::getItemTotalPrice).sum()
        );
        cartRepo.save(cart);
    }

    public List<CartItem> findByCartId(Long cartId) {
        return cartItemRepo.findByCartId(cartId);
    }
}
