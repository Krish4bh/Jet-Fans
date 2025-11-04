package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Feedback;
import com.Jet_Fans.web.repository.FeedbackRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepo feedbackRepo;

    public FeedbackService(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public void save(Feedback feedback) {
        feedbackRepo.save(feedback);
    }

    @Transactional(readOnly = true)
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    public void deleteFeedbackById(Long feedbackId) {
        try {
            feedbackRepo.deleteById(feedbackId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting feedback: " + e.getMessage());
        }
    }
}
