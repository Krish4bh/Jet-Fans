package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Feedback;
import com.Jet_Fans.web.repository.FeedbackRepo;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepo feedbackRepo;

    public FeedbackService(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public void save(Feedback feedback) {
        feedbackRepo.save(feedback);
    }
}
