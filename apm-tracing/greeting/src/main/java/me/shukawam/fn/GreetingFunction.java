package me.shukawam.fn;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;

import java.util.logging.Logger;

public class GreetingFunction {
    private static final Logger LOGGER = Logger.getLogger(GreetingFunction.class.getName());

    public String handleRequest(String input, HTTPGatewayContext ctx) {
        String name = (input == null || input.isEmpty()) ? "world" : input;

        System.out.println("Inside Java Hello World function");
        loggingHeaders(ctx);
        return "Hello, " + name + "!";
    }

    private void loggingHeaders(HTTPGatewayContext ctx) {
        LOGGER.info("*** HTTP Request Headers ***");
        ctx.getInvocationContext().getRequestHeaders().asMap().forEach((k, v) -> LOGGER.info(String.format("%s: %s", k, v)));
    }

}