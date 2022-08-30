package com.example.fn;

public class HelloFunction {

    public String handleRequest(NotificationsInput input) {
        System.err.println(input.dedupeKey);
        return "ok";
    }

}