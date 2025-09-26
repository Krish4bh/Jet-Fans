package com.Jet_Fans.web.repository;

import com.Jet_Fans.web.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    public List<CartItem> findByCartId(Long cartId);
}
