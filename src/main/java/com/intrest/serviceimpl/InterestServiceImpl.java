package com.intrest.serviceimpl;

import com.intrest.model.Duration;
import com.intrest.model.Interest;
import com.intrest.service.InterestService;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class InterestServiceImpl implements InterestService {
    @Override
    public double calculateInterest(Interest interest) {
        return (interest.getPrice()*interest.getMonths()*interest.getRate())/100;
    }

    @Override
    public Map<String, Long> calculateDuration(Duration duration) {
        long months = ChronoUnit.MONTHS.between(duration.getParsedStartDate(), duration.getParsedEndDate());
        long days = ChronoUnit.DAYS.between(duration.getParsedStartDate().plusMonths(months), duration.getParsedEndDate());

        Map<String, Long> result = new HashMap<>();
        result.put("months", months);
        result.put("days", days);

        return result;
    }

}
