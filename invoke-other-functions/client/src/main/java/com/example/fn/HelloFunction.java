package com.example.fn;

import com.fnproject.fn.api.InvocationContext;

import java.util.logging.Logger;

public class HelloFunction {
    private static final Logger LOGGER = Logger.getLogger(HelloFunction.class.getName());

    public String handleRequest(InvocationContext ctx) {
        var fnOcid = ctx.getRuntimeContext().getConfiguration().get("FUNCTIONS_OCID");
        ctx.getRuntimeContext().getConfiguration().forEach((k, v) -> {
            LOGGER.info(String.format("%s: %S", k, v));
        });
        return fnOcid;
    }

}