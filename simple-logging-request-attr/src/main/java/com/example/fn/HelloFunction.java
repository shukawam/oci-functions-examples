package com.example.fn;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;

public class HelloFunction {

    public String handleRequest(HTTPGatewayContext ctx) {
        System.out.println("*** Query Parameters ***");
        ctx.getQueryParameters().getAll().forEach((k, v) -> {
            System.out.println(String.format("k: %s, v: %s", k, v));
        });
        System.out.println("*** Request URL ***");
        System.out.println(ctx.getRequestURL());
        return "ok";
    }

}