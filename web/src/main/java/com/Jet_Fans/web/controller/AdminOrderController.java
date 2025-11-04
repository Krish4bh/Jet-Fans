package com.Jet_Fans.web.controller;
import com.Jet_Fans.web.entity.Feedback;
import com.Jet_Fans.web.entity.Order;
import com.Jet_Fans.web.service.FeedbackService;
import com.Jet_Fans.web.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    private final OrderService orderService;
    private final FeedbackService feedbackService;

    @Autowired
    public AdminOrderController(OrderService orderService, FeedbackService feedbackService) {
        this.orderService = orderService;
        this.feedbackService = feedbackService;
    }

    /**
     * GET admin manage page - shows all orders on left and all feedbacks on right.
     * Template: admin-manage-orders.html
     *
     * Required service methods used (make sure these exist in your services):
     * - List<Order> getAllOrders();
     * - void cancelOrderById(Long orderId);
     *
     * - List<Feedback> getAllFeedbacks();
     * - void deleteFeedbackById(Long feedbackId);
     *
     * If your service methods use different names, change the calls below accordingly.
     */
    @GetMapping("/manage-orders-feedbacks")
    public String adminManageOrders(HttpSession session, Model model) {
        Object admin = session.getAttribute("loggedInAdmin");
        if (admin == null) {
            return "redirect:/admin/login"; // or wherever your admin login page is
        }

        List<Order> orders = orderService.getAllOrders();
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

        model.addAttribute("orders", orders);
        model.addAttribute("feedbacks", feedbacks);

        return "admin-manage-order-feedback";
    }

    /**
     * Cancel an order (admin action).
     * Implementation expectation: set an order status to 'CANCELLED' or remove record.
     * Here we call a service method that performs cancellation.
     */
    @PostMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long orderId, RedirectAttributes redirectAttrs) {
        try {
            orderService.cancelOrderById(orderId);
            redirectAttrs.addFlashAttribute("successMessage", "Order #" + orderId + " cancelled successfully.");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("errorMessage", "Could not cancel order #" + orderId + ". " + ex.getMessage());
        }
        return "redirect:/admin/manage-orders-feedbacks";
    }

    @PostMapping("/feedbacks/{id}/delete")
    public String deleteFeedback(@PathVariable("id") Long feedbackId, RedirectAttributes redirectAttrs) {
        try {
            feedbackService.deleteFeedbackById(feedbackId);
            redirectAttrs.addFlashAttribute("successMessage", "Feedback removed successfully.");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("errorMessage", "Could not delete feedback. " + ex.getMessage());
        }
        return "redirect:/admin/manage-orders-feedbacks";
    }

}
