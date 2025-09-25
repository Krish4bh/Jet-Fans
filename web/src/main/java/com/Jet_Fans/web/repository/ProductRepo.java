package com.Jet_Fans.web.repository;


import com.Jet_Fans.web.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByTitle(String title);

    List<Product> findByTitleContainingIgnoreCase(String title);
}
