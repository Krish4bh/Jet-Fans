package com.Jet_Fans.web.controller;

import com.Jet_Fans.web.entity.Feedback;
import com.Jet_Fans.web.entity.Product;
import com.Jet_Fans.web.service.FeedbackService;
import com.Jet_Fans.web.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final ProductService productService;

    public FeedbackController(FeedbackService feedbackService, ProductService productService) {
        this.feedbackService = feedbackService;
        this.productService = productService;
    }

    @PostMapping("/feedback/add")
    public String addFeedback(@ModelAttribute Feedback feedback,
                              @RequestParam Long productId) {

        Product product = productService.getById(productId);
        feedback.setProduct(product);
        feedbackService.save(feedback);

        return "redirect:/product/" + productId;
    }
}
