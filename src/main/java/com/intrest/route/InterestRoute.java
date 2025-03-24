package com.intrest.route;

import com.intrest.model.Duration;
import com.intrest.model.Interest;
import com.intrest.processor.ValidationProcessor;
import com.intrest.service.InterestService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.jboss.logging.Logger;

@ApplicationScoped
public class InterestRoute extends RouteBuilder {

    private static final Logger LOG = Logger.getLogger(InterestRoute.class);

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
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody().simple("{ \"error\": \"${exception.message}\" }");

        onException(Exception.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .process(exchange -> {
                    Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    LOG.error("Error processing request", exception);
                    exchange.getMessage().setBody("{ \"error\": \"" + exception.getMessage() + "\" }");
                });

        rest("/rate")
                .post()
                .consumes("application/json")
                .produces("application/json")
                .type(Interest.class)
                .to("direct:calculateInterest")
                .post("/duration")
                .consumes("application/json")
                .produces("application/json")
                .type(Duration.class)
                .to("direct:calculateDuration");

        from("direct:calculateInterest")
                .process(new ValidationProcessor())
                .bean(interestService, "calculateInterest");

        from("direct:calculateDuration")
                .bean(interestService,"calculateDuration");
    }
}
