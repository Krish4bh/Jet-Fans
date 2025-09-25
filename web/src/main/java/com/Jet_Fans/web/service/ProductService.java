package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllByTitle(String title) {
        return productRepo.findByTitleContainingIgnoreCase(title);
    }

    public Product getById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("No product found..."));
    }

    public void updateProduct(Product product) {
        Product productFromDb = productRepo.findById(product.getId()).orElseThrow(() -> new RuntimeException("No product found..."));

        if (product.getTitle() != null) {
            productFromDb.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            productFromDb.setDescription(product.getDescription());
        }
        if (product.getPrice() > 0) {
            productFromDb.setPrice(product.getPrice());
        }
    }
}
