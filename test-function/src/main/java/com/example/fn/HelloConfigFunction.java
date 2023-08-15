package com.example.fn;

import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;

public class HelloConfigFunction {
    private String greet;
    
    @FnConfiguration
    public void config(RuntimeContext ctx) {
        greet = ctx.getConfigurationByKey("GREET").orElse("Hello");
    }

    public String handleRequest(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;

        System.out.println("Inside Java Hello World function"); 
        return greet + ", " + name + "!";
    }
}
