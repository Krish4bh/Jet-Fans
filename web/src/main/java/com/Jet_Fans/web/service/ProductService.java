package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.repository.CartItemRepo;
import com.Jet_Fans.web.repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public void createProduct(Product product) {
        productRepo.save(product);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

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

        productRepo.save(productFromDb);
    }

    public void addProductImages(Long productId, List<String> newImages) {
        Product productFromDb = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("No product found..."));
        productFromDb.getImages().addAll(newImages);
        productRepo.save(productFromDb);
    }

    public void removeProductImages(Long productId, List<String> imagesToRemove) {
        Product productFromDb = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("No product found..."));
        productFromDb.getImages().removeAll(imagesToRemove);
        productRepo.save(productFromDb);
    }

    @Transactional
    public void deleteProduct(Long id) {
        // delete cart items referencing this product first (to avoid FK constraint)
        try {
            cartItemRepo.deleteByProductId(id);
        } catch (Exception ex) {
            // log but continue; we still attempt product deletion and bubble up if needed
            System.err.println("Error deleting cart items for product " + id + ": " + ex.getMessage());
        }

        // finally delete the product
        productRepo.deleteById(id);
    }
}
