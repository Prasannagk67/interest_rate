package com.intrest.serviceimpl;

import com.intrest.model.Interest;
import com.intrest.service.InterestService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InterestServiceImpl implements InterestService {
    @Override
    public double calculateInterest(Interest interest) {
        return (interest.getPrice()*interest.getMonths()*interest.getRate())/100;
    }
}
