package com.example.fn;

public class HelloJsonFunction {

    public String handleRequest(Input input) {
        System.out.println("Inside Java Hello World function");
        return "Hello, " + input.name() + "!";
    }

}
