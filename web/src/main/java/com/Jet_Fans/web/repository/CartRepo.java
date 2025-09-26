package com.Jet_Fans.web.repository;

import com.Jet_Fans.web.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    public Cart findByUserId(Long id);

    public void deleteByUserId(Long id);
}
