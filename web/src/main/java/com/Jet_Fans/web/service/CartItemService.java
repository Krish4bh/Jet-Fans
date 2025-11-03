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
            // If the item exists, just update its quantity and itemTotalPrice
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setItemTotalPrice(existingItem.getProduct().getPrice() * existingItem.getQuantity());

            // persist the updated item
            CartItem saved = cartItemRepo.save(existingItem);

            // Recompute cart total and save cart
            double cartTotal = cart.getCartItem().stream()
                    .mapToDouble(CartItem::getItemTotalPrice)
                    .sum();
            cart.setTotalPrice(cartTotal);
            cartRepo.save(cart);

            return saved;
        }

        // If the item was deleted previously in the same session, clear the persistence context first
        // (flush calls kept as you had them)
        cartRepo.flush();  // ensure DB sync
        cartItemRepo.flush();  // clear any pending inserts/deletes

        // Now create a fresh one safely
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setItemTotalPrice(product.getPrice() * quantity);

        // Save the CartItem (so it has an ID and is persisted)
        CartItem savedNew = cartItemRepo.save(newItem);

        // Ensure cart's list contains the saved item (if not already)
        if (cart.getCartItem() == null) {
            cart.setCartItem(new java.util.ArrayList<>());
        }
        if (!cart.getCartItem().contains(savedNew)) {
            cart.getCartItem().add(savedNew);
        }

        // Recompute cart total and save cart
        double cartTotal = cart.getCartItem().stream()
                .mapToDouble(CartItem::getItemTotalPrice)
                .sum();
        cart.setTotalPrice(cartTotal);
        cartRepo.save(cart);

        return savedNew;
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
