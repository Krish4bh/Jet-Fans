package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String searchProductByKeyword(@RequestParam String keyword, Model model) {
        List<Product> searchResult = productService.getAllByTitle(keyword);
        model.addAttribute("products", searchResult);
        return "shop-now";
    }

    @GetMapping("/home/shop-now")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "shop-now";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @PostMapping("/add/product")
    public String addProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // Handle image uploads if files are provided
            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<String> imageUrls = new ArrayList<>();

                for (MultipartFile file : imageFiles) {
                    if (!file.isEmpty()) {
                        String fileName = saveImage(file);
                        imageUrls.add(fileName);
                    }
                }

                product.setImages(imageUrls);

                // Set first image as primary
                if (!imageUrls.isEmpty()) {
                    product.setImage(imageUrls.get(0));
                }
            }

            productService.createProduct(product);

            System.out.println("************************************************************************");
            System.out.println("Product with id " + product.getId() + " has been successfully added.");
            System.out.println("************************************************************************");

            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
            return "redirect:/admin/admin-home";

        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add product: " + e.getMessage());
            return "redirect:/admin/admin-home";
        }
    }

    @PostMapping("/edit/product")
    public String editProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // Get existing product to preserve old images if no new images uploaded
            Product existingProduct = productService.getById(product.getId());

            // Handle new image uploads
            if (imageFiles != null && !imageFiles.isEmpty() && !imageFiles.get(0).isEmpty()) {
                List<String> newImageUrls = new ArrayList<>();

                for (MultipartFile file : imageFiles) {
                    if (!file.isEmpty()) {
                        String fileName = saveImage(file);
                        newImageUrls.add(fileName);
                    }
                }

                // Optional: Delete old images from disk
                deleteOldImages(existingProduct.getImages());

                product.setImages(newImageUrls);

                if (!newImageUrls.isEmpty()) {
                    product.setImage(newImageUrls.get(0));
                }
            } else {
                // Keep existing images if no new images uploaded
                product.setImages(existingProduct.getImages());
                product.setImage(existingProduct.getImage());
            }

            productService.updateProduct(product);

            System.out.println("************************************************************************");
            System.out.println("Product with id " + product.getId() + " has been successfully updated.");
            System.out.println("************************************************************************");

            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
            return "redirect:/admin/admin-home";

        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update product");
            return "redirect:/admin/admin-home";
        }
    }

    @PostMapping("/product/delete/{id}")
    public String removeProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Get product to access its images
            Product product = productService.getById(id);

            // Delete associated images from disk
            if (product != null && product.getImages() != null) {
                deleteOldImages(product.getImages());
            }

            // Delete product from database
            productService.deleteProduct(id);

            System.out.println("************************************************************************");
            System.out.println("Product with id " + id + " has been successfully deleted.");
            System.out.println("************************************************************************");

            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
            return "redirect:/admin/admin-home";

        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete product");
            return "redirect:/admin/admin-home";
        }
    }

    // Helper method to save images to disk
    private String saveImage(MultipartFile file) throws IOException {
        // upload directory inside resources/static
        String uploadDir = "src/main/resources/static/Product-Imgs/";

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;

        // Save file to static folder
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Image saved: " + fileName);
        // return only the filename (no leading /Product-Imgs/)
        return fileName;
    }

    // Helper method to delete old images from disk
    private void deleteOldImages(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        // upload dir path
        String uploadDir = "src/main/resources/static/Product-Imgs/";

        for (String imageUrl : imageUrls) {
            try {
                // imageUrl might be stored with a leading path ("/Product-Imgs/filename")
                // so normalize to only filename
                String filename = imageUrl;
                if (filename.startsWith("/")) {
                    filename = filename.substring(filename.lastIndexOf("/") + 1);
                } else if (filename.contains("/")) {
                    filename = Paths.get(filename).getFileName().toString();
                }

                Path imagePath = Paths.get(uploadDir).resolve(filename);
                Files.deleteIfExists(imagePath);
                System.out.println("Deleted old image: " + filename);
            } catch (IOException e) {
                System.err.println("Failed to delete image: " + imageUrl);
                e.printStackTrace();
            }
        }
    }
}