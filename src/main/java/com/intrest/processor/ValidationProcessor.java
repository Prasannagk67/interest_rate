package com.intrest.processor;


import com.intrest.model.Interest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ValidationProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Interest interest = exchange.getIn().getBody(Interest.class);
        if (interest == null) {
            throw new IllegalArgumentException("Request body is missing or invalid.");
        }
        if (interest.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (interest.getMonths() <= 0) {
            throw new IllegalArgumentException("Months must be greater than zero.");
        }
        if (interest.getRate() <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero.");
        }
    }
}
