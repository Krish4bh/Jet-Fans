package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Cart;
import com.Jet_Fans.web.entity.CartItem;
import com.Jet_Fans.web.entity.Order;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    @Transactional
    public Order placeOrder(User user, String address) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must be logged in to place order");
        }

        // load cart using existing method names in your project
        Cart cart = cartService.getCartByUserId(user.getId());
        if (cart == null) {
            throw new IllegalStateException("Cart not found.");
        }

        List<CartItem> cartItems = cartItemService.findByCartId(cart.getId());
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot place order with empty cart.");
        }

        // create order and map products
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address == null ? (user.getAddress() == null ? "" : user.getAddress()) : address);

        // total amount: use cart.getTotalPrice() (your Cart has totalPrice field)
        order.setTotalAmount(cart.getTotalPrice());

        // map products from cart items
        List<Product> products = cartItems.stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());
        order.setProducts(products);

        // save order
        Order saved = orderRepo.save(order);

        // clear cart using your existing CartService method (signature: clearCart(Long userId))
        cartService.clearCart(user.getId());

        return saved;
    }

    public List<Order> findOrdersByUser(User user) {
        return orderRepo.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public void cancelOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            try {

                 orderRepo.delete(order);

            } catch (Exception e) {
                throw new RuntimeException("Error cancelling order: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }

}
