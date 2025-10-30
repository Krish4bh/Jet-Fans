package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.CartItem;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.repository.CartItemRepo;
import com.Jet_Fans.web.repository.CartRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Transactional
    public CartItem createCartItem(Cart cart, Product product, int quantity) {
        CartItem existingItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), product.getId()).orElse(null);

        if (existingItem != null) {
            // If the item exists, just update its quantity
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setItemTotalPrice(existingItem.getProduct().getPrice() * existingItem.getQuantity());
            return cartItemRepo.save(existingItem);
        }

        // If the item was deleted previously in the same session, clear the persistence context first
        cartRepo.flush();  // ensure DB sync
        cartItemRepo.flush();  // clear any pending inserts/deletes

        // Now create a fresh one safely
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setItemTotalPrice(product.getPrice() * quantity);

        cart.getCartItem().add(newItem);
        return cartItemRepo.save(newItem);
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
