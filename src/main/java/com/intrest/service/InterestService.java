package com.intrest.service;

import com.intrest.model.Duration;
import com.intrest.model.Interest;

import java.util.Map;

public interface InterestService {
    double calculateInterest(Interest interest);
    Map<String,Long> calculateDuration(Duration duration);
}
