package com.Jet_Fans.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Jet_Fans.web.entity.Order;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    public List<Order> getAllByUserId(Long userId);

    List<Order> findByUserId(Long userId);

}
