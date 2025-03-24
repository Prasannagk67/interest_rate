package com.intrest.route;

import com.intrest.model.Interest;
import com.intrest.processor.ValidationProcessor;
import com.intrest.service.InterestService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

@ApplicationScoped
public class InterestRoute extends RouteBuilder {
    @Inject
    InterestService interestService;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("platform-http")
                .bindingMode(RestBindingMode.json)
                .contextPath("/api");
        onException(IllegalArgumentException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(400))
                .setBody().simple("{ \"error\": \"${exception.message}\" }");
        onException(Exception.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(500))
                .setBody().simple("{ \"error\": \"Internal Server Error\" }");
        rest("/rate")
                .post()
                .consumes("application/json")
                .produces("application/json")
                .type(Interest.class)
                .to("direct:calculateInterest");
        from("direct:calculateInterest")
                .process(new ValidationProcessor())
                .bean(interestService,"calculateInterest");
    }
}
